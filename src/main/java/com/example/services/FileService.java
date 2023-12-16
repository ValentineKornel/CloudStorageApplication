package com.example.services;

import com.example.mapper.FileMapper;
import com.example.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public List<File> getFilesByUser(int userId) {
        return fileMapper.getFilesByUser(userId);
    }

    public File getFileById(int fileId) {
        return fileMapper.getFileById(fileId);
    }

    public boolean addFile(MultipartFile multipartFile, boolean isPublic, String username) throws IOException {
        int userId = userService.getUser(username).getUserId();

        File file = new File(
                null,
                isPublic,
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                Long.toString(multipartFile.getSize()),
                multipartFile.getBytes(),
                userId,
                username
        );
        return fileMapper.insertFile(file) > 0;
    }

    public List<File> getAllFiles() {
        return fileMapper.getAllFiles();
    }

    public boolean deleteFile(int fileId) {
        File file = fileMapper.getFileById(fileId);
        if (file != null) {
            return fileMapper.deleteFile(fileId) > 0;
        }
        return false;
    }

    public void changeVisibility(int fileId){
        File file = fileMapper.getFileById(fileId);

        fileMapper.changeVisibility(fileId, !file.getIsPublic());
    }

    public List<File> getAllFilesSearched(String searchRequest, int userId){
        return fileMapper.getAllFilesSearched(searchRequest, userId);
    }

    public List<File> getUserFilesSearched(int senderId, int userId){
        return fileMapper.getUserFilesSearched(senderId, userId);
    }
}