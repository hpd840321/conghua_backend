<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>景区管理系统</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcdn.net/ajax/libs/echarts/5.4.3/echarts.min.js" rel="stylesheet">
    <style>
        .sidebar {
            position: fixed;
            top: 0;
            bottom: 0;
            left: 0;
            z-index: 100;
            padding: 48px 0 0;
            box-shadow: inset -1px 0 0 rgba(0, 0, 0, .1);
            background-color: #f8f9fa;
        }
        .main-content {
            margin-left: 240px;
            padding: 20px;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- 侧边栏 -->
            <nav class="col-md-3 col-lg-2 d-md-block sidebar">
                <div class="position-sticky pt-3">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link active" href="#" data-page="crowd-count">
                                人群统计
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#" data-page="alert-center">
                                告警中心
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <!-- 主要内容区域 -->
            <main class="main-content col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div id="content-area"></div>
            </main>
        </div>
    </div>

    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/echarts/5.4.3/echarts.min.js"></script>
    <script>
        $(document).ready(function() {
            // 默认加载人群统计页面
            loadPage('crowd-count');

            // 导航点击事件
            $('.nav-link').click(function(e) {
                e.preventDefault();
                $('.nav-link').removeClass('active');
                $(this).addClass('active');
                loadPage($(this).data('page'));
            });
        });

        function loadPage(page) {
            $('#content-area').load('/pages/' + page);
        }
    </script>
</body>
</html> 