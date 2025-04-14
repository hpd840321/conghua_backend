package com.scenic.ai.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 自定义错误处理控制器
 */
@Controller
public class CustomErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String message = (String) request.getAttribute("jakarta.servlet.error.message");
        Exception exception = (Exception) request.getAttribute("jakarta.servlet.error.exception");

        if (statusCode == null) {
            statusCode = 500;
        }

        model.addAttribute("code", statusCode);
        model.addAttribute("message", message != null ? message : getDefaultMessage(statusCode));
        model.addAttribute("error", exception != null ? exception.getMessage() : "未知错误");

        return "error/error";
    }

    private String getDefaultMessage(int status) {
        switch (status) {
            case 404:
                return "请求的资源不存在";
            case 403:
                return "没有访问权限";
            case 500:
                return "服务器内部错误";
            default:
                return "未知错误";
        }
    }
} 