<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<jsp:include page="doctype.jsp" flush="true" />
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Dev Util</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
function login() {
	var username = $('#username').val();
	var password = $('#password').val();
	if (username == '') {
		ui.showMessage('请输入用户名', 'orange');
	} else if (password == '') {
		ui.showMessage('请输入密码', 'orange');
	} else {
		$.post('${pageContext.request.contextPath}/login.htm', {username: username, password: password}, function(data) {
			try {
				eval('data = ' + data);
			} catch (e) {}
			if (typeof (data) == 'object') {
				if (data['responseCode'] == 'OK') {
					window.location.href = '${pageContext.request.contextPath}/workspace.htm'
				} else if (data['message'] != null) {
					ui.showMessage(data['message'], 'red');
				} else {
					ui.showMessage('登录失败', 'red')
				}
			}
		});
	}
}
</script>
</head>
<body style="margin: 0 auto; text-align: center; background-color: #aaa;">

<div class="roundCorderC" style="margin-top: 150px;width: 300px; height: 230px;margin-left: auto;margin-right: auto;background-color: gray;">
	<div style="font-size: 24px;color: #555;">
		Dev Util
	</div>
	<div style="padding-top: 20px; padding-bottom: 30px;">
		<div><input type="text" id="username" placeholder="用户名" /></div>
		<div style="margin-top: 30px;"><input type="text" id="password" placeholder="密码" /></div>
		
		<div style="text-align: center;margin-top: 30px;">
			<input class="button white" type="button" value="登入" onclick="login();" />
		</div>
		<div style="margin-top: 15px;">
			<a href="${pageContext.request.contextPath}/register.htm">注&nbsp;&nbsp;册</a>
		</div>
	</div>
</div>
</body>
</html>