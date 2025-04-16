package com.scenic.ai.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 第三方API服务
 * <p>
 * 该服务负责与外部第三方系统进行交互，获取各类数据。
 * 主要功能包括：
 * 1. 下载图片资源
 * 2. 获取高密度区域数据
 * 3. 获取统计数据（人流量统计）
 * 4. 获取详细数据（具体记录）
 * <p>
 * 该服务实现了数据同步模块的核心功能，支持系统从第三方API获取数据，
 * 并通过数据映射转换保持与现有系统的兼容性。
 * <p>
 * 所有方法都包含完善的错误处理和日志记录，确保系统稳定性和可追踪性。
 *
 * @author 景区AI管理系统开发团队
 * @version 1.0
 */
@Service
public class ThirdPartyApiService {

    static final Logger log = LoggerFactory.getLogger(ThirdPartyApiService.class);

    /**
     * RestTemplate实例，用于发送HTTP请求
     * 通过构造函数注入，支持自定义配置（如代理、超时设置等）
     */
    private final RestTemplate restTemplate;

    /**
     * ObjectMapper实例，用于JSON序列化和反序列化
     * 通过构造函数注入，支持自定义配置（如日期格式处理等）
     */
    private final ObjectMapper objectMapper;

    /**
     * 第三方API的基础URL
     * 从配置文件中读取，默认值为http://localhost:8080
     */
    @Value("${api.base-url:http://172.26.46.25:30081}")
    private String baseUrl;

    /**
     * API路径前缀
     * 从配置文件中读取，默认值为/api
     */
    @Value("${third-party.api-path:/api}")
    private String apiPath;

    /**
     * 构造函数
     * 
     * @param restTemplate RestTemplate实例
     * @param objectMapper ObjectMapper实例
     */
    public ThirdPartyApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 获取统计数据
     */
    public List<Map<String, Object>> getStatisticsData(LocalDateTime startTime) {
        String url = baseUrl + "/api/statistics";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {
                });

        return response.getBody();
    }

    /**
     * 获取详细数据
     */
    public List<Map<String, Object>> getDetailedData(String deviceCode, String algName, LocalDateTime startTime,
            LocalDateTime endTime) {
        log.info("获取详细数据: deviceCode={}, algName={}, startTime={}, endTime={}", deviceCode, algName, startTime,
                endTime);
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("pageSize", 100);
            request.put("deviceCode", deviceCode);
            request.put("algName", algName);
            request.put("recordBeginDate", formatDate(startTime));
            request.put("recordEndDate", formatDate(endTime));

            List<Map<String, Object>> allResults = new ArrayList<>();
            int pageNo = 1;

            Map<String, Object> response;
            do {
                request.put("pageNo", pageNo);
                response = fetchDetailedPage(request);

                if (response != null && response.containsKey("data")) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> data = (Map<String, Object>) response.get("data");
                    if (data != null && data.containsKey("rows")) {
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> rows = (List<Map<String, Object>>) data.get("rows");
                        if (rows != null && !rows.isEmpty()) {
                            allResults.addAll(rows);
                            pageNo++;
                        }
                    }
                } else {
                    break;
                }
            } while (response != null && isMorePages(response));

            log.info("成功获取{}条详细数据", allResults.size());
            return allResults;
        } catch (Exception e) {
            log.error("获取详细数据失败", e);
            return new ArrayList<>();
        }
    }

    /**
     * 下载图片
     */
    public byte[] downloadImage(String imageUrl) {
        return restTemplate.getForObject(imageUrl, byte[].class);
    }

    /**
     * 构建请求URL
     */
    private String buildUrl(String path) {
        return UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .path(apiPath)
                .path(path)
                .toUriString();
    }

    /**
     * 构建请求头
     */
    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    /**
     * 获取统计数据分页
     * 注意：此方法当前未在本地使用，但保留以备将来扩展
     */
    @SuppressWarnings("unused")
    private Map<String, Object> fetchStatisticsPage(Map<String, Object> request) {
        try {
            String url = buildUrl("/client/third/getPage");

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, buildHeaders());

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {
                });
            }

            return null;
        } catch (Exception e) {
            log.error("获取统计数据分页失败", e);
            return null;
        }
    }

    /**
     * 获取详细数据分页
     */
    private Map<String, Object> fetchDetailedPage(Map<String, Object> request) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .path(apiPath + "/client/third/getDetailsPage")
                    .queryParam("pageNo", request.get("pageNo"))
                    .queryParam("pageSize", request.get("pageSize"))
                    .queryParam("deviceCode", request.get("deviceCode"))
                    .queryParam("algName", request.get("algName"))
                    .queryParam("recordBeginDate", request.get("recordBeginDate"))
                    .queryParam("recordEndDate", request.get("recordEndDate"))
                    .toUriString();

            HttpEntity<String> entity = new HttpEntity<>(buildHeaders());
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {
                });
            }

            return null;
        } catch (Exception e) {
            log.error("获取详细数据分页失败", e);
            return null;
        }
    }

    /**
     * 检查是否还有更多页
     */
    private boolean isMorePages(Map<String, Object> response) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) response.get("data");
            int pageNo = (int) data.get("pageNo");
            int totalPage = (int) data.get("totalPage");
            return pageNo < totalPage;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 格式化日期
     */
    private String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
