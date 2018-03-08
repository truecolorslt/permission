<%@ page language="java" pageEncoding="utf-8"%>
<!-- 新增function -->
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
									<a href="http://www.fontawesome.com.cn/faicons/#transportation"
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
