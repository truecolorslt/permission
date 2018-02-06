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
									width : 80
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
									width : 100,
									fixed : true,
									sortable : false,
									resize : false,
									formatter : function(cellvalue, options,
											rowObject) {
										var detailFunction = "detailDict('"
												+ rowObject.did + "','"
												+ rowObject.dname + "','"
												+ rowObject.dcode + "')";
										// 调用删除方法
										var deleteFunction = "deleteDict('"
												+ rowObject.did + "')";

										var actions = '<a href="#" class="btn btn-info" onclick="'
												+ detailFunction
												+ '" title="查看用户">'
												+ '<i class="fa fa-file-text-o" aria-hidden="true"></i></a>';
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
}

/**
 * 重新加载表格
 */
function reloadGrid() {
	$(".norecords").hide();
	lastsel = 0;
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