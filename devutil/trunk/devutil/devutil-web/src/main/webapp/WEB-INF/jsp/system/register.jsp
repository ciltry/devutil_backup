<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<jsp:include page="doctype.jsp" flush="true" />
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ui.js"></script>
<script type="text/javascript">



function register() {
	
	
	var req = {};
	req['username'] = $('#username').val();
	req['nickname'] = $('#nickname').val();
	req['password'] = $('#password').val();
	
	if (req['username'] == '') {
		ui.showMessage('用户名不能为空', 'orange');
		return false;
	}
	if (req['nickname'] == '') {
		ui.showMessage('昵称不能为空', 'orange');
		return false;
	}
	if (req['password'] == '') {
		ui.showMessage('密码不能为空', 'orange');
		return false;
	}
	
	if ($('#password').val() != $('#password1').val()) {
		ui.showMessage('输入的密码不一致', 'orange');
		return false;
	}
	
	$.post('${pageContext.request.contextPath}/register.htm', req, function (data) {
		//alert(data);
		try {
			eval('var data=' + data);
		} catch (e) {alert(e.message);}
		if (data['responseCode'] == 'OK') {
			ui.showMessage('注册成功！');
		} else {
			ui.showMessage(data['message'], 'red');
		}
	});
}
</script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Dev Util - 注册</title>
</head>
<body style="margin: 0 auto; text-align: center; background-color: #aaa;">
<form action="#">
<div class="roundCorderC" style="margin-top: 150px;width: 300px; height: 300px;margin-left: auto;margin-right: auto;background-color: gray;overflow: hidden;">
	<div style="color: #555;text-align: left;">
		注 册
	</div>
	<div style="padding-top: 20px; padding-bottom: 30px;">
		<div><input type="text" id="username" placeholder="用户名" /></div>
		<div style="margin-top: 30px;"><input type="text" id="nickname" placeholder="昵称" /></div>
		<div style="margin-top: 30px;"><input type="password" id="password" placeholder="密码" /></div>
		<div style="margin-top: 30px;"><input type="password" id="password1" placeholder="重复密码" /></div>
		
		<div style="text-align: center;margin-top: 30px;">
			<input class="button white" style="width: 60px;" type="reset" value="重置" />
			&nbsp;&nbsp;&nbsp;
			<input class="button white" style="width: 60px;" type="button" value="提交" onclick="register();" />
		</div>
	</div>
</div>
</form>
</body>
</html>