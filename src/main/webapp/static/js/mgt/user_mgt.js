// ztree设置
var setting;
// ztree节点
var zNodes;
// ztree设置
var settingAdd;
// ztree设置
var settingUpdate;
// ztree节点
var zNodesOpt;

$(document).ready(function() {
	// 初始化表格
	initTable();
	// 初始化按钮
	initButton();
	// 初始化验证规则
	initValidateRule();
	// 初始化部门tree
	initDeptTree();
	// 初始化下拉框
	initSelect();
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
						autoScroll : true,
						rowNum : 10,
						rowList : [ 10, 20, 50 ],
						colModel : [
								{
									label : '用户姓名',
									name : 'realName',
									index : 'realName',
									editable : true,
									width : 120,
									sortable : false
								},
								{
									label : '用户帐号',
									name : 'username',
									index : 'username',
									editable : false,
									width : 120
								},
								{
									label : '性别',
									name : 'sex',
									index : 'sex',
									editable : false,
									width : 60
								},
								{
									label : '部门',
									name : 'dname',
									index : 'dname',
									editable : false,
									width : 200
								},
								{
									label : '昵称',
									name : 'nickName',
									index : 'nickName',
									editable : false,
									width : 120
								},
								{
									label : '备注',
									name : 'remark',
									index : 'remark',
									editable : true,
									width : 120,
									sortable : false
								},
								{
									label : '操作',
									name : 'operate',
									index : 'operate',
									width : 220,
									fixed : true,
									sortable : false,
									resize : false,
									formatter : function(cellvalue, options,
											rowObject) {
										// 编辑用户
										var detailFunction = "viewUser('"
												+ rowObject.uid + "','"
												+ rowObject.username + "','"
												+ rowObject.realName + "','"
												+ rowObject.dname + "','"
												+ rowObject.did + "','"
												+ rowObject.nickName + "','"
												+ rowObject.sex + "','"
												+ rowObject.sexCode + "','"
												+ rowObject.remark + "')";
										// 调用删除方法
										var deleteFunction = "deleteUser('"
												+ rowObject.uid + "')";
										// 重置密码
										var resetFunction = "resetPwdView('"
												+ rowObject.uid + "','"
												+ rowObject.username + "','"
												+ rowObject.realName + "')";
										// 设置角色
										var roleFunction = "roleView('"
												+ rowObject.uid + "','"
												+ rowObject.username + "','"
												+ rowObject.realName + "')";
										var actions = '<a href="#" class="btn btn-info" onclick="'
												+ detailFunction
												+ '" title="编辑用户">'
												+ '<i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>';
										actions += '&nbsp;&nbsp;&nbsp;&nbsp;';

										actions += '<a href="#" class="btn btn-warning" onclick="'
												+ roleFunction
												+ '" title="设置角色">'
												+ '<i class="fa fa-user-md" aria-hidden="true"></i></a>';
										actions += '&nbsp;&nbsp;&nbsp;&nbsp;';

										actions += '<a href="#" class="btn btn-success" onclick="'
												+ resetFunction
												+ '" title="重置密码">'
												+ '<i class="fa fa-key" aria-hidden="true"></i></a>';
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

	$("#table_list").jqGrid('setFrozenColumns');

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
			$("#dname_add").val("");
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
		$("#dname").val("");
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

	// 重置密码按钮
	$("#btn_pwd_update").click(function() {
		// 初始化新增form校验规则
		if ($("#updatePwdForm").validate().form()) {
			// 验证成功
			var l = Ladda.create(this);
			resetPwd(l);
		}
	});

	// 设置权限按钮
	$("#btn_role_update").click(function() {
		var l = Ladda.create(this);
		setUserRole(l);
	});

	// 刷新按钮
	$("#btn_refresh").click(function() {
		initRoleSelect();
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
					did_add : "required",
					dname_add : "required",
				},
				messages : {
					username_add : "请输入账号",
					password_add : "请输入密码",
					realName_add : "请输入姓名",
					did_add : "请选择部门",
					dname_add : "请选择部门"
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
 * 校验新增用户的form规则
 */
function validateUpdateUserForm() {
	$("#updateUserForm").validate(
			{
				rules : {
					realName_update : "required",
					did_update : "required",
					dname_update : "required"
				},
				messages : {
					realName_update : "请输入姓名",
					did_update : "请选择部门",
					dname_update : "请选择部门"
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
 * 校验新增用户的form规则
 */
function validateUpdatePwdForm() {
	$("#updatePwdForm").validate(
			{
				rules : {
					password_pwd : "required"
				},
				messages : {
					password_pwd : "请输入新密码"
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
	var dname_add = $("#dname_add").val();
	var nickName_add = $("#nickName_add").val();
	var sex_add = $("#sex_add").val();
	var remark = $("#remark_add").val();
	var data = {
		"username" : username_add,
		"password" : password_add,
		"realName" : realName_add,
		"did" : did_add,
		"dname" : dname_add,
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
function viewUser(uid, username, realName, dname, did, nickName, sex, sexCode,
		remark) {
	$("#userUpdateModalLabel").text("编辑用户");
	$("#userUpdateModal").modal();
	$("#username_update").html(username);
	$("#uid_update").val(uid);
	$("#realName_update").val(realName);
	$("#dname_update").val(dname);
	$("#did_update").val(did);
	$("#nickName_update").val(nickName);
	$("#sex_update").val(sexCode);
	$("#remark_update").val(remark);
	$(".mblack").html("");
}

/**
 * 重置密码
 * 
 * @param uid
 * @param username
 */
function resetPwdView(uid, username, realName) {
	$("#pwdUpdateModalLabel").text("重置密码");
	$("#pwdUpdateModal").modal();

	$("#realName_pwd").html(realName);
	$("#username_pwd").html(username);
	$("#uid_pwd").val(uid);
	$("#password_pwd").val("");
	$(".mblack").html("");
}

/**
 * 设置角色
 * 
 * @param uid
 * @param username
 */
function roleView(uid, username, realName) {
	$("#roleUpdateModalLabel").text("设置角色");
	$("#roleUpdateModal").modal();

	$("#realName_role").html(realName);
	$("#username_role").html(username);
	$("#uid_role").val(uid);
	initRoleSelect();
}

function initRoleSelect() {
	var uid = $("#uid_role").val();
	var param = {
		uid : uid
	};
	var nonSelectedList;
	var selectedList;
	$.ajax({
		data : param,
		async : false,
		type : 'POST',
		dataType : "json",
		url : "getRoleList",// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('角色加载失败');
		},
		success : function(data) { // 请求成功后处理函数。
			nonSelectedList = data.nonSelectedList;
			selectedList = data.selectedList;
		}
	});
	$("#roleSelect").empty();
	$('#roleSelect').doublebox({
		nonSelectedListLabel : '选择角色',
		selectedListLabel : '授权用户角色',
		preserveSelectionOnMove : 'moved',
		moveOnSelect : false,
		filterPlaceHolder : "请输入角色名称",
		nonSelectedList : nonSelectedList,
		selectedList : selectedList,
		optionValue : "roleId",
		optionText : "roleName",
		doubleMove : true,
	});
}

/**
 * 编辑用户信息
 * 
 * @param uid
 */
function updateUser(l) {
	l.start();
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

/**
 * 重置密码
 * 
 * @param uid
 */
function resetPwd(l) {
	l.start();
	var uid = $("#uid_pwd").val();
	var password = $("#password_pwd").val();
	var param = {
		uid : uid,
		password : password
	};

	$.ajax({
		type : "post",
		dataType : "json",
		url : "resetPwd",
		data : param,
		success : function(data) { // 请求成功后处理函数。
			var result = data.result;
			if (result) {
				swal('重置密码成功!', '', 'success');
				$('#pwdUpdateModal').modal('hide');
			} else {
				swal('重置密码失败!', '', 'error');
			}
		},
		error : function() {
			swal('重置密码失败!', '', 'error');
		},
		complete : function() {
			l.stop();
		}
	});
}

/**
 * 初始化验证规则
 */
function initValidateRule() {
	validateUserForm();
	validateUpdateUserForm();
	validateUpdatePwdForm();
}

/**
 * 初始化部门tree
 */
function initDeptTree() {
	setting = {
		check : {
			enable : false
		},
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : onClickNode
		}
	};
	settingAdd = {
		check : {
			enable : false
		},
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : onClickNodeAdd
		}
	};
	settingUpdate = {
		check : {
			enable : false
		},
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : onClickNodeUpdate
		}
	};

	var param = {
		src : "query"
	};
	$.ajax({
		data : param,
		async : false,
		type : 'POST',
		dataType : "json",
		url : "../dept/findDepartmentTrees",// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('树形菜单加载失败');
		},
		success : function(data) { // 请求成功后处理函数。
			zNodes = data; // 把后台封装好的简单Json格式赋给treeNodes
		}
	});

	var paramOpt = {
		src : "opt"
	};
	$.ajax({
		data : paramOpt,
		async : false,
		type : 'POST',
		dataType : "json",
		url : "../dept/findDepartmentTrees",// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('树形菜单加载失败');
		},
		success : function(data) { // 请求成功后处理函数。
			zNodesOpt = data; // 把后台封装好的简单Json格式赋给treeNodes
		}
	});

	$.fn.zTree.init($("#deptTree"), setting, zNodes);
	$.fn.zTree.init($("#deptTreeAdd"), settingAdd, zNodesOpt);
	$.fn.zTree.init($("#deptTreeUpdate"), settingUpdate, zNodesOpt);
}

/**
 * 显示菜单
 */
function showMenu() {
	$("#deptMenu").css({
		left : "15px",
		top : "34px"
	}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function showMenuAdd() {
	$("#deptMenuAdd").css({
		left : "15px",
		top : "34px"
	}).slideDown("fast");

	$("body").bind("mousedown", onBodyDownAdd);
}
function showMenuUpdate() {
	$("#deptMenuUpdate").css({
		left : "15px",
		top : "34px"
	}).slideDown("fast");

	$("body").bind("mousedown", onBodyDownUpdate);
}

/**
 * 隐藏菜单
 */
function hideMenu() {
	$("#deptMenu").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
/**
 * 隐藏菜单
 */
function hideMenuAdd() {
	$("#deptMenuAdd").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDownAdd);
}
/**
 * 隐藏菜单
 */
function hideMenuUpdate() {
	$("#deptMenuUpdate").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDownUpdate);
}

function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "deptMenu"
			|| event.target.id == "did" || $(event.target).parents("#deptMenu").length > 0)) {
		hideMenu();
	}
}
function onBodyDownAdd(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "deptMenuAdd"
			|| event.target.id == "did_add" || $(event.target).parents(
			"#deptMenuAdd").length > 0)) {
		hideMenuAdd();
	}
}
function onBodyDownUpdate(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "deptMenuUpdate"
			|| event.target.id == "did_Update" || $(event.target).parents(
			"#deptMenuUpdate").length > 0)) {
		hideMenuUpdate();
	}
}

/**
 * 节点点击事件
 */
function onClickNode(e, treeId, treeNode) {
	$("#dname").val(getTreePath(treeNode));
	// $("#dname").val(treeNode.name);
	$("#did").val(treeNode.id);
	hideMenu();
}
/**
 * 节点点击事件
 */
function onClickNodeAdd(e, treeId, treeNode) {
	$("#dname_add").val(getTreePath(treeNode));
	// $("#dname_add").val(treeNode.name);
	$("#did_add").val(treeNode.id);
	hideMenuAdd();
}
/**
 * 节点点击事件
 */
function onClickNodeUpdate(e, treeId, treeNode) {
	$("#dname_update").val(getTreePath(treeNode));
	// $("#dname_update").val(treeNode.name);
	$("#did_update").val(treeNode.id);
	hideMenuUpdate();
}

/**
 * 获取子节点，所有父节点的name的拼接字符串
 * 
 * @param treeObj
 * @returns
 */
function getTreePath(treeObj) {
	if (treeObj == null || treeObj.id == "0") {
		return "";
	}
	var treename = treeObj.name;
	var pNode = treeObj.getParentNode();
	if (pNode != null) {
		treename = getTreePath(pNode) + "|" + treename;
	}
	if (treename.indexOf("|") == 0) {
		treename = treename.substring(1, treename.length);
	}
	return treename;
}

/**
 * 初始化下拉菜单
 */
function initSelect() {
	// 初始化数据字典：性别
	var param = {
		code : "sex"
	};
	$.ajax({
		data : param,
		type : 'POST',
		url : "../dict/getDictAttrsByCode",
		dataType : "json",
		success : function(data) {
			$.each(data, function(index, element) {
				$("#sex_update").append(
						"<option value=" + element.value + ">" + element.text
								+ "</option>");
				$("#sex_add").append(
						"<option value=" + element.value + ">" + element.text
								+ "</option>");

			});
		},

		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
}

/**
 * 设置用户角色
 */
function setUserRole(l) {
	var uid = $("#uid_role").val();
	var roles = "";
	$("#roleSelect option:selected").each(function() {
		roles += $(this).val() + "|";
	});
	if (roles == "") {
		swal('请选择角色!', '', 'warning');
		return;
	}

	var param = {
		uid : uid,
		roles : roles
	};
	l.start();
	$.ajax({
		type : 'POST',
		dataType : "json",
		// contentType : 'application/json;charset=utf-8',
		url : "setUserRole",// 请求的action路径
		data : param,
		error : function() {// 请求失败处理函数
			swal('设置用户角色失败!', '', 'error');
		},
		success : function(data) { // 请求成功后处理函数。
			var result = data.result;
			if (result) {
				swal('设置用户角色成功!', '', 'success');
			} else {
				var msg = data.msg;
				swal('设置用户角色失败!', msg, 'error');
			}
		},
		complete : function() {
			l.stop();
		}
	});
}
