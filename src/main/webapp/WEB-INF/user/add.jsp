
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增用户</title>
</head>
<body>

<form method="post" action="${pageContext.request.contextPath}/user/save">

用户名:<input name="name" type="text"/><br/>
密码:<input name="loginPwd" type="password"/><br/>
    <input type="submit"/>
</form>
</body>
</html>
