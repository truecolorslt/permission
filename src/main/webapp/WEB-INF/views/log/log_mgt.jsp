<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/common/include.jsp"%>


<link rel="stylesheet"
	href="<%=path%>/static/css/plugins/jqgrid/ui.jqgrid.css?0820">
<link rel="stylesheet" href="<%=path%>/static/css/table.css">

<!-- 日期控件 -->
<link
	href="<%=path%>/static/css/plugins/foundation/foundation-datepicker.css"
	rel="stylesheet">
<title>日志管理</title>
</head>
<body style="background-color: transparent;">
	<div id="areascontent" style="height: 100%">
		<div class="rows"
			style="margin-bottom: 0.8%; overflow: hidden;height: 98%">
			<div style="float: left; width: 100%;height: 100%">
				<div class="panel panel-default" style="height: 95%">
					<!-- <div class="panel-heading">
					<i class="fa fa-book" style="padding-right: 5px;"></i>数据字典列表
				</div> -->
					<form id="formSearch" class="form-horizontal">
						<div class="form-group" style="margin-top:15px">
							<label class="control-label col-sm-3" style="" for="logType">日志类型</label>
							<div class="col-sm-2">
								<select class="form-control" id="logType" name="logType">
									<option value="">请选择</option>
								</select>
							</div>
							<label class="control-label col-sm-3" style="" for="creator">操作人员名称</label>
							<div class="col-sm-2">
								<input type="text" class="form-control" id="creator"
									name="creator">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3" style="" for="">操作日期</label>
							<div class="col-sm-2">
								<input class="form-control" type="text" title="双击清空日期" value="" id="startDate" placeholder="开始日期">
							</div>
							<div class="col-sm-2">
								<input class="form-control" type="text" title="双击清空日期" value="" id="endDate" placeholder="结束日期">
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
					<div class="panel-body" style="height: 100%;">
						<div class="jqGrid_wrapper" style="height: 100%;/* overflow-x:scroll */">
							<table id="table_list" style="height: 100%;"></table>
							<div id="pager_list"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 新增、编辑div -->
	<%@ include file="log_opt.jsp"%>
</body>
<!-- js插件 -->
<!-- jqGrid表格 -->
<script
	src="<%=path%>/static/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script
	src="<%=path%>/static/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<!-- 日期控件js -->
<script
	src="<%=path%>/static/js/plugins/foundation/foundation-datepicker.js"></script>
<script
	src="<%=path%>/static/js/plugins/foundation/locales/foundation-datepicker.zh-CN.js"></script>

<!-- 自定义js -->
<script src="<%=path%>/static/js/mgt/log_mgt.js"></script>