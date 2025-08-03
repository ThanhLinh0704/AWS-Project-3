package fpt.linhlt.AWS_Project_3.controller;

import fpt.linhlt.AWS_Project_3.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FileController {
    private final FileService service;

    public FileController(FileService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            service.uploadFile(file);
            return ResponseEntity.ok("Uploaded.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed.");
        }
    }

    @GetMapping("/getfilesfromdb")
    public List<String> getFiles() {
        return service.getFileNames();
    }

    @GetMapping("/file-content/{fileName}")
    public ResponseEntity<String> getFileContent(@PathVariable String fileName) throws IOException {
        String content = service.getFileContent(fileName);
        return content == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(content);
    }
}

