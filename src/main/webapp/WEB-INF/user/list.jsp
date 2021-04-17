<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户列表</title>
</head>
<body>

<a href="${pageContext.request.contextPath}/user/add">添加用户</a><br/>

<c:forEach var="user" items="${userList}">


    ${user.id},${user.name}
    <a href="${pageContext.request.contextPath}/user/update?id=${user.id}">修改 </a>
    <a href="${pageContext.request.contextPath}/user/del?id=${user.id}">删除 </a>


    <br/>
</c:forEach>


</body>
</html>
