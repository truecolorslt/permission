<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/common/include.jsp"%>

<link rel="stylesheet" href="<%=path%>/static/css/table.css">
<link rel="stylesheet" href="<%=path%>/static/css/modal.css">

<link rel="stylesheet"
	href="<%=path%>/static/css/plugins/jqgrid/ui.jqgrid.css?0820">
<title>角色管理</title>
</head>
<body style="background-color: transparent;">
	<div id="areascontent" style="height: 100%">
		<div class="rows"
			style="margin-bottom: 0.8%; overflow: hidden;height: 98%">
			<div style="float: left; width: 100%;height: 100%">
				<div class="panel panel-default" style="height: 95%">
					<form id="formSearch" class="form-horizontal">
						<div class="form-group" style="margin-top:15px">
							<label class="control-label col-sm-2" style="" for="rname">角色名称</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="rname" name="rname">
							</div>

							<label class="control-label col-sm-2 style=" " for="rcode">角色编号</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="rcode" name="rcode">
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

	<!-- 新增、编辑div -->
	<%@ include file="role_opt.jsp"%>
</body>

<!-- js插件 -->
<!-- jqGrid表格 -->
<script
	src="<%=path%>/static/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script
	src="<%=path%>/static/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>

<!-- 自定义js -->
<script src="<%=path%>/static/js/mgt/role_mgt.js"></script>