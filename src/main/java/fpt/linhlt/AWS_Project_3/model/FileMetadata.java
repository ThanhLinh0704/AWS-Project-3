package fpt.linhlt.AWS_Project_3.model;

public class FileMetadata {
    private String objectkey;
    private String filename;
    private String uploadTime;
    private String s3Uri;


    public String getObjectkey() {
        return objectkey;
    }

    public void setObjectkey(String objectkey) {
        this.objectkey = objectkey;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getS3Uri() {
        return s3Uri;
    }

    public void setS3Uri(String s3Uri) {
        this.s3Uri = s3Uri;
    }
}
