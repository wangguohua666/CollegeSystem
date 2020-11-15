<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>模拟登录成功</title>
</head>
<body>
模拟登录成功页面
<br/>
<c:if test="${url != null && url != ''}">
<a href="${url}">回到刚才访问地址</a><br/>
</c:if>
<a href="${pageContext.request.contextPath}/test/success">回到模拟成功页</a>
</body>
</html>