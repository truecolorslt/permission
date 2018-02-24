$(document).ready(function() {
	// 初始化表格
	initTable();
	// 初始化按钮
	initButton();
	// 初始化用户验证规则
	validateUserForm();
});

/**
 * 初始化表格
 */
function initTable() {
	$.jgrid.defaults.styleUI = 'Bootstrap';
	$("#table_list")
			.jqGrid(
					{
						// data : data,
						url : "findUsers",
						mtype : "POST",
						rownumbers : true,
						datatype : "json",
						autowidth : true,
						shrinkToFit : true,
						rowNum : 10,
						rowList : [ 10, 20, 50 ],
						colModel : [
								{
									label : '用户姓名',
									name : 'realName',
									index : 'realName',
									editable : true,
									width : 85,
									sortable : false
								},
								{
									label : '用户帐号',
									name : 'username',
									index : 'username',
									editable : false,
									width : 80
								},
								{
									label : '性别',
									name : 'sex',
									index : 'sex',
									editable : false,
									width : 50
								},
								{
									label : '部门',
									name : 'dname',
									index : 'dname',
									editable : false,
									width : 80,
								},
								{
									label : '昵称',
									name : 'nickName',
									index : 'nickName',
									editable : false,
									width : 80,
								},
								{
									label : '备注',
									name : 'remark',
									index : 'remark',
									editable : true,
									width : 80,
									sortable : false,
								},
								{
									label : '操作',
									name : 'operate',
									index : 'operate',
									width : 150,
									fixed : true,
									sortable : false,
									resize : false,
									formatter : function(cellvalue, options,
											rowObject) {
										var detailFunction = "viewUser('"
												+ rowObject.uid + "','"
												+ rowObject.username + "','"
												+ rowObject.realName + "','"
												+ rowObject.dname + "','"
												+ rowObject.did + "','"
												+ rowObject.nickName + "','"
												+ rowObject.sex + "','"
												+ rowObject.remark + "')";
										// 调用删除方法
										var deleteFunction = "deleteUser('"
												+ rowObject.uid + "')";
										var actions = '<a href="#" class="btn btn-success" onclick="'
												+ deleteFunction
												+ '" title="重置密码">'
												+ '<i class="fa fa-key" aria-hidden="true"></i></a>';
										actions += '&nbsp;&nbsp;&nbsp;&nbsp;';
										actions += '<a href="#" class="btn btn-info" onclick="'
												+ detailFunction
												+ '" title="编辑用户">'
												+ '<i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>';
										actions += '&nbsp;&nbsp;&nbsp;&nbsp;';
										actions += '<a href="#" class="btn btn-danger" onclick="'
												+ deleteFunction
												+ '" title="删除用户">'
												+ '<i class="fa fa-trash" aria-hidden="true"></i></a>';
										return actions;
									}
								}, {
									label : '用户ID',
									name : 'uid',
									index : 'uid',
									hidden : true
								} ],
						pager : "#pager_list",
						viewrecords : true,
						hidegrid : false,
						loadComplete : function() {
							var re_records = $("#table_list").getGridParam(
									'records');
							if (re_records == 0 || re_records == null) {
								if ($(".norecords").html() == null) {
									$("#table_list")
											.parent()
											.append(
													"<div class=\"norecords\">没有符合数据</div>");
								}
								$(".norecords").show();
							}
						}
					});

	// 自应高度
	var newHeight = $(window).height() - 265;
	$(".ui-jqgrid .ui-jqgrid-bdiv").css("cssText",
			"height: " + newHeight + "px!important;");

	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_list').setGridWidth(width);
	});
	// 初始化按钮
	$("#table_list").jqGrid('navGrid', '#pager_list', {
		edit : false,
		add : false,
		del : false,
		search : false,
		refresh : true,
		refreshtext : "刷新",
		refreshtitle : "刷新用户列表",
	}).navButtonAdd('#pager_list', {
		caption : "新增",
		title : "新增用户",
		buttonicon : "glyphicon glyphicon-plus",
		onClickButton : function() {
			$("#userModalLabel").text("新增用户");
			$('#userModal').modal();
			$("#username_add").val("");
			$("#password_add").val("");
			$("#did_add").val("");
			$("#realName_add").val("");
			$("#nickName_add").val("");
			$("#sex_add").val("");
			$("#remark_add").val("");
			$(".mblack").html("");
		},
		position : "first"
	}).navSeparatorAdd('#pager_list', {
		sepclass : "ui-separator",
		sepcontent : ""
	});
}
/**
 * 初始化按钮
 */
function initButton() {
	// 查询按钮
	$("#find_btn").click(function() {
		reloadGrid();
	});

	// 重置按钮事件
	$("#resetBtn").click(function() {
		$("#did").val("");
		$("#username").val("");
		$("#realName").val("");
		return false;
	});

	// 新增保存按钮
	$("#btn_save_new").click(function() {
		// 初始化新增form校验规则
		if ($("#newUserForm").validate().form()) {
			// 验证成功
			var l = Ladda.create(this);
			addUser(l);
		}
	});

	// 新增保存按钮
	$("#btn_save_update").click(function() {
		// 初始化新增form校验规则
		if ($("#updateUserForm").validate().form()) {
			// 验证成功
			var l = Ladda.create(this);
			updateUser(l);
		}
	});
}

/**
 * 重新加载表格
 */
