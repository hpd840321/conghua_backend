package com.scenic.ai.common.converter;

import com.scenic.ai.dto.PanoramaDTO;

import org.springframework.stereotype.Component;

/**
 * 全景图DTO转换器
 */
@Component
public class PanoramaConverter implements BaseConverter<PanoramaDTO, Panorama> {
    
    @Override
    public PanoramaDTO toDTO(Panorama entity) {
        if (entity == null) {
            return null;
        }
        
        PanoramaDTO dto = new PanoramaDTO();
        dto.setId(entity.getId());
        dto.setDeviceCode(entity.getDeviceCode());
        dto.setDeviceName(entity.getDeviceName());
        dto.setTourismName(entity.getTourismName());
        dto.setImageUrl(entity.getImageUrl());
        dto.setCreateTime(entity.getCreateTime());
        dto.setUpdateTime(entity.getUpdateTime());
        return dto;
    }
    
    @Override
    public Panorama toEntity(PanoramaDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Panorama entity = new Panorama();
        entity.setId(dto.getId());
        entity.setDeviceCode(dto.getDeviceCode());
        entity.setDeviceName(dto.getDeviceName());
        entity.setTourismName(dto.getTourismName());
        entity.setImageUrl(dto.getImageUrl());
        entity.setCreateTime(dto.getCreateTime());
        entity.setUpdateTime(dto.getUpdateTime());
        return entity;
    }
} 