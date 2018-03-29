﻿<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<%@include file="/common/constants.jsp"%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<head>
<link rel="stylesheet"
	href="<%=path%>/static/js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path%>/static/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=path%>/static/css/index.css">
<link rel="stylesheet" href="<%=path%>/static/css/skins/_all-skins.css">
<!-- 动画css -->
<link rel="stylesheet" href="<%=path%>/static/css/animate.css">
<title>管理系统</title>
</head>
<body class="hold-transition skin-blue sidebar-mini"
	style="overflow:hidden;">
	<div id="ajax-loader"
		style="cursor: progress; position: fixed; top: -50%; left: -50%; width: 200%; height: 200%; background: #fff; z-index: 10000; overflow: hidden;">
		<img src="static/img/ajax-loader.gif"
			style="position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: auto;" />
	</div>
	<div class="wrapper">
		<!--头部信息-->
		<header class="main-header">
			<a href="#" target="_blank" class="logo"> <span class="logo-mini">MS</span>
				<span class="logo-lg"><strong>管理系统</strong> </span> </a>
			<nav class="navbar navbar-static-top">
				<a class="sidebar-toggle"> <span class="sr-only">Toggle
						navigation</span> </a>
				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<li class="dropdown messages-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="fa fa-envelope-o "></i> <span class="label label-success">4</span>
						</a>
						</li>
						<li class="dropdown notifications-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="fa fa-bell-o"></i> <span class="label label-warning">10</span>
						</a>
						</li>
						<li class="dropdown tasks-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="fa fa-flag-o"></i> <span class="label label-danger">9</span>
						</a>
						</li>
						<li class="dropdown user user-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <img
								src="static/img/user2-160x160.jpg" class="user-image"
								alt="User Image"> <span class="hidden-xs"><shiro:principal
										property="realName" /> ( <shiro:principal property="username" />
									)</span> </a>
							<ul class="dropdown-menu pull-right">
								<li><a class="menuItem" data-id="userInfo"
									href="/SystemManage/User/Info"><i class="fa fa-user"></i>个人信息</a>
								</li>
								<li><a href="javascript:void();"><i
										class="fa fa-trash-o"></i>清空缓存</a></li>
								<li><a href="javascript:void();"><i
										class="fa fa-paint-brush"></i>皮肤设置</a></li>
								<li class="divider"></li>
								<li><a href="#" id="logout"><i
										class="ace-icon fa fa-power-off"></i>安全退出</a></li>
							</ul>
						</li>
					</ul>
				</div>
			</nav>
		</header>
		<!--左边导航-->
		<div class="main-sidebar">
			<div class="sidebar">
				<div class="user-panel">
					<div class="pull-left image">
						<img src="static/img/user2-160x160.jpg" class="img-circle"
							alt="User Image">
					</div>
					<div class="pull-left info">
						<p>
							<shiro:principal property="realName" />
							(
							<shiro:principal property="username" />
							)
						</p>
						<a><i class="fa fa-circle text-success"></i>在线</a>
					</div>
				</div>
				<!-- <form action="#" method="get" class="sidebar-form">
					<div class="input-group">
						<input type="text" name="functionName" class="form-control"
							placeholder="Search..."> <span class="input-group-btn">
							<a class="btn btn-flat"><i class="fa fa-search"></i> </a> </span>
					</div>
				</form> -->
				<ul class="sidebar-menu" id="sidebar-menu">
					<!--<li class="header">导航菜单</li>-->
				</ul>
			</div>
		</div>
		<!--中间内容-->
		<div id="content-wrapper" class="content-wrapper">
			<div class="content-tabs">
				<button class="roll-nav roll-left tabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs menuTabs">
					<div class="page-tabs-content" style="margin-left: 0px;">
						<a href="javascript:;" class="menuTab active" data-id="welcome">欢迎首页</a>
					</div>
				</nav>
				<button class="roll-nav roll-right tabRight">
					<i class="fa fa-forward" style="margin-left: 3px;"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown tabClose" data-toggle="dropdown">
						页签操作<i class="fa fa-caret-down" style="padding-left: 3px;"></i>
					</button>
					<ul class="dropdown-menu dropdown-menu-right">
						<li><a class="tabReload" href="javascript:void();">刷新当前</a></li>
						<li><a class="tabCloseCurrent" href="javascript:void();">关闭当前</a>
						</li>
						<li><a class="tabCloseAll" href="javascript:void();">全部关闭</a>
						</li>
						<li><a class="tabCloseOther" href="javascript:void();">除此之外全部关闭</a>
						</li>
					</ul>
				</div>
				<button class="roll-nav roll-right fullscreen">
					<i class="fa fa-arrows-alt"></i>
				</button>
			</div>
			<div class="content-iframe" style="overflow: hidden;">
				<div class="mainContent" id="content-main"
					style="margin: 10px; margin-bottom: 0px; padding: 0;">
					<iframe class="LRADMS_iframe" width="100%" height="100%"
						src="welcome" frameborder="0" data-id="welcome"></iframe>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

<script type="text/javascript"
	src="<%=path%>/static/js/jquery/jQuery-2.2.0.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/static/js/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/main.js"></script>
<!-- 操作弹框提醒 -->
<script type="text/javascript"
	src="<%=path%>/static/js/bootstrap/bootstrap-notify/bootstrap-notify.min.js"></script>