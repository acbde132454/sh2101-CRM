
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改用户</title>
</head>
<body>

<form method="post" action="${pageContext.request.contextPath}/user/saveUpdate">
<input name="id" value="${user.id}" type="hidden"/>
用户名:<input name="name" type="text" value="${user.name}"/><br/>
密码:<input name="loginPwd" type="password"/><br/>
    <input type="submit"/>
</form>
</body>
</html>
