<%@ page language="java" pageEncoding="utf-8"%>

<!-- 新增部门 -->
<div class="modal fade" id="departmentModal" tabindex="-1" role="dialog"
	aria-labelledby="departmentModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="departmentModalLabel">新增</h4>
			</div>
			<div class="modal-body">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="departmentForm">
							<input type="hidden" name="pdid_add" id="pdid_add">
							<div class="form-group">
								<label class="col-sm-3 control-label">上级部门名称：</label>
								<div class="col-sm-8">
									<p class="form-control-static text-muted">
										<font size="2"><strong><span
												id="pDepartmentName_add"></span> </strong> </font>
									</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="text-danger">*</span>部门名称：</label>
								<div class="col-sm-6">
									<input id="dname_add" name="dname_add" type="text"
										class="form-control" placeholder="部门名称（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="text-danger">*</span>部门编码：</label>
								<div class="col-sm-6">
									<input id="dcode_add" type="text" class="form-control"
										name="dcode_add" placeholder="部门编码（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">部门顺序：</label>
								<div class="col-sm-6">
									<input id="dsort_add" type="text" class="form-control"
										name="dsort_add" placeholder="功能顺序（1～100的数字）">
								</div>
								<div class="col-sm-2 mblack"></div>
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
