$(document).ready(function() {
	// 初始化表格
	initTable();
	// 初始化按钮
	initButton();
	// 初始化验证规则
	initValidateRule();
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
						url : "findRoles",
						mtype : "POST",
						rownumbers : true,
						datatype : "json",
						autowidth : true,
						shrinkToFit : true,
						rowNum : 10,
						rowList : [ 10, 20, 50 ],
						colModel : [
								{
									label : '角色名称',
									name : 'rname',
									index : 'rname',
									editable : false,
									width : 60,
									sortable : false
								},
								{
									label : '角色编号',
									name : 'rcode',
									index : 'rcode',
									editable : false,
									width : 60
								},
								{
									label : '备注',
									name : 'remark',
									index : 'remark',
									editable : true,
									width : 100,
									sortable : false,
								},
								{
									label : '操作',
									name : 'operate',
									index : 'operate',
									width : 100,
									// fixed : true,
									sortable : false,
									resize : false,
									formatter : function(cellvalue, options,
											rowObject) {
										var editFunction = "viewRole('"
												+ rowObject.rid + "','"
												+ rowObject.rname + "','"
												+ rowObject.rcode + "','"
												+ rowObject.remark + "')";
										var setFunction = "";
										var deleteFunction = "deleteRole('"
												+ rowObject.rid + "')";
										var actions = '<a href="#" class="btn btn-info" onclick="'
												+ editFunction
												+ '" title="编辑角色">'
												+ '<i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>';
										actions += '&nbsp;&nbsp;&nbsp;&nbsp;';

										actions += '<a href="#" class="btn btn-success" onclick="'
												+ setFunction
												+ '" title="设置权限">'
												+ '<i class="fa fa-wrench" aria-hidden="true"></i></a>';
										actions += '&nbsp;&nbsp;&nbsp;&nbsp;';

										actions += '<a href="#" class="btn btn-danger" onclick="'
												+ deleteFunction
												+ '" title="删除角色">'
												+ '<i class="fa fa-trash" aria-hidden="true"></i></a>';
										return actions;
									}
								}, {
									label : '角色ID',
									name : 'rid',
									index : 'rid',
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
		refreshtitle : "刷新角色列表",
	}).navButtonAdd('#pager_list', {
		caption : "新增",
		title : "新增角色",
		buttonicon : "glyphicon glyphicon-plus",
		onClickButton : function() {
			$("#roleModalLabel").text("新增角色");
			$('#roleModal').modal();
			$("#rname_add").val("");
			$("#rcode_add").val("");
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
		$("#rname").val("");
		$("#rcode").val("");
		return false;
	});

	// 新增保存按钮
	$("#btn_save_new").click(function() {
		// 初始化新增form校验规则
		if ($("#roleForm").validate().form()) {
			// 验证成功
			var l = Ladda.create(this);
			addRole(l);
		}
	});

	// 编辑保存按钮
	$("#btn_save_update").click(function() {
		// 初始化新增form校验规则
		if ($("#roleUpdateForm").validate().form()) {
			// 验证成功
			var l = Ladda.create(this);
			updateRole(l);
		}
	});
}

/**
 * 初始化验证规则
 */
function initValidateRule() {
	validateRoleForm();
	validateRoleUpdateForm();
}
/**
 * 校验新增角色的form规则
 */
function validateRoleForm() {
	$("#roleForm").validate(
			{
				rules : {
					rname_add : "required",
					rcode_add : "required"
				},
				messages : {
					rname_add : "请输入角色名称",
					rcode_add : "请输入角色编码"
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
 * 校验新增角色的form规则
 */
function validateRoleUpdateForm() {
	$("#roleUpdateForm").validate(
			{
				rules : {
					rname_update : "required",
					rcode_update : "required"
				},
				messages : {
					rname_update : "请输入角色名称",
					rcode_update : "请输入角色编码"
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
 * 重新加载表格
 */
function reloadGrid() {
	$(".norecords").hide();
	jQuery("#table_list").jqGrid('setGridParam', {
		datatype : 'json',
		url : 'findRoles',
		postData : {
			'rname' : $("#rname").val(),
			'rcode' : $("#rcode").val()
		},
		page : 1
	}).trigger("reloadGrid");
}

/**
 * 新增
 */
function addRole(l) {
	l.start();
	var rname = $("#rname_add").val();
	var rcode = $("#rcode_add").val();
	var remark = $("#remark_add").val();
	var data = {
		"rname" : rname,
		"rcode" : rcode,
		"remark" : remark
	};
	$.ajax({
		type : 'POST',
		dataType : "json",
		contentType : 'application/json;charset=utf-8',
		url : "addRole",// 请求的action路径
		data : JSON.stringify(data),
		error : function() {// 请求失败处理函数
			swal('新增角色失败!', '', 'error');
		},
		success : function(data) { // 请求成功后处理函数。
			var result = data.result;
			if (result) {
				swal('新增角色成功!', '', 'success');
				$('#roleModal').modal('hide');
				reloadGrid();
			} else {
				swal('新增角色失败!', '', 'error');
			}
		},
		complete : function() {
			l.stop();
		}
	});
}

/**
 * 编辑
 */
function updateRole(l) {
	l.start();
	var rid = $("#rid_update").val();
	var rname = $("#rname_update").val();
	var rcode = $("#rcode_update").val();
	var remark = $("#remark_update").val();
	var data = {
		"rid" : rid,
		"rname" : rname,
		"rcode" : rcode,
		"remark" : remark
	};
	$.ajax({
		type : 'POST',
		dataType : "json",
		contentType : 'application/json;charset=utf-8',
		url : "updateRole",// 请求的action路径
		data : JSON.stringify(data),
		error : function() {// 请求失败处理函数
			swal('编辑角色失败!', '', 'error');
		},
		success : function(data) { // 请求成功后处理函数。
			var result = data.result;
			if (result) {
				swal('编辑角色成功!', '', 'success');
				$('#roleUpdateModal').modal('hide');
				reloadGrid();
			} else {
				swal('编辑角色失败!', '', 'error');
			}
		},
		complete : function() {
			l.stop();
		}
	});
}

function viewRole(rid, rname, rcode, remark) {
	$("#roleUpdateModalLabel").text("编辑角色");
	$("#roleUpdateModal").modal();
	$("#rid_update").val(rid);
	$("#rname_update").val(rname);
	$("#rcode_update").val(rcode);
	$("#remark_update").val(remark);
	$(".mblack").html("");
}

/**
 * 删除用户
 * 
 * @param uid
 */
function deleteRole(rid) {
	var param = {
		rid : rid
	};
	swal({
		title : '请确认是否删除此角色',
		text : "此角色被删除后将无法恢复!",
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
				url : "deleteRole",// 请求的action路径
				data : param,
				error : function() {// 请求失败处理函数
					swal('删除角色失败!', '', 'error');
				},
				success : function(data) { // 请求成功后处理函数。
					var result = data.result;
					if (result) {
						swal('删除角色成功!', '', 'success');
						reloadGrid();
					} else {
						swal('删除角色失败!', '', 'error');
					}
				}
			});
		}
	});
	return false;
}