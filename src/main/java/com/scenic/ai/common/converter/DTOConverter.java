package com.scenic.ai.common.converter;

import com.scenic.ai.dto.*;
import com.scenic.ai.dto.request.ThirdPartyQueryRequest;
import com.scenic.ai.dto.response.PageResponse;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO转换工具类
 * 用于处理DTO对象之间的转换
 *
 * @author scenic-AI
 * @version 1.0
 */
@Component
public class DTOConverter {

    /**
     * 转换第三方接口数据到告警DTO
     */
    public AlertDTO convertToAlertDTO(ThirdPartyDataDTO thirdPartyData) {
        if (thirdPartyData == null) {
            return null;
        }

        AlertDTO alertDTO = new AlertDTO();
        alertDTO.setId(thirdPartyData.getId());
        alertDTO.setDeviceCode(thirdPartyData.getDeviceCode());
        alertDTO.setAlertType(FieldConverter.AlertConverter.getAlertTypeFromEvent(thirdPartyData.getAlarmEvent()));
        alertDTO.setAlertContent(thirdPartyData.getAlarmEvent());
        alertDTO.setCreateTime(thirdPartyData.getCreateTime());
        alertDTO.setUpdateTime(thirdPartyData.getUpdateTime());
        return alertDTO;
    }

    /**
     * 转换第三方接口数据到全景图DTO
     */
    public PanoramaDTO convertToPanoramaDTO(ThirdPartyDataDTO thirdPartyData) {
        if (thirdPartyData == null) {
            return null;
        }

        PanoramaDTO panoramaDTO = new PanoramaDTO();
        panoramaDTO.setId(thirdPartyData.getId());
        panoramaDTO.setDeviceCode(thirdPartyData.getDeviceCode());
        panoramaDTO.setDeviceName(thirdPartyData.getDeviceName());
        panoramaDTO.setTourismName(thirdPartyData.getTourismName());
        panoramaDTO.setImageUrl(FieldConverter.PanoramaConverter.getImageUrlFromImage(thirdPartyData.getImage()));
        panoramaDTO.setCreateTime(thirdPartyData.getCreateTime());
        panoramaDTO.setUpdateTime(thirdPartyData.getUpdateTime());
        return panoramaDTO;
    }

    /**
     * 转换第三方接口数据到人群统计DTO
     */
    public CrowdStatisticsDTO convertToCrowdStatisticsDTO(ThirdPartyDataDTO thirdPartyData) {
        if (thirdPartyData == null) {
            return null;
        }

        CrowdStatisticsDTO crowdStatisticsDTO = new CrowdStatisticsDTO();
        crowdStatisticsDTO.setId(thirdPartyData.getId());
        crowdStatisticsDTO.setDeviceCode(thirdPartyData.getDeviceCode());
        crowdStatisticsDTO.setCrowdCount(FieldConverter.CrowdConverter.getCrowdCountFromCount(thirdPartyData.getCount()));
        crowdStatisticsDTO.setRecordTime(thirdPartyData.getCreateTime());
        return crowdStatisticsDTO;
    }

    /**
     * 批量转换第三方接口数据到告警DTO
     */
    public List<AlertDTO> convertToAlertDTOList(List<ThirdPartyDataDTO> thirdPartyDataList) {
        if (thirdPartyDataList == null) {
            return null;
        }
        return thirdPartyDataList.stream()
                .map(this::convertToAlertDTO)
                .collect(Collectors.toList());
    }

    /**
     * 批量转换第三方接口数据到全景图DTO
     */
    public List<PanoramaDTO> convertToPanoramaDTOList(List<ThirdPartyDataDTO> thirdPartyDataList) {
        if (thirdPartyDataList == null) {
            return null;
        }
        return thirdPartyDataList.stream()
                .map(this::convertToPanoramaDTO)
                .collect(Collectors.toList());
    }

    /**
     * 批量转换第三方接口数据到人群统计DTO
     */
    public List<CrowdStatisticsDTO> convertToCrowdStatisticsDTOList(List<ThirdPartyDataDTO> thirdPartyDataList) {
        if (thirdPartyDataList == null) {
            return null;
        }
        return thirdPartyDataList.stream()
                .map(this::convertToCrowdStatisticsDTO)
                .collect(Collectors.toList());
    }

    /**
     * 转换分页响应
     */
    public <T> PageResponse<T> convertToPageResponse(Integer pageNo, Integer pageSize, Long totalRows, List<T> rows) {
        PageResponse<T> response = new PageResponse<>();
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalRows(totalRows);
        response.setTotalPage((int) Math.ceil((double) totalRows / pageSize));
        response.setRows(rows);
        
        // 生成分页导航数组
        List<Integer> rainbow = List.of(1, 2, 3); // 简单示例，实际可根据需求生成
        response.setRainbow(rainbow);
        
        return response;
    }
} 