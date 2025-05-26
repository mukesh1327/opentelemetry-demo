package com.cloudxplorer.springboot_todo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    private static final String IMAGE_URL = "/images/todo.png"; // Serve a fixed image

    @Value("${app.title}")
    private String appTitle;

    @GetMapping("/")
    public String getImageHtml() {
        return "<html>" +
                "<head><title>Image Viewer</title></head>" +
                "<body>" +
                "<h1>" + appTitle + "</h1>" +
                "<h2>App modernization</h2>" +
                "<img src='" + IMAGE_URL + "' alt='Image' width='500'/>" +
                "</body>" +
                "</html>";
    }
}
