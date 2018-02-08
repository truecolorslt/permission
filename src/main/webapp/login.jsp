<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/common/include.jsp"%>

<!-- Custom styles for this template -->
<link rel="stylesheet" href="<%=path%>/static/css/signin.css">

<title>登录页面</title>
</head>
<body>
	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="col-lg-4 col-lg-offset-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">登录</h3>
					</div>
					<div class="panel-body">
						<form id="loginForm" method="post" class="form-signin">
							<div class="form-group">
								<label>用户账号</label> <input type="text" class="form-control"
									name="username" id="username" placeholder="用户账号" />
								<!-- required autofocus -->
							</div>
							<div class="form-group">
								<label>用户密码</label> <input type="password" class="form-control"
									name="password" id="password" placeholder="用户密码" />
								<!-- required -->
							</div>
							<div class="form-group" align="center">
								<button id="loginBtn"
									class="btn btn-lg btn-primary btn-block ladda-button"
									data-style="zoom-in">
									<span id="loginSpan" class="ladda-label">登录</span>
								</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- /container -->
</body>
</html>

<!-- 自定义js -->
<script src="<%=path%>/static/js/login.js"></script>