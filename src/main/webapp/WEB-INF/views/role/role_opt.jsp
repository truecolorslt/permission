<%@ page language="java" pageEncoding="utf-8"%>
<!-- 新增角色div -->
<div class="modal fade" id="roleModal" tabindex="-1" role="dialog"
	aria-labelledby="roleModalLabel">
	<div class="modal-dialog" role="document" style="height: 70%">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="roleModalLabel">新增</h4>
			</div>
			<div class="modal-body">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="roleForm">
							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="text-danger">*</span>角色名称：</label>
								<div class="col-sm-6">
									<input id="rname_add" name="rname_add" type="text"
										class="form-control" placeholder="角色名称（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="text-danger">*</span>角色编码：</label>
								<div class="col-sm-6">
									<input id="rcode_add" name="rcode_add" type="text"
										class="form-control" placeholder="角色编码（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">备注：</label>
								<div class="col-sm-6">
									<textarea rows="3" class="form-control" id="remark_add"
										name="remark_add" placeholder="备注"></textarea>
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

<!-- 编辑角色div -->
<div class="modal fade" id="roleUpdateModal" tabindex="-1" role="dialog"
	aria-labelledby="roleUpdateModalLabel">
	<div class="modal-dialog" role="document" style="height: 70%">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="roleUpdateModalLabel">新增</h4>
			</div>
			<div class="modal-body">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="roleUpdateForm">
							<input type="hidden" id="rid_update" name="rid_update">
							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="text-danger">*</span>角色名称：</label>
								<div class="col-sm-6">
									<input id="rname_update" name="rname_update" type="text"
										class="form-control" placeholder="角色名称（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="text-danger">*</span>角色编码：</label>
								<div class="col-sm-6">
									<input id="rcode_update" name="rcode_update" type="text"
										class="form-control" placeholder="角色编码（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">备注：</label>
								<div class="col-sm-6">
									<textarea rows="3" class="form-control" id="remark_update"
										name="remark_update" placeholder="备注"></textarea>
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

<!-- 设置权限div -->
<div class="modal fade" id="roleSetModal" tabindex="-1" role="dialog"
	aria-labelledby="roleSetModalLabel">
	<div class="modal-dialog" role="document" style="">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="roleSetModalLabel">新增</h4>
			</div>
			<div class="modal-body">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="roleSetForm">
							<input type="hidden" id="rid_set" name="rid_set">
							<div class="row">
								<div class="col-sm-4">
									<label class="control-label">角色名称：</label>
									<p class="form-control-static text-muted" style="margin-left: 30px">
										<font size="2"><span id="rname_set"></span></font>
									</p>
									<label class="control-label">角色编码：</label>
									<p class="form-control-static text-muted" style="margin-left: 30px">
										<font size="2"><span id="rcode_set"></span></font>
									</p>
								</div>
								<div class="col-sm-8">
									<div style="float: left; width: 80%;height: 70%">
												<label class="control-label">功能菜单：</label>
										<div class="panel panel-default" style="height: 70%">
											<div class="panel-body" style="height:300px; overflow:auto;">
												<ul id="functionTree" class="ztree"></ul>
											</div>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button id="btn_save_set" class="btn btn-primary ladda-button"
					data-style="zoom-in">
					<span class="glyphicon glyphicon-floppy-disk ladda-label">保存</span>
				</button>
				<button id="btn_refresh" class="btn btn-info" data-style="zoom-in">
					<i class="fa fa-refresh" aria-hidden="true"></i> 刷新
				</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
				</button>
			</div>
		</div>
	</div>
</div>