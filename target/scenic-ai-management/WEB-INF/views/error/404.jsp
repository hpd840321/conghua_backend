<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>页面不存在</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6 text-center">
                <h1 class="display-1">404</h1>
                <h2>页面不存在</h2>
                <p class="lead">${error}</p>
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary">返回首页</a>
            </div>
        </div>
    </div>
</body>
</html> 