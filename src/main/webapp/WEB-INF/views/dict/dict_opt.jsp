<%@ page language="java" pageEncoding="utf-8"%>

<!-- 新增数据字典div -->
<div class="modal fade" id="dictModal" tabindex="-1" role="dialog"
	aria-labelledby="dictModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="dictModalLabel">新增</h4>
			</div>
			<div class="modal-body">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="newDictForm">
							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="text-danger">*</span>数据字典名称：</label>
								<div class="col-sm-6">
									<input id="dname_add" name="dname_add" type="text"
										class="form-control" placeholder="数据字典名称（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="text-danger">*</span>数据字典编码：</label>
								<div class="col-sm-6">
									<input id="dcode_add" name="dcode_add" type="text"
										class="form-control" placeholder="数据字典编码（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
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

<!-- 查询数据字典明细div -->
<div class="modal fade" id="dictDetailModal" tabindex="-1" role="dialog"
	aria-labelledby="dictDetailModalLabel">
	<div class="modal-dialog" role="document" style="width:80%;">
		<div class="modal-content" style="">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="dictDetailModalLabel">数据字典属性明细</h4>
			</div>
			<div class="modal-body" style="">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="dictDetailForm">
							<input type="hidden" name="pdid_add" id="pdid_add">
							<div class="form-group">
								<label class="col-sm-2 control-label">数据字典名称：</label>
								<div class="col-sm-4">
									<p class="form-control-static text-muted">
										<font size="2"><strong><span id="pdname_add"></span>
										</strong> </font>
									</p>
								</div>
								<label class="col-sm-2 control-label">数据字典编码：</label>
								<div class="col-sm-4">
									<p class="form-control-static text-muted">
										<font size="2"><strong><span id="pdcode_add"></span>
										</strong> </font>
									</p>
								</div>
							</div>
							<div class="panel-body" style="height: 235px;width: 100%">
								<div class="jqGrid_wrapper" style="">
									<table id="table_detail_list" style=""></table>
									<div id="pager_detail_list"></div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button id="btn_add_detail" class="btn btn-primary"
					data-style="zoom-in">
					<i class="fa fa-plus" aria-hidden="true"></i>新增
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

<!-- 新增数据字典属性div -->
<div class="modal fade" id="dictDetailAddModal" tabindex="-1"
	role="dialog" aria-labelledby="dictDetailAddModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="dictDetailAddModalLabel">新增属性</h4>
			</div>
			<div class="modal-body">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="dictDetailAddForm">
							<div class="form-group">
								<label class="col-sm-4 control-label"><span
									class="text-danger">*</span>数据字典属性编码：</label>
								<div class="col-sm-5">
									<input id="detail_code_add" name="detail_code_add" type="text"
										class="form-control" placeholder="数据字典属性编码（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label"><span
									class="text-danger">*</span>数据字典属性名称：</label>
								<div class="col-sm-5">
									<input id="detail_name_add" name="detail_name_add" type="text"
										class="form-control" placeholder="数据字典属性名称（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label"><span
									class="text-danger">*</span>数据字典属性顺序：</label>
								<div class="col-sm-5">
									<input id="detail_sort_add" name="detail_sort_add" type="text"
										class="form-control" placeholder="数据字典属性顺序（必填项）">
								</div>
								<div class="col-sm-2 mblack"></div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label">备注：</label>
								<div class="col-sm-5">
									<input id="detail_remark_add" type="text" class="form-control"
										name="detail_remark_add" placeholder="备注">
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button id="btn_save_detail" class="btn btn-primary ladda-button"
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