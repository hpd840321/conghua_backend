-- 创建区域表
CREATE TABLE t_area (
    id NUMBER(20) PRIMARY KEY,
    area_name VARCHAR2(100) NOT NULL,
    description VARCHAR2(500),
    status NUMBER(1) DEFAULT 1,
    create_time TIMESTAMP DEFAULT SYSDATE,
    update_time TIMESTAMP DEFAULT SYSDATE
);

-- 创建设备表
CREATE TABLE t_device (
    id NUMBER(20) PRIMARY KEY,
    device_name VARCHAR2(100) NOT NULL,
    area_id NUMBER(20) NOT NULL,
    device_type VARCHAR2(50),
    status VARCHAR2(20),
    ip_address VARCHAR2(50),
    create_time TIMESTAMP DEFAULT SYSDATE,
    update_time TIMESTAMP DEFAULT SYSDATE
);

-- 创建人群计数表
CREATE TABLE t_crowd_count (
    id NUMBER(20) PRIMARY KEY,
    area_id NUMBER(20) NOT NULL,
    device_id NUMBER(20) NOT NULL,
    count NUMBER(10),
    panorama_image_url VARCHAR2(500),
    flow_pressure NUMBER(1),
    count_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT SYSDATE,
    update_time TIMESTAMP DEFAULT SYSDATE
);

-- 创建告警表
CREATE TABLE t_alert (
    id NUMBER(20) PRIMARY KEY,
    area_id NUMBER(20) NOT NULL,
    device_id NUMBER(20) NOT NULL,
    alert_type VARCHAR2(50),
    alert_content VARCHAR2(500),
    alert_level NUMBER(1),
    status NUMBER(1) DEFAULT 0,
    alert_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT SYSDATE,
    update_time TIMESTAMP DEFAULT SYSDATE
);

-- 插入测试数据
INSERT INTO t_area (id, area_name, description) VALUES (1, '入口区域', '景区主入口');
INSERT INTO t_area (id, area_name, description) VALUES (2, '中心广场', '景区中心休息区');
INSERT INTO t_area (id, area_name, description) VALUES (3, '景点A', '主要景点A区域');

INSERT INTO t_device (id, device_name, area_id, device_type, status, ip_address)
VALUES (1, '入口摄像头1', 1, 'CAMERA', 'ONLINE', '192.168.1.100');
INSERT INTO t_device (id, device_name, area_id, device_type, status, ip_address)
VALUES (2, '广场摄像头1', 2, 'CAMERA', 'ONLINE', '192.168.1.101');
INSERT INTO t_device (id, device_name, area_id, device_type, status, ip_address)
VALUES (3, '景点A摄像头1', 3, 'CAMERA', 'ONLINE', '192.168.1.102');

-- 插入人群计数测试数据
INSERT INTO t_crowd_count (id, area_id, device_id, count, flow_pressure, count_time)
VALUES (1, 1, 1, 50, 1, SYSDATE);
INSERT INTO t_crowd_count (id, area_id, device_id, count, flow_pressure, count_time)
VALUES (2, 2, 2, 150, 2, SYSDATE);
INSERT INTO t_crowd_count (id, area_id, device_id, count, flow_pressure, count_time)
VALUES (3, 3, 3, 80, 1, SYSDATE);

-- 插入告警测试数据
INSERT INTO t_alert (id, area_id, device_id, alert_type, alert_content, alert_level, alert_time)
VALUES (1, 1, 1, '人群密度', '入口区域人群密度超过阈值', 2, SYSDATE);
INSERT INTO t_alert (id, area_id, device_id, alert_type, alert_content, alert_level, alert_time)
VALUES (2, 2, 2, '异常行为', '中心广场发现异常聚集', 3, SYSDATE);
INSERT INTO t_alert (id, area_id, device_id, alert_type, alert_content, alert_level, alert_time)
VALUES (3, 3, 3, '设备异常', '景点A摄像头网络异常', 1, SYSDATE); 