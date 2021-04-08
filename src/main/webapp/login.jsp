<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="/crm/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="/crm/jquery/layer.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/crm/jquery/layer.js"></script>
<script type="text/javascript" src="/crm/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
</head>
<body>
    <%--<a href="javascript:void(0)" onclick="location.href='http://www.baidu.com'">去百度</a>--%>

	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="/crm/image/IMG_7114.JPG" style="width: 100%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2020&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="/crm/settings/user/login" class="form-horizontal" role="form" method="post">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input name="loginAct" id="loginAct" class="form-control" type="text" placeholder="用户名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" name="loginPwd" id="loginPwd" type="password" placeholder="密码">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<span id="msg" style="color: red">${message}</span>
					</div>
					<%--
						type=submit,默认按aaction提交的
					--%>
					<%--<input type="submit" value="">--%>
					<button type="button" onclick="login()" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>

<script>
	function login(){
		$.post("/crm/settings/user/loginAsync",{
			'loginAct' : $('#loginAct').val(),
			'loginPwd' : $('#loginPwd').val()
		},function(data){
			if(data.ok){
				layer.alert(data.message, {icon: 6});
				//跳转到首页,不能使用该方式直接跳转到页面，只能向后台发送跳转到首页的请求
				location.href = "/crm/toView/workbench/index";
			}else{
				layer.alert(data.message, {icon: 5});
			}
		},'json')
	}

</script>
</body>
</html>