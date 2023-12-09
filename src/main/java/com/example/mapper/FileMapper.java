package com.example.mapper;

import com.example.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileById(int fileId);

    @Insert("INSERT INTO FILES (filename, isPublic, contentType, fileSize, fileData, userId, ownerUsername) VALUES (#{filename}, #{isPublic}, #{contentType}, #{fileSize}, #{fileData}, #{userId}, #{ownerUsername})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(int fileId);

    @Select("SELECT * FROM FILES")
    List<File> getAllFiles();

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFilesByUser(int userId);

    @Select("SELECT * FROM FILES WHERE filename LIKE CONCAT('%', #{searchRequest}, '%')" +
            " AND (isPublic = 1" +
            " OR userId = #{userId})")
    List<File> getAllFilesSearched(String searchRequest, int userId);

    @Select("SELECT * FROM FILES WHERE (userId = #{searchId} AND isPublic = 1)" +
            " OR userId = #{userId}")
    List<File> getUserFilesSearched(int searchId, int userId);

    @Update("UPDATE FILES SET isPublic = #{isPublic} WHERE fileId = #{fileId}")
    void changeVisibility(int fileId, boolean isPublic);
}