function reloadGrid() {
	$(".norecords").hide();
	jQuery("#table_list").jqGrid('setGridParam', {
		datatype : 'json',
		url : 'findUsers',
		postData : {
			'did' : $("#did").val(),
			'username' : $("#username").val(),
			'realName' : $("#realName").val()
		},
		page : 1
	}).trigger("reloadGrid");
}
/**
 * 校验新增用户的form规则
 */
function validateUserForm() {
	$("#newUserForm").validate(
			{
				rules : {
					username_add : "required",
					password_add : "required",
					realName_add : "required",
					did_add : "required"
				},
				messages : {
					username_add : "请输入账号",
					password_add : "请输入密码",
					realName_add : "请输入姓名",
					did_add : "请选择部门"
				},
				// the errorPlacement has to take the table layout into account
				errorPlacement : function(error, element) {
					if (element.is(":radio")) {
						error.appendTo(element.parent().next());
					} else if (element.is(":checkbox")) {
						error.appendTo(element.next());
					} else {
						error.appendTo(element.parent().next());
					}
				},
				// set this class to error-labels to indicate valid fields
				success : function(label) {
					// set &nbsp; as text for IE
					label.html("&nbsp;").addClass("checked");
					// label.addClass("valid").text("Ok!")
				},
				highlight : function(element, errorClass) {
					$(element).parent().next().find("." + errorClass)
							.removeClass("checked");
				}
			});
}

/**
 * 新增用户
 */
function addUser(l) {
	l.start();

	var username_add = $("#username_add").val();
	var password_add = $("#password_add").val();
	var realName_add = $("#realName_add").val();
	var did_add = $("#did_add").val();
	var nickName_add = $("#nickName_add").val();
	var sex_add = $("#sex_add").val();
	var remark = $("#remark_add").val();
	var data = {
		"username" : username_add,
		"password" : password_add,
		"realName" : realName_add,
		"did" : did_add,
		"nickName" : nickName_add,
		"sex" : sex_add,
		"remark" : remark
	};
	$.ajax({
		type : 'POST',
		dataType : "json",
		contentType : 'application/json;charset=utf-8',
		url : "addUser",// 请求的action路径
		data : JSON.stringify(data),
		error : function() {// 请求失败处理函数
			swal('新增用户失败!', '', 'error');
		},
		success : function(data) { // 请求成功后处理函数。
			var result = data.result;
			if (result) {
				swal('新增用户成功!', '', 'success');
				$('#userModal').modal('hide');
				reloadGrid();
			} else {
				swal('新增用户失败!', '', 'error');
			}
		},
		complete : function() {
			l.stop();
		}
	});
}

/**
 * 删除用户
 * 
 * @param uid
 */
function deleteUser(uid) {
	var param = {
		uid : uid
	};
	swal({
		title : '请确认是否删除此用户',
		text : "此用户被删除后将无法恢复!",
		type : 'warning',
		showCancelButton : true,
		confirmButtonColor : '#3085d6',
		cancelButtonColor : '#d33',
		confirmButtonText : '确认',
		cancelButtonText : '取消'
	}).then(function(isConfirm) {
		if (isConfirm) {
			$.ajax({
				type : 'POST',
				dataType : "json",
				// contentType : 'application/json;charset=utf-8',
				url : "deleteUser",// 请求的action路径
				data : param,
				error : function() {// 请求失败处理函数
					swal('删除用户失败!', '', 'error');
				},
				success : function(data) { // 请求成功后处理函数。
					var result = data.result;
					if (result) {
						swal('删除用户成功!', '', 'success');
						reloadGrid();
					} else {
						swal('删除用户失败!', '', 'error');
					}
				}
			});
		}
	});
	return false;
}

/**
 * 查看用户信息
 * 
 * @param uid
 */
function viewUser(uid, username, realName, dname, did, nickName, sex, remark) {
	$("#userUpdateModalLabel").text("编辑用户");
	$("#userUpdateModal").modal();
	$("#username_update").html(username);
	$("#uid_update").val(uid);
	$("#realName_update").val(realName);
	$("#dname_update").val(dname);
	$("#did_update").val(did);
	$("#nickName_update").val(nickName);
	$("#sex_update").val(sex);
	$("#remark_update").val(remark);
}

/**
 * 编辑用户信息
 * 
 * @param uid
 */
function updateUser(l) {
	var uid = $("#uid_update").val();
	var realName = $("#realName_update").val();
	var dname = $("#dname_update").val();
	var did = $("#did_update").val();
	var nickName = $("#nickName_update").val();
	var sex = $("#sex_update").val();
	var remark = $("#remark_update").val();
	var param = {
		uid : uid,
		realName : realName,
		dname : dname,
		did : did,
		nickName : nickName,
		sex : sex,
		remark : remark
	};

	// 异步保存打分和得分
	$.ajax({
		type : "post",
		dataType : "json",
		contentType : 'application/json;charset=utf-8',
		url : "updateUser",
		data : JSON.stringify(param),
		success : function(data) { // 请求成功后处理函数。
			var result = data.result;
			if (result) {
				swal('编辑用户成功!', '', 'success');
				$('#userUpdateModal').modal('hide');
				reloadGrid();
			} else {
				swal('编辑用户失败!', '', 'error');
			}
		},
		error : function() {
			swal('编辑用户失败!', '', 'error');
		},
		complete : function() {
			l.stop();
		}
	});

}