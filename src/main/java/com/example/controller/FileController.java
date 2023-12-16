package com.example.controller;


import com.example.model.File;
import com.example.services.FileService;
import com.mysql.cj.exceptions.DataTruncationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DataTruncation;
import java.util.Objects;

@Controller
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }
    @PostMapping("/files")
    public ModelAndView fileUpload(
            @RequestParam("fileUpload")MultipartFile file,
            Authentication auth
            ){
        ModelAndView result = new ModelAndView();
        String status = null;
        boolean isPublic = false;

        if(file.getSize() > 30000000){
            result.addObject("errorMsg", true);
            status = "File is too big. Max file size is 30MB";
            result.setViewName("result");
            result.addObject("message", status);
            return result;
        }

        try{
            if(fileService.addFile(file, isPublic, auth.getName())) {
                result.addObject("success", true);
                status = "File uploaded successfully";
            }
        } catch (IOException e){
            result.addObject("errorMsg", true);
            status = "Unable to upload file.";
        }

        result.setViewName("result");
        result.addObject("message", status);
        return result;
    }

    @GetMapping("files/delete/{fileId}")
    public ModelAndView deleteFile(@PathVariable int fileId){
        ModelAndView result = new ModelAndView();
        String status = "";

        if(fileService.deleteFile(fileId)){
            result.addObject("success", true);
            status = "File deleted";
        }else {
            result.addObject("errorMsg", true);
            status = "Unable to delete file";
        }
        result.setViewName("result");
        result.addObject("message", status);
        return result;
    }

    @GetMapping("files/download/{fileId}")
    public void downloadFile(@PathVariable int fileId, HttpServletResponse response) throws IOException {
        File file = fileService.getFileById(fileId);

        response.setContentType(file.getContentType());
        response.setContentLength(Integer.parseInt(file.getFileSize()));
        String headerValue = "file:" + file.getFilename();
        response.setHeader("Content-Disposition", headerValue);
        response.getOutputStream().write(file.getFileData());
        response.flushBuffer();
    }

    @GetMapping("files/changeVisibility/{fileId}")
    public ModelAndView changeVisibility(@PathVariable int fileId){
        ModelAndView result = new ModelAndView();
        String status = null;

        fileService.changeVisibility(fileId);
        result.addObject("success", true);
        status = "File uploaded successfully";
        result.setViewName("result");
        result.addObject("message", status);
        return result;

    }
}
