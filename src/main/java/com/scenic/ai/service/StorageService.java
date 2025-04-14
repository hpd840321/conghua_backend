package com.scenic.ai.service;

import com.scenic.ai.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 存储服务，用于处理图片和其他文件的存储、检索和删除
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {
    private final ThirdPartyApiService thirdPartyApiService;

    @Value("${storage.base-path}")
    private String storagePath;

    /**
     * 从第三方API下载图片并存储到本地
     *
     * @param imageUrl 图片URL
     * @return 存储路径
     * @throws IOException 如果下载或存储过程中发生错误
     */
    @Transactional
    @CacheEvict(value = "imageCache", allEntries = true)
    public String downloadAndStore(String imageUrl) throws IOException {
        try {
            log.info("Downloading image from URL: {}", imageUrl);
            byte[] imageData = thirdPartyApiService.downloadImage(imageUrl);
            return saveImage(imageData);
        } catch (Exception e) {
            log.error("Failed to download and store image from URL: {}", imageUrl, e);
            throw new IOException("Failed to download and store image: " + e.getMessage(), e);
        }
    }

    /**
     * 保存图片数据到本地存储
     *
     * @param imageData 图片数据
     * @return 存储路径
     * @throws IOException 如果存储过程中发生错误
     */
    private String saveImage(byte[] imageData) throws IOException {
        String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String fileName = UUID.randomUUID().toString() + ".jpg";
        Path fullPath = Paths.get(storagePath, datePath, fileName);
        
        try {
            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath, imageData);
            log.info("Saved image to: {}", fullPath);
            return datePath + "/" + fileName;
        } catch (IOException e) {
            log.error("Failed to save image to: {}", fullPath, e);
            throw new IOException("Failed to save image: " + e.getMessage(), e);
        }
    }

    /**
     * 检索图片数据
     *
     * @param imagePath 图片路径
     * @return 图片数据
     * @throws IOException 如果检索过程中发生错误
     */
    @Cacheable(value = "imageCache", key = "#imagePath")
    public byte[] retrieveImage(String imagePath) throws IOException {
        Path fullPath = Paths.get(storagePath, imagePath);
        try {
            if (Files.exists(fullPath) && Files.isReadable(fullPath)) {
                log.debug("Retrieved image from: {}", fullPath);
                return Files.readAllBytes(fullPath);
            } else {
                log.warn("Image not found: {}", fullPath);
                throw new ResourceNotFoundException("Image not found: " + imagePath);
            }
        } catch (IOException e) {
            log.error("Failed to retrieve image: {}", imagePath, e);
            throw new IOException("Failed to retrieve image: " + e.getMessage(), e);
        }
    }
    
    /**
     * 删除文件
     *
     * @param imagePath 文件路径
     * @return 是否删除成功
     */
    @Transactional
    @CacheEvict(value = "imageCache", key = "#imagePath")
    public boolean deleteFile(String imagePath) {
        try {
            Path fullPath = Paths.get(storagePath, imagePath);
            if (Files.exists(fullPath)) {
                Files.delete(fullPath);
                log.info("Deleted file: {}", fullPath);
                
                // 尝试删除空目录
                Path parent = fullPath.getParent();
                if (Files.exists(parent) && Files.isDirectory(parent)) {
                    if (Files.list(parent).count() == 0) {
                        Files.delete(parent);
                        log.info("Deleted empty directory: {}", parent);
                    }
                }
                
                return true;
            } else {
                log.warn("File not found for deletion: {}", fullPath);
                return false;
            }
        } catch (IOException e) {
            log.error("Error deleting file: {}", imagePath, e);
            return false;
        }
    }
}
