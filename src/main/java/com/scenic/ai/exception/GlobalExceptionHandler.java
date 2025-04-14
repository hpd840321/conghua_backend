package com.scenic.ai.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusinessException(HttpServletRequest request, BusinessException ex) {
        log.error("业务异常: {}", ex.getMessage(), ex);
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("status", ex.getStatus().value());
        mav.addObject("error", "业务异常");
        mav.addObject("message", ex.getMessage());
        mav.setViewName("error/error");
        return mav;
    }
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex) {
        log.error("系统异常: {}", ex.getMessage(), ex);
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        mav.addObject("error", "系统异常");
        mav.addObject("message", ex.getMessage());
        mav.setViewName("error/error");
        return mav;
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleValidationException(HttpServletRequest request, ConstraintViolationException ex) {
        log.error("请求: {} 数据验证失败", request.getRequestURL(), ex);
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", request.getRequestURL());
        mav.addObject("statusCode", HttpStatus.BAD_REQUEST.value());
        mav.addObject("errorMessage", "数据验证失败: " + ex.getMessage());
        mav.setViewName("error/error");
        
        return mav;
    }
} 