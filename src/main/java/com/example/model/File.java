package com.example.model;

public class File {
    private Integer fileId;
    private Boolean isPublic;
    private String filename;
    private String contentType;
    private String fileSize;
    private byte[] fileData;
    private int userId;
    private String ownerUsername;

    public File() {
    }

    public File(Integer fileId, Boolean isPublic, String filename, String contentType, String fileSize, byte[] fileData, int userId, String ownerUsername) {
        this.fileId = fileId;
        this.isPublic = isPublic;
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileData = fileData;
        this.userId = userId;
        this.ownerUsername = ownerUsername;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOwnerUsername(){return this.ownerUsername;}

    public void setOwnerUsername(String ownerUsername){
        this.ownerUsername = ownerUsername;
    }

    @Override
    public String toString(){
        return "" + this.fileId + " " + this.filename + " " + this.isPublic;
    }
}