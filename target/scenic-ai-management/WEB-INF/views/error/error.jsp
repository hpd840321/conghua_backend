<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>错误页面</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-8 offset-md-2">
                <div class="card">
                    <div class="card-header bg-danger text-white">
                        <h4 class="mb-0">发生错误</h4>
                    </div>
                    <div class="card-body">
                        <h5>错误详情：</h5>
                        <p>状态码：${statusCode}</p>
                        <p>错误信息：${errorMessage}</p>
                        <p>请求路径：${requestUri}</p>
                        <c:if test="${not empty exception}">
                            <p>异常信息：${exception}</p>
                        </c:if>
                        
                        <div class="mt-4">
                            <a href="/" class="btn btn-primary">返回首页</a>
                            <button onclick="history.back()" class="btn btn-secondary ml-2">返回上一页</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html> 