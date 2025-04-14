package com.scenic.ai.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 设备编码验证器
 * 验证规则：
 * 1. 不能为空
 * 2. 长度在4-20位之间
 * 3. 只能包含字母、数字
 */
public class DeviceCodeValidator implements ConstraintValidator<ValidDeviceCode, String> {

    @Override
    public void initialize(ValidDeviceCode constraintAnnotation) {
        // 初始化方法，无需特殊处理
    }

    @Override
    public boolean isValid(String deviceCode, ConstraintValidatorContext context) {
        if (deviceCode == null || deviceCode.trim().isEmpty()) {
            return false;
        }
        
        // 验证长度和字符组成
        return deviceCode.length() >= 4 && deviceCode.length() <= 20 
            && deviceCode.matches("^[a-zA-Z0-9]+$");
    }
} 