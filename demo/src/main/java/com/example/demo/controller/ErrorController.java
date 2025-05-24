package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
    @ExceptionHandler()
    public String handleError(HttpServletRequest request, Model model) {
        Object code = request.getAttribute("jakarta.servlet.error.status_code");
        model.addAttribute("status", code);
        String text;
        switch ((int)code) {
            case 404 -> text = "Not Found";
            case 500 -> text = "Internal Server Error";
            case 400 -> text = "Bad Request";
            case 401 -> text = "Unauthorized";
            case 502 -> text = "Bad Gateway";
        }
        return "error";
    }
}
