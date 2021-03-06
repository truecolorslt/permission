<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/common/include.jsp"%>

<title>部门管理</title>
</head>
<body style="background-color: transparent">
	<div id="areascontent" style="height: 100%">
		<div class="rows"
			style="margin-bottom: 0.8%; overflow: hidden;height: 98%">
			<!-- 树形菜单 -->
			<div style="float: left; width: 35%;height: 100%">
				<div class="panel panel-default" style="height: 95%">
					<div class="panel-heading">
						<i class="fa fa-tree" style="padding-right: 5px;"></i>部门组织结构
					</div>
					<div class="panel-body" style="height:430px; overflow:auto;">
						<ul id="departmentTree" class="ztree"></ul>
					</div>
				</div>
			</div>
			<!-- 部门信息 -->
			<div style="float: right; width: 64.2%;height: 100%">
				<div class="panel panel-default" style="height: 95%">
					<div class="panel-heading">
						<i class="fa fa-info" style="padding-right: 5px;"></i>部门信息修改
					</div>
					<div class="panel-body" style="height:430px; overflow:auto;">
						<form class="form-horizontal m-t" id="updateDepartmentForm">
							<input type="hidden" name="did" id="did">
							<div class="form-group">
								<label class="col-sm-3 control-label">上级部门名称：</label>
								<div class="col-sm-8">
									<p class="form-control-static text-muted">
										<font size="2"><strong><span
												id="pDepartmentName"></span> </strong> </font>
									</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="text-danger">*</span>部门名称：</label>
								<div class="col-sm-6">
									<input id="dname" name="dname" type="text" class="form-control"
										placeholder="部门名称（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="text-danger">*</span>部门编码：</label>
								<div class="col-sm-6">
									<input id="dcode" type="text" class="form-control" name="dcode"
										placeholder="部门编码（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">部门顺序：</label>
								<div class="col-sm-6">
									<input id="dsort" type="text" class="form-control" name="dsort"
										placeholder="部门顺序（1～100的数字）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

						</form>

						<div class="form-group">
							<p align="center">
								<button id="btn_save_update"
									class="btn btn-primary ladda-button" data-style="zoom-in">
									<span class="glyphicon glyphicon-floppy-disk ladda-label">保存</span>
								</button>
							</p>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 新增、编辑div -->
	<%@ include file="dept_opt.jsp"%>
</body>
</html>
<!-- js插件 -->
<!-- 树形菜单ztree -->
<script src="<%=path%>/static/js/plugins/ztree/jquery.ztree.core.js"></script>
<script src="<%=path%>/static/js/plugins/ztree/jquery.ztree.excheck.js"></script>
<script src="<%=path%>/static/js/plugins/ztree/jquery.ztree.exedit.js"></script>

<!-- 自定义js -->
<script src="<%=path%>/static/js/mgt/dept_mgt.js"></script>