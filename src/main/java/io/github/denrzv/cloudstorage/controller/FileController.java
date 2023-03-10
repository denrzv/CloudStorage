package io.github.denrzv.cloudstorage.controller;

import io.github.denrzv.cloudstorage.entity.User;
import io.github.denrzv.cloudstorage.entity.UserFile;
import io.github.denrzv.cloudstorage.service.FileService;
import io.github.denrzv.cloudstorage.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class FileController {

    private final FileService fileService;

    private final UserService userService;

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        User user = userService.getUserByUsername(getAuthenticatedUsername());
        try {
            fileService.saveFile(file, user.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save file: " + e.getMessage());
        }
        return ResponseEntity.ok("File uploaded successfully.");
    }

    @PutMapping("/file")
    public ResponseEntity<String> renameFile(@RequestParam("filename") String currentFilename, @RequestBody Map<String, String> request) {
        User user = userService.getUserByUsername(getAuthenticatedUsername());
        long userId = user.getId();
        String newFilename = request.get("filename");

        boolean result = fileService.renameFile(currentFilename, newFilename, userId);
        if (result) {
            return ResponseEntity.ok("File renamed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to rename file.");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserFile>> listFiles(@RequestParam Integer limit) {
        User user = userService.getUserByUsername(getAuthenticatedUsername());
        List<UserFile> files = fileService.listFilesForUser(user.getId());
        files = files.parallelStream().limit(limit).toList();
        return ResponseEntity.ok(files);
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String filename) {
        User user = userService.getUserByUsername(getAuthenticatedUsername());
        byte[] fileData = fileService.getFileData(filename, user.getId());
        if (fileData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fileData);
    }

    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestParam String filename) {
        User user = userService.getUserByUsername(getAuthenticatedUsername());
        boolean success = fileService.deleteFile(filename, user.getId());
        if (success) {
            return ResponseEntity.ok("File deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private String getAuthenticatedUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}



