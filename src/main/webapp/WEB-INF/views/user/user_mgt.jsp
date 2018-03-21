<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/common/include.jsp"%>

<link rel="stylesheet" href="<%=path%>/static/css/table.css">
<link rel="stylesheet" href="<%=path%>/static/css/modal.css">
<!-- jqgrid表格css -->
<link rel="stylesheet"
	href="<%=path%>/static/css/plugins/jqgrid/ui.jqgrid.css">
<!-- 双向选择css -->
<link rel="stylesheet"
	href="<%=path%>/static/css/plugins/doublebox/doublebox-bootstrap.css">
<style>
  .ue-container {
	   width: 90%;
	   margin: 0 auto;
	   margin-top: 1%;
	   padding: 20px 40px;
	   border: 1px solid #ddd;
	   background: #fff;
   }
	</style>
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
	<!-- 新增、编辑div -->
	<%@ include file="user_opt.jsp"%>
	
</body>

<!-- js插件 -->
<!-- jqGrid表格 -->
<script
	src="<%=path%>/static/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script
	src="<%=path%>/static/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<!-- ztree树形菜单 -->
<script src="<%=path%>/static/assets/js/zTree/jquery.ztree.all-3.5.js"></script>
<!-- 双向表格js -->
<script
	src="<%=path%>/static/js/plugins/doublebox/doublebox-bootstrap.js"></script>
<!-- 自定义js -->
<script src="<%=path%>/static/js/mgt/user_mgt.js"></script>