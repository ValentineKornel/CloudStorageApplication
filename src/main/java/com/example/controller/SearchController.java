package com.example.controller;


import com.example.model.File;
import com.example.services.FileService;
import com.example.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/search")
public class SearchController {
    private final FileService fileService;
    private final UserService userService;

    public SearchController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public String getSearch(@RequestParam String searchRequest, Authentication authentication, Model model){
        int userId = userService.getUser(authentication.getName()).getUserId();

        if(searchRequest.charAt(0) == '@'){
            Integer senderId = null;
            try{
                senderId = userService.getUser(searchRequest.substring(1)).getUserId();
            }catch (NullPointerException e){
                model.addAttribute("message", "Search results with '" + searchRequest + "': ");
                model.addAttribute("files", new ArrayList<>());
                return "search";
            }
            if(senderId.equals(userId)){
                return "redirect:/home";
            }

            model.addAttribute("message", "Search results with '" + searchRequest + "': ");
            model.addAttribute("files", fileService.getUserFilesSearched(senderId, userId));
        }else{
            model.addAttribute("message", "Search results with '" + searchRequest + "': ");
            model.addAttribute("files", fileService.getAllFilesSearched(searchRequest, userId));
        }

        return "search";
    }
}

/*
@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final UserService userService;

    public HomeController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomeView(Authentication authentication, File file, Model model) {
        int userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("files", fileService.getFilesByUser(userId));
        return "home";
    }
}*/
