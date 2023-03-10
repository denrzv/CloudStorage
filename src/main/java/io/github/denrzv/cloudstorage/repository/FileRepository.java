package io.github.denrzv.cloudstorage.repository;

import io.github.denrzv.cloudstorage.entity.UserFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends CrudRepository<UserFile, Long> {
    List<UserFile> findByUserId(long userId);
    UserFile findByFilenameAndUserId(String filename, long userId);
}

