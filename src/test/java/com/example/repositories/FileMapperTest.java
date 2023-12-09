package com.example.repositories;

import com.example.model.File;
import com.example.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;

    private static User testUser;

    private File testFile;

    @BeforeAll
    static void beforeAll() {
        testUser = new User(null, "johnnydoe", "someSalt", "password", "John", "Doe");
    }

    @BeforeEach
    void setUp() {
        userRepository.createUser(testUser);
        testUser = userRepository.getUser(testUser.getUsername());
        byte[] fileData = new byte[20];
        testFile = new File(null, "somefile.png", "image/png", "32", fileData, testUser.getUserId());
        fileRepository.insertFile(testFile);
    }

    @Test
    void getFileById() {
        File fileById = fileRepository.getFileById(1);
        assertThat(fileById.getFilename()).isEqualTo(testFile.getFilename());
        assertThat(fileById.getFileData()).isEqualTo(testFile.getFileData());
    }


    @Test
    void insertFile() {
        byte[] fileData = new byte[20];
        int created = fileRepository.insertFile(new File(null, "somefile.png", "image/png", "32", fileData, testUser.getUserId()));
        assertThat(created).isGreaterThan(0);
    }

    @Test
    void deleteFile() {
        int deleted = fileRepository.deleteFile(1);
        assertThat(deleted).isGreaterThan(0);
    }

    @Test
    void getAllFiles() {
        List<File> allFiles = fileRepository.getAllFiles();
        assertThat(allFiles).isNotEmpty();

        File file = allFiles.get(0);
        assertThat(file.getContentType()).isEqualTo(testFile.getContentType());
        assertThat(file.getFileSize()).isEqualTo(testFile.getFileSize());
    }

    @Test
    void getFilesByUser() {
        fileRepository.insertFile(testFile);
        List<File> filesByUser = fileRepository.getFilesByUser(1);
        assertThat(filesByUser).isNotEmpty();
        assertThat(filesByUser.size()).isGreaterThan(1);

        File file = filesByUser.get(0);
        assertThat(file).isNotNull();
        assertThat(file.getFilename()).isEqualTo(testFile.getFilename());
        assertThat(file.getFileData()).isEqualTo(testFile.getFileData());
    }
}