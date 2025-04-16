CREATE TABLE alert (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tourism_name VARCHAR(100) NOT NULL COMMENT '景区名称',
    device_code VARCHAR(50) NOT NULL COMMENT '设备编码',
    alert_type VARCHAR(50) NOT NULL COMMENT '告警类型',
    alert_level INT NOT NULL COMMENT '告警等级：1-一般，2-重要，3-紧急',
    alert_content TEXT NOT NULL COMMENT '告警内容',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-未处理，1-处理中，2-已处理',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    INDEX idx_tourism_device (tourism_name, device_code),
    INDEX idx_create_time (create_time),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警信息表'; 