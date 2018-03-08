<%@ page language="java" pageEncoding="utf-8"%>
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
									<input id="password_add" name="password_add" type="password"
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
										style="cursor:pointer;background-color: white;" readonly /> <input
										type="hidden" id="did_add" name="did_add">
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
									<select class="form-control" id="sex_add" name="sex_add">
										<option value="">请选择</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">备注：</label>
								<div class="col-sm-6">
									<textarea class="form-control" rows="3" id="remark_add"
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

<!-- 修改用户div -->
<div class="modal fade" id="userUpdateModal" tabindex="-1" role="dialog"
	aria-labelledby="userUpdateModalLabel">
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
										style="cursor:pointer;background-color: white;" readonly /> <input
										type="hidden" id="did_update" name="did_update">
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
									<select class="form-control" id="sex_update" name="sex_update">
										<option value="">请选择</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">备注：</label>
								<div class="col-sm-6">
									<textarea class="form-control" rows="3" id="remark_update"
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
										<font size="2"><strong><span id="realName_pwd"></span>
										</strong> </font>
									</p>
								</div>
								<label class="col-sm-3 control-label">用户账号：</label>
								<div class="col-sm-3">
									<p class="form-control-static text-muted">
										<font size="2"><strong><span id="username_pwd"></span>
										</strong> </font>
									</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label"><span
									class="text-danger">*</span>新密码：</label>
								<div class="col-sm-6">
									<input id="password_pwd" name="password_pwd" type="password"
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
