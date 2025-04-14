package com.scenic.ai.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // 获取错误状态码
        Object status = request.getAttribute("jakarta.servlet.error.status_code");
        Object message = request.getAttribute("jakarta.servlet.error.message");
        Object error = request.getAttribute("jakarta.servlet.error.exception");
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            
            // 设置默认错误信息
            if (message == null) {
                switch (statusCode) {
                    case 404:
                        message = "请求的页面不存在";
                        break;
                    case 403:
                        message = "没有访问权限";
                        break;
                    case 500:
                        message = "服务器内部错误";
                        break;
                    default:
                        message = "未知错误";
                }
            }
            
            model.addAttribute("status", status);
            model.addAttribute("message", message);
            model.addAttribute("error", error != null ? error : "");
        }
        
        return "error/error";
    }
} 