package fpt.linhlt.AWS_Project_3.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FileService {
    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.dynamodb.table}")
    private String tableName;

    private final S3Client s3;
    private final DynamoDbClient dynamoDb;

    public FileService(S3Client s3, DynamoDbClient dynamoDb) {
        this.s3 = s3;
        this.dynamoDb = dynamoDb;
    }

    public void uploadFile(MultipartFile file) throws IOException {
        String key = file.getOriginalFilename();

        // Upload to S3
        PutObjectRequest putReq = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3.putObject(putReq, RequestBody.fromBytes(file.getBytes()));

        // Save metadata
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("objectkey", AttributeValue.fromS(key));
        item.put("filename", AttributeValue.fromS(key));
        item.put("uploadTime", AttributeValue.fromS(Instant.now().toString()));
        item.put("s3Uri", AttributeValue.fromS("s3://" + bucketName + "/" + key));

        PutItemRequest putItem = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        dynamoDb.putItem(putItem);
    }

    public List<String> getFileNames() {
        ScanRequest scan = ScanRequest.builder()
                .tableName(tableName)
                .attributesToGet("filename")
                .build();
        List<Map<String, AttributeValue>> items = dynamoDb.scan(scan).items();

        return items.stream()
                .map(m -> m.get("filename"))
                .filter(Objects::nonNull)
                .map(AttributeValue::s)
                .collect(Collectors.toList());
    }

    public String getFileContent(String s3Uri) throws IOException {
        Map<String, AttributeValue> key = Map.of("objectkey", AttributeValue.fromS(s3Uri));

        GetItemRequest request = GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build();

        Map<String, AttributeValue> item = dynamoDb.getItem(request).item();
        if (item == null || !item.containsKey("objectkey")) return null;

        String fullS3Uri = item.get("objectkey").s();

        String s3Key = fullS3Uri.substring(fullS3Uri.lastIndexOf("/") + 1);

        GetObjectRequest getObj = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build();

        return new String(s3.getObject(getObj).readAllBytes(), StandardCharsets.UTF_8);
    }

}
