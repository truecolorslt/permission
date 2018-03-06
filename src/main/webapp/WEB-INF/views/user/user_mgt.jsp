<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/common/include.jsp"%>

<link rel="stylesheet" href="<%=path%>/static/css/table.css">
<link rel="stylesheet" href="<%=path%>/static/css/modal.css">

<link rel="stylesheet"
	href="<%=path%>/static/css/plugins/jqgrid/ui.jqgrid.css?0820">

<title>用户管理</title>
</head>
<body style="background-color: transparent;">
	<div id="areascontent" style="height: 100%">
		<div class="rows"
			style="margin-bottom: 0.8%; overflow: hidden;height: 98%">
			<div style="float: left; width: 100%;height: 100%">
				<div class="panel panel-default" style="height: 95%">
					<form id="formSearch" class="form-horizontal">
						<div class="form-group" style="margin-top:15px">
							<label class="control-label col-sm-1" style="" for="realName">姓名</label>
							<div class="col-sm-2">
								<input type="text" class="form-control" id="realName"
									name="realName">
							</div>

							<label class="control-label col-sm-1 style=" " for="username">账号</label>
							<div class="col-sm-2">
								<input type="text" class="form-control" id="username"
									name="username">
							</div>

							<label class="control-label col-sm-1" style="" for="dname">部门</label>
							<div class="col-sm-3 has-feedback">
								<span
									class="glyphicon glyphicon-collapse-down form-control-feedback"
									style="font-size: 18px"></span> <input type="text"
									class="form-control" id="dname" name="dname" placeholder="全部"
									onclick="showMenu()"
									style="cursor:pointer;background-color: white;" readonly /> <input
									type="hidden" id="did" name="did">
								<div id="deptMenu" class="menuContent"
									style="display:none;position: absolute;
												width:87%;border:1px solid rgb(170,170,170);
												z-index:1;background-color:white;overflow-y:auto;max-height: 270px;">
									<ul id="deptTree" class="ztree"
										style="margin-top:0; height:auto;"></ul>
								</div>
							</div>
							<div class="col-sm-12" style="text-align:center;">&nbsp;</div>
							<div class="col-sm-12" style="text-align:center;">
								<button type="button" id="find_btn" class="btn btn-primary">
									<i class="fa fa-search" aria-hidden="true"></i>查询
								</button>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<button class="btn" id="resetBtn">
									<i class="fa fa-reply" aria-hidden="true"></i>重置
								</button>
							</div>
						</div>
					</form>
					<hr>
					<div class="panel-body" style="height: 100%">
						<div class="jqGrid_wrapper" style="height: 100%">
							<table id="table_list" style="height: 100%"></table>
							<div id="pager_list"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 新增用户div -->
	<div class="modal fade" id="userModal" tabindex="-1" role="dialog"
		aria-labelledby="userModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="userModalLabel">新增</h4>
				</div>
				<div class="modal-body">
					<div class="ibox float-e-margins">
						<div class="ibox-content">
							<form class="form-horizontal m-t" id="newUserForm">
								<div class="form-group">
									<label class="col-sm-3 control-label"><span
										class="text-danger">*</span>账号：</label>
									<div class="col-sm-6">
										<input id="username_add" name="username_add" type="text"
											class="form-control" placeholder="账号（必填项）">
									</div>
									<div class="col-sm-2 mblack"></div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label"><span
										class="text-danger">*</span>密码：</label>
									<div class="col-sm-6">
										<input id="password_add" name="password_add" type="text"
											class="form-control" placeholder="密码（必填项）">
									</div>
									<div class="col-sm-2 mblack"></div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label"><span
										class="text-danger">*</span>姓名：</label>
									<div class="col-sm-6">
										<input id="realName_add" name="realName_add" type="text"
											class="form-control" placeholder="姓名（必填项）">
									</div>
									<div class="col-sm-2 mblack"></div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label"><span
										class="text-danger">*</span>部门：</label>
									<div class="col-sm-6 has-feedback">
										<span
											class="glyphicon glyphicon-collapse-down form-control-feedback"
											style="font-size: 18px"></span> <input type="text"
											class="form-control" id="dname_add" name="dname_add"
											placeholder="部门（必填项）" onclick="showMenuAdd()"
											style="cursor:pointer;background-color: white;" readonly />
										<input type="hidden" id="did_add" name="did_add">
										<div id="deptMenuAdd" class="menuContent"
											style="display:none;position: absolute;
												width:87%;border:1px solid rgb(170,170,170);
												z-index:1;background-color:white;overflow-y:auto;max-height: 270px;">
											<ul id="deptTreeAdd" class="ztree"
												style="margin-top:0; height:auto;"></ul>
										</div>
									</div>
									<div class="col-sm-2 mblack"></div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label">昵称：</label>
									<div class="col-sm-6">
										<input id="nickName_add" type="text" class="form-control"
											name="nickName_add" placeholder="昵称">
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label">性别：</label>
									<div class="col-sm-6">
										<input id="sex_add" type="text" class="form-control"
											name="sex_add" placeholder="性别">
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label">备注：</label>
									<div class="col-sm-6">
										<input id="remark_add" type="text" class="form-control"
											name="remark_add" placeholder="备注">
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button id="btn_save_new" class="btn btn-primary ladda-button"
						data-style="zoom-in">
						<span class="glyphicon glyphicon-floppy-disk ladda-label">保存</span>
					</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
					</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改用户div -->
	<div class="modal fade" id="userUpdateModal" tabindex="-1"
		role="dialog" aria-labelledby="userUpdateModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="userUpdateModalLabel">编辑</h4>
				</div>
				<div class="modal-body">
					<div class="ibox float-e-margins">
						<div class="ibox-content">
							<form class="form-horizontal m-t" id="updateUserForm">
								<input type="hidden" name="uid_update" id="uid_update">
								<div class="form-group">
									<label class="col-sm-3 control-label">账号：</label>
									<div class="col-sm-6">
										<p class="form-control-static text-muted">
											<font size="2"><strong><span
													id="username_update"></span> </strong> </font>
										</p>
									</div>
									<div class="col-sm-2 mblack"></div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label"><span
										class="text-danger">*</span>姓名：</label>
									<div class="col-sm-6">
										<input id="realName_update" name="realName_update" type="text"
											class="form-control" placeholder="姓名（必填项）">
									</div>
									<div class="col-sm-2 mblack"></div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label"><span
										class="text-danger">*</span>部门：</label>
									<div class="col-sm-6 has-feedback">
										<span
											class="glyphicon glyphicon-collapse-down form-control-feedback"
											style="font-size: 18px"></span> <input type="text"
											class="form-control" id="dname_update" name="dname_update"
											placeholder="部门（必填项）" onclick="showMenuUpdate()"
											style="cursor:pointer;background-color: white;" readonly />
										<input type="hidden" id="did_update" name="did_update">
										<div id="deptMenuUpdate" class="menuContent"
											style="display:none;position: absolute;
												width:87%;border:1px solid rgb(170,170,170);
												z-index:1;background-color:white;overflow-y:auto;max-height: 270px;">
											<ul id="deptTreeUpdate" class="ztree"
												style="margin-top:0; height:auto;"></ul>
										</div>
									</div>
									<div class="col-sm-2 mblack"></div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label">昵称：</label>
									<div class="col-sm-6">
										<input id="nickName_update" type="text" class="form-control"
											name="nickName_update" placeholder="昵称">
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label">性别：</label>
									<div class="col-sm-6">
										<input id="sex_update" type="text" class="form-control"
											name="sex_update" placeholder="性别">
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label">备注：</label>
									<div class="col-sm-6">
										<input id="remark_update" type="text" class="form-control"
											name="remark_update" placeholder="备注">
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button id="btn_save_update" class="btn btn-primary ladda-button"
						data-style="zoom-in">
						<span class="glyphicon glyphicon-floppy-disk ladda-label">保存</span>
					</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
					</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 重置密码div -->
	<div class="modal fade" id="pwdUpdateModal" tabindex="-1" role="dialog"
		aria-labelledby="pwdUpdateModalLabel">
		<div class="modal-dialog" role="document"
			style="width: 60%;height: 60%">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="pwdUpdateModalLabel">重置密码</h4>
				</div>
				<div class="modal-body">
					<div class="ibox float-e-margins">
						<div class="ibox-content">
							<form class="form-horizontal m-t" id="updatePwdForm">
								<input type="hidden" name="uid_pwd" id="uid_pwd">
								<div class="form-group">
									<label class="col-sm-3 control-label">用户姓名：</label>
									<div class="col-sm-2">
										<p class="form-control-static text-muted">
											<font size="2"><strong><span
													id="realName_pwd"></span> </strong> </font>
										</p>
									</div>
									<label class="col-sm-3 control-label">用户账号：</label>
									<div class="col-sm-3">
										<p class="form-control-static text-muted">
											<font size="2"><strong><span
													id="username_pwd"></span> </strong> </font>
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label"><span
										class="text-danger">*</span>新密码：</label>
									<div class="col-sm-6">
										<input id="password_pwd" name="password_pwd" type="text"
											class="form-control" placeholder="新密码（必填项）">
									</div>
									<div class="col-sm-2 mblack"></div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button id="btn_pwd_update" class="btn btn-primary ladda-button"
						data-style="zoom-in">
						<span class="glyphicon glyphicon-floppy-disk ladda-label">保存</span>
					</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
					</button>
				</div>
			</div>
		</div>
	</div>
</body>

<!-- js插件 -->
<!-- jqGrid表格 -->
<script
	src="<%=path%>/static/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script
	src="<%=path%>/static/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>

<script src="<%=path%>/static/assets/js/zTree/jquery.ztree.all-3.5.js"></script>

<!-- 自定义js -->
<script src="<%=path%>/static/js/user/user_mgt.js"></script>