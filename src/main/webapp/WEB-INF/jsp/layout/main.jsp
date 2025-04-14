<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>景区智能分析系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/fontawesome.min.css">
    <style>
        /* 基础布局样式 */
        body {
            margin: 0;
            padding: 0;
            background: #f0f2f5;
            font-family: -apple-system,BlinkMacSystemFont,Segoe UI,PingFang SC,Hiragino Sans GB,Microsoft YaHei,Helvetica Neue,Helvetica,Arial,sans-serif;
        }
        
        /* 左侧菜单 */
        .side-menu {
            position: fixed;
            left: 0;
            top: 0;
            bottom: 0;
            width: 200px;
            background: #001529;
            color: #fff;
            z-index: 1000;
            overflow-y: auto;
        }
        
        .menu-logo {
            height: 60px;
            line-height: 60px;
            text-align: center;
            font-size: 18px;
            font-weight: bold;
            border-bottom: 1px solid rgba(255,255,255,0.1);
        }
        
        .menu-item {
            padding: 12px 24px;
            cursor: pointer;
            transition: all 0.3s;
            display: flex;
            align-items: center;
            color: rgba(255,255,255,0.65);
        }
        
        .menu-item i {
            margin-right: 10px;
            width: 16px;
        }
        
        .menu-item:hover {
            color: #fff;
            background: rgba(255,255,255,0.1);
        }
        
        .menu-item.active {
            color: #fff;
            background: #1890ff;
        }
        
        .submenu {
            padding-left: 24px;
            background: rgba(0,0,0,0.2);
        }
        
        /* 顶部标题栏 */
        .header {
            position: fixed;
            top: 0;
            left: 200px;
            right: 0;
            height: 60px;
            background: #fff;
            padding: 0 24px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            box-shadow: 0 1px 4px rgba(0,21,41,0.08);
            z-index: 999;
        }
        
        .header-title {
            font-size: 18px;
            font-weight: bold;
            color: rgba(0,0,0,0.85);
        }
        
        .header-right {
            display: flex;
            align-items: center;
        }
        
        .user-info {
            margin-left: 16px;
            color: rgba(0,0,0,0.65);
        }
        
        /* 主内容区 */
        .main-content {
            margin-left: 200px;
            padding: 80px 24px 24px;
            min-height: 100vh;
            transition: all 0.3s;
        }
        
        /* 响应式布局 */
        @media (max-width: 768px) {
            .side-menu {
                transform: translateX(-200px);
            }
            .header, .main-content {
                left: 0;
            }
        }
        
        /* 添加加载动画 */
        .loading {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            z-index: 1000;
            display: none;
        }
    </style>
</head>
<body>
    <!-- 左侧菜单 -->
    <div class="side-menu">
        <div class="menu-logo">
            景区智能分析系统
        </div>
        <div class="menu-item" data-url="${pageContext.request.contextPath}/event/list">
            <i class="fas fa-chart-bar"></i>
            <span>事件管理</span>
        </div>
        <div class="menu-item" data-url="${pageContext.request.contextPath}/theme/list">
            <i class="fas fa-desktop"></i>
            <span>主题库</span>
        </div>
        <div class="menu-item" data-url="${pageContext.request.contextPath}/traffic/civilized">
            <i class="fas fa-video"></i>
            <span>交通文明信息主题库</span>
        </div>
        <div class="menu-item" data-url="${pageContext.request.contextPath}/traffic/realtime">
            <i class="fas fa-camera"></i>
            <span>交通实时信息主题库</span>
        </div>
        <div class="menu-item active" data-url="${pageContext.request.contextPath}/video/analysis">
            <i class="fas fa-chart-line"></i>
            <span>视频接入和解析处理主题库</span>
        </div>
        <div class="menu-item" data-url="${pageContext.request.contextPath}/task/list">
            <i class="fas fa-tasks"></i>
            <span>任务管理</span>
        </div>
        <div class="menu-item" data-url="${pageContext.request.contextPath}/system/settings">
            <i class="fas fa-cog"></i>
            <span>系统设置</span>
        </div>
    </div>

    <!-- 顶部标题栏 -->
    <div class="header">
        <div class="header-title">
            视频接入和解析处理主题库
        </div>
        <div class="header-right">
            <span class="user-info">
                <i class="fas fa-user"></i>
                管理员
            </span>
        </div>
    </div>

    <!-- 加载动画 -->
    <div class="loading">
        <div class="spinner-border text-primary" role="status">
            <span class="sr-only">加载中...</span>
        </div>
    </div>

    <!-- 主内容区 -->
    <div class="main-content">
        <div id="content-container">
            <!-- 内容将通过AJAX动态加载 -->
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
    <script>
        $(document).ready(function() {
            // 初始加载默认页面
            loadContent($('.menu-item.active').data('url'));
            
            // 菜单项点击事件
            $('.menu-item').click(function() {
                $('.menu-item').removeClass('active');
                $(this).addClass('active');
                
                // 更新顶部标题
                $('.header-title').text($(this).find('span').text());
                
                // 加载对应的内容
                loadContent($(this).data('url'));
            });
            
            // 加载内容的函数
            function loadContent(url) {
                if (!url) return;
                
                $('.loading').show();
                
                $.ajax({
                    url: url,
                    type: 'GET',
                    success: function(response) {
                        $('#content-container').html(response);
                        // 触发内容加载完成事件
                        $(document).trigger('contentLoaded');
                    },
                    error: function(xhr, status, error) {
                        console.error('加载内容失败:', error);
                        $('#content-container').html('<div class="alert alert-danger">加载内容失败</div>');
                    },
                    complete: function() {
                        $('.loading').hide();
                    }
                });
            }
            
            // 处理浏览器前进/后退
            $(window).on('popstate', function(event) {
                if (event.originalEvent.state) {
                    loadContent(event.originalEvent.state.url);
                }
            });
        });
    </script>
</body>
</html> 