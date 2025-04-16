<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>错误页面</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.2.3/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .error-template {
            padding: 40px 15px;
            text-align: center;
        }
        .error-actions {
            margin-top: 15px;
            margin-bottom: 15px;
        }
        .error-actions .btn {
            margin-right: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="error-template">
                    <h1>出错了!</h1>
                    <h2>错误代码: ${status}</h2>
                    <div class="error-details">
                        ${error}
                        <br>
                        ${message}
                    </div>
                    <div class="error-actions">
                        <a href="/" class="btn btn-primary">
                            <i class="icon-home icon-white"></i> 返回首页
                        </a>
                        <a href="javascript:history.back()" class="btn btn-default">
                            返回上一页
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.2.3/js/bootstrap.bundle.min.js"></script>
</body>
</html> 