<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/common/include.jsp"%>
<title>功能菜单管理</title>
</head>
<body style="background-color: transparent">
	<div id="areascontent" style="height: 100%">
		<div class="rows"
			style="margin-bottom: 0.8%; overflow: hidden;height: 98%">
			<!-- 树形菜单 -->
			<div style="float: left; width: 35%;height: 100%">
				<div class="panel panel-default" style="height: 95%">
					<div class="panel-heading">
						<i class="fa fa-tree" style="padding-right: 5px;"></i>功能树形菜单
					</div>
					<div class="panel-body" style="height:430px; overflow:auto;">
						<ul id="functionTree" class="ztree"></ul>
					</div>
				</div>
			</div>
			<!-- 功能信息 -->
			<div style="float: right; width: 64.2%;height: 100%">
				<div class="panel panel-default" style="height: 95%">
					<div class="panel-heading">
						<i class="fa fa-info" style="padding-right: 5px;"></i>功能信息修改
					</div>
					<div class="panel-body" style="height:430px; overflow:auto;">
						<form class="form-horizontal m-t" id="updateFunctionForm">
							<input type="hidden" name="fid" id="fid">
							<div class="form-group">
								<label class="col-sm-3 control-label">上级功能名称：</label>
								<div class="col-sm-8">
									<p class="form-control-static text-muted">
										<font size="2"><strong><span
												id="pFunctionName"></span> </strong> </font>
									</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="text-danger">*</span>功能名称：</label>
								<div class="col-sm-6">
									<input id="fname" name="fname" type="text" class="form-control"
										placeholder="功能名称（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="text-danger">*</span>功能编码：</label>
								<div class="col-sm-6">
									<input id="fcode" type="text" class="form-control" name="fcode"
										placeholder="功能编码（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">访问地址：</label>
								<div class="col-sm-6">
									<input id="furl" type="text" class="form-control" name="furl"
										placeholder="访问地址">
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">功能顺序：</label>
								<div class="col-sm-6">
									<input id="fsort" type="text" class="form-control" name="fsort"
										placeholder="功能顺序（1～100的数字）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">图标：</label>
								<div class="col-sm-6">
									<input id="ficon" type="text" class="form-control" name="ficon"
										placeholder="图标">
								</div>
								<div class="col-sm-2">
									<a href="http://www.fontawesome.com.cn/faicons/#transportation"
										target="_blank" class="btn btn-success btn-sm" role="button">参考图标</a>
								</div>
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

	<div class="modal fade" id="functionModal" tabindex="-1" role="dialog"
		aria-labelledby="functionModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="functionModalLabel">新增</h4>
				</div>
				<div class="modal-body">
					<div class="ibox float-e-margins">
						<div class="ibox-content">
							<form class="form-horizontal m-t" id="newFunctionForm">
								<input type="hidden" name="pfid_add" id="pfid_add">
								<div class="form-group">
									<label class="col-sm-3 control-label">上级功能名称：</label>
									<div class="col-sm-8">
										<p class="form-control-static text-muted">
											<font size="2"><strong><span
													id="pFunctionName_add"></span> </strong> </font>
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label"><span
										class="text-danger">*</span>功能名称：</label>
									<div class="col-sm-6">
										<input id="fname_add" name="fname_add" type="text"
											class="form-control" placeholder="功能名称（必填项）">
									</div>
									<div class="col-sm-2 mblack"></div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label"><span
										class="text-danger">*</span>功能编码：</label>
									<div class="col-sm-6">
										<input id="fcode_add" type="text" class="form-control"
											name="fcode_add" placeholder="功能编码（必填项）">
									</div>
									<div class="col-sm-2 mblack"></div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label">访问地址：</label>
									<div class="col-sm-6">
										<input id="furl_add" type="text" class="form-control"
											name="furl_add" placeholder="访问地址">
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label">功能顺序：</label>
									<div class="col-sm-6">
										<input id="fsort_add" type="text" class="form-control"
											name="fsort_add" placeholder="功能顺序（1～100的数字）">
									</div>
									<div class="col-sm-2 mblack"></div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label">图标：</label>
									<div class="col-sm-6">
										<input id="ficon_add" type="text" class="form-control"
											name="ficon_add" placeholder="图标">
									</div>

									<div class="col-sm-2">
										<a
											href="http://www.fontawesome.com.cn/faicons/#transportation"
											target="_blank" class="btn btn-success btn-sm" role="button">参考图标</a>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
					</button>
					<button id="btn_save_new" class="btn btn-primary ladda-button"
						data-style="zoom-in">
						<span class="glyphicon glyphicon-floppy-disk ladda-label">保存</span>
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<!-- js插件 -->
<!-- 树形菜单ztree -->
<script src="<%=path%>/static/js/plugins/ztree/jquery.ztree.core.js"></script>
<script src="<%=path%>/static/js/plugins/ztree/jquery.ztree.excheck.js"></script>
<script src="<%=path%>/static/js/plugins/ztree/jquery.ztree.exedit.js"></script>

<!-- 自定义js -->
<script src="<%=path%>/static/js/function/function_mgt.js"></script>
