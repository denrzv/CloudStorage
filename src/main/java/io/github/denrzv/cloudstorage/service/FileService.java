package io.github.denrzv.cloudstorage.service;

import io.github.denrzv.cloudstorage.entity.User;
import io.github.denrzv.cloudstorage.entity.UserFile;
import io.github.denrzv.cloudstorage.repository.FileRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Data
@Service
public class FileService {

    private final FileRepository userFileRepository;
    private final String uploadDir = "upload";

    public void saveFile(MultipartFile file, long userId) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        UserFile userFile = UserFile.builder()
                .filename(filename)
                .size((int) file.getSize())
                .user(User.builder().id(userId).build())
                .build();
        userFileRepository.save(userFile);

        String fullPath = uploadDir + "/" + userId;

        Path uploadDir = Paths.get(fullPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadDir.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public List<UserFile> listFilesForUser(long userId) {
        return userFileRepository.findByUserId(userId);
    }

    public byte[] getFileData(String filename, long userId) {
        UserFile userFile = userFileRepository.findByFilenameAndUserId(filename, userId);
        if (userFile == null) {
            return null;
        }
        Path filePath = Paths.get(uploadDir+ "/" + userId + "/" + filename);
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            return null;
        }
    }

    public boolean deleteFile(String filename, long userId) {
        UserFile userFile = userFileRepository.findByFilenameAndUserId(filename, userId);
        if (userFile == null) {
            return false;
        }
        userFileRepository.delete(userFile);
        Path filePath = Paths.get(uploadDir + "/" + userId + "/" + filename);
        try {
            Files.delete(filePath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean renameFile(String currentFilename, String newFilename, long userId) {
        UserFile userFile = userFileRepository.findByFilenameAndUserId(currentFilename, userId);
        if (userFile == null) {
            return false;
        }
        String fullPath = uploadDir + "/" + userId + "/";
        Path currentFilePath = Paths.get(fullPath + currentFilename);
        Path newFilePath = Paths.get(fullPath + newFilename);
        try {
            Files.move(currentFilePath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return false;
        }
        userFile.setFilename(newFilename);
        userFileRepository.save(userFile);
        return true;
    }

}


