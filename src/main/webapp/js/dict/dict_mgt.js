var pdid;
$(document).ready(function() {
	// 初始化表格
	initTable();
	// 初始化按钮
	initButton();
	// 初始化数据字典验证规则
	validateNewDictForm();
	// 初始化detail表格
	initDictDetailTable();
	// 刷新按钮
	$("#btn_refresh").click(function() {
		reloadDetailGrid();
	});

});

// 上次选择row_id
var lastsel = 0;

/**
 * 初始化表格
 */
function initTable() {
	$.jgrid.defaults.styleUI = 'Bootstrap';
	$("#table_list")
			.jqGrid(
					{
						// data : data,
						url : "findDicts",
						mtype : "POST",
						rownumbers : true,
						datatype : "json",
						// height : 370,
						autowidth : true,
						shrinkToFit : true,
						// viewrecords:true,
						onSelectRow : editSelectRow,
						// onCellSelect : editSelectRow,
						ondblClickRow : dblClickRow,
						rowNum : 10,
						rowList : [ 10, 20, 50 ],
						colModel : [
								{
									label : '数据字典名称<font color=gray size=1px>（双击编辑）</font>',
									name : 'dname',
									index : 'dname',
									editable : true,
									width : 85,
									sortable : false,
									editoptions : {
										dataInit : function(element) {
											$(element)
													.keydown(
															function(event) {
																if (event.keyCode == 13) { // 回车
																	updateDict();
																} else if (event.keyCode == 27) { // esc
																	// alert("27")
																}
															});
										}
									}
								},
								{
									label : '数据字典编码',
									name : 'dcode',
									index : 'dcode',
									editable : false,
									width : 80
								},
								{
									label : '备注<font color=gray size=1px>（双击编辑）</font>',
									name : 'remark',
									index : 'remark',
									editable : true,
									width : 80,
									sortable : false,
									editoptions : {
										dataInit : function(element) {
											$(element)
													.keydown(
															function(event) {
																if (event.keyCode == 13) { // 回车
																	updateDict();
																} else if (event.keyCode == 27) { // esc
																	// alert("27")
																}
															});
										}
									}
								},
								{
									label : '修改时间',
									name : 'modifiedTime',
									index : 'modifiedTime',
									editable : false,
									width : 80
								},
								{
									label : '修改人',
									name : 'modifier',
									index : 'modifier',
									editable : false,
									width : 60,
								},
								{
									label : '操作',
									name : 'operate',
									index : 'operate',
									width : 100,
									fixed : true,
									sortable : false,
									resize : false,
									// formatter : 'actions'
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
												+ '" title="查看属性明细">'
												+ '<i class="fa fa-file-text-o" aria-hidden="true"></i></a>';
										actions += '&nbsp;&nbsp;&nbsp;&nbsp;';
										actions += '<a href="#" class="btn btn-danger" onclick="'
												+ deleteFunction
												+ '" title="删除数据字典">'
												+ '<i class="fa fa-trash" aria-hidden="true"></i></a>';
										return actions;
									}
								}, {
									label : '数据字典ID',
									name : 'did',
									index : 'did',
									hidden : true
								} ],
						pager : "#pager_list",
						viewrecords : true,
						// caption : "数据字典列表",
						// add : true,
						// edit : true,
						// addtext : 'Add',
						// edittext : 'Edit',
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

	// Add selection
	// $("#table_list").setSelection(4, true);

	// 初始化按钮
	$("#table_list").jqGrid('navGrid', '#pager_list', {
		edit : false,
		add : false,
		del : false,
		search : false,
		refresh : true,
		refreshtext : "刷新",
		refreshtitle : "刷新数据字典列表",
	}).navButtonAdd('#pager_list', {
		caption : "新增",
		title : "新增数据字典",
		buttonicon : "glyphicon glyphicon-plus",
		onClickButton : function() {
			$("#dictModalLabel").text("新增数据字典");
			$('#dictModal').modal();
			$("#dname_add").val("");
			$("#dcode_add").val("");
			$("#remark_add").val("");
			$(".mblack").html("");
		},
		position : "first"
	}).navSeparatorAdd('#pager_list', {
		sepclass : "ui-separator",
		sepcontent : ""
	});

	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_list').setGridWidth(width);
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
		$("#dname").val("");
		$("#dcode").val("");
		return false;
	});

	// 新增保存按钮
	$("#btn_save_new").click(function() {
		// 初始化新增form校验规则
		if ($("#newDictForm").validate().form()) {
			// 验证成功
			var l = Ladda.create(this);
			addDict(l);
		}
	});

	// 新增属性按钮
	$("#btn_add_detail").click(function() {
		$("#detail_name_add").val("");
		$("#detail_key_add").val("");
		$("#detail_value_add").val("");
		$("#detail_sort_add").val("");
		$("#detail_remark_add").val("");
		$(".mblack").html("");
		$("#dictDetailAddModalLabel").text("新增数据字典属性");
		$('#dictDetailAddModal').modal();
	});

	// 新增属性保存按钮
	$("#btn_save_detail").click(function() {
		// 初始化新增form校验规则
		if (validateDictDetailForm().form()) {
			// 验证成功
			var l = Ladda.create(this);
			addDictDetail(l);
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
		url : 'findDicts',
		postData : {
			'dname' : $("#dname").val(),
			'dcode' : $("#dcode").val()
		},
		page : 1
	}).trigger("reloadGrid");
}

/**
 * 删除数据字典
 * 
 * @param did
 * @returns {Boolean}
 */
function deleteDict(did) {
	var param = {
		did : did
	};
	swal({
		title : '请确认是否删除此数据字典',
		text : "此数据字典被删除后将无法恢复!",
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
				url : "deleteDict",// 请求的action路径
				data : param,
				error : function() {// 请求失败处理函数
					swal('删除数据字典失败!', '', 'error');
				},
				success : function(data) { // 请求成功后处理函数。
					var result = data.result;
					if (result) {
						swal('删除数据字典成功!', '', 'success');
						reloadGrid();
					} else {
						swal('删除数据字典失败!', '', 'error');
					}
				}
			});
		}
	});
	return false;
}

/**
 * 查看数据字典属性明细
 * 
 * @param did
 */

function detailDict(did, dname, dcode) {
	pdid = did;
	$("#dictDetailModalLabel").text("查看数据字典属性明细");
	$('#dictDetailModal').modal();
	$("#pdname_add").html(dname);
	$("#pdcode_add").html(dcode);

	// 初始化明细表格
	reloadDetailGrid();

}

/**
 * 保存上一行数据
 * 
 * @param id
 */
function editSelectRow(rowid, status) {
	if (rowid && rowid != lastsel) {
		// 保存上一行数据
		updateDict();
		// 恢复上行数据
		jQuery('#table_list').jqGrid('restoreRow', lastsel);
		lastsel = rowid;
	}
}

/**
 * 双击
 * 
 * @param id
 */
function dblClickRow(rowid, iRow, iCol, e) {
	// 启用当前行为编辑状态
	jQuery('#table_list').jqGrid('editRow', rowid, true);
}

/**
 * 执行保存
 */
function updateDict() {
	// 原选中行ID
	var oldSelectRowId = lastsel;
	if (oldSelectRowId != null && oldSelectRowId != "0") {
		// && oldSelectRowId.length > 0) {
		// 保存上一行
		$("#table_list").jqGrid('saveRow', oldSelectRowId);
		// 获取上一行数据
		var rowDatas = $("#table_list").jqGrid('getRowData', oldSelectRowId);

		// 异步保存打分和得分
		$.ajax({
			type : "post",
			url : "editDict",
			data : {
				did : rowDatas.did,
				dname : rowDatas.dname,
				remark : rowDatas.remark
			},
			success : function(data) { // 请求成功后处理函数。
				var result = data.result;
			},
			error : function() {
				swal('编辑数据字典失败!', '', 'error');
			}
		});
	}
}

/**
 * 校验新增数据字典的form规则
 */
function validateNewDictForm() {
	$("#newDictForm").validate(
			{
				rules : {
					dname_add : "required",
					dcode_add : {
						required : true,
						remote : {
							async : false,// 默认为异步请求，设置false为同步
							cache : false,
							url : "checkDcode",
							type : "post", // 数据发送方式
							data : {
								dcode : function() {
									return $("#dcode_add").val();
								}
							}
						}
					},
				},
				messages : {
					dname_add : "请输入数据字典名称!",
					dcode_add : {
						required : "请输入数据字典编码!",
						remote : "数据字典编码已存在!"
					},
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
 * 新增功能
 */
function addDict(l) {
	l.start();
	var dname = $("#dname_add").val();
	var dcode = $("#dcode_add").val();
	var remark = $("#remark_add").val();
	var data = {
		"dname" : dname,
		"dcode" : dcode,
		"remark" : remark
	};
	$.ajax({
		type : 'POST',
		dataType : "json",
		contentType : 'application/json;charset=utf-8',
		url : "addDict",// 请求的action路径
		data : JSON.stringify(data),
		error : function() {// 请求失败处理函数
			swal('新增数据字典失败!', '', 'error');
		},
		success : function(data) { // 请求成功后处理函数。
			var result = data.result;
			if (result) {
				swal('新增数据字典成功!', '', 'success');
				$('#dictModal').modal('hide');
				reloadGrid();
			} else {
				swal('新增数据字典失败!', '', 'error');
			}
		},
		complete : function() {
			l.stop();
		}
	});
}

/**
 * 初始化明细表格
 */
function initDictDetailTable() {
	$.jgrid.defaults.styleUI = 'Bootstrap';
	$("#table_detail_list")
			.jqGrid(
					{
						// url : "findDictDetails",
						mtype : "POST",
						height : 195,
						rownumbers : true,
						datatype : "json",
						autowidth : true,
						shrinkToFit : true,
						rowNum : -1,
						colModel : [
								{
									label : '属性名称',
									name : 'dname',
									index : 'dname',
									editable : true,
									width : 120,
									sortable : false
								},
								{
									label : '属性键',
									name : 'dkey',
									index : 'dkey',
									editable : false,
									width : 120
								},
								{
									label : '属性值',
									name : 'dvalue',
									index : 'dvalue',
									editable : true,
									width : 120,
									sortable : false
								},
								{
									label : '顺序',
									name : 'dsort',
									index : 'dsort',
									editable : false,
									width : 80
								},
								{
									label : '备注',
									name : 'remark',
									index : 'remark',
									editable : false,
									width : 150,
								},
								{
									label : '操作',
									name : 'operate',
									index : 'operate',
									width : 50,
									fixed : true,
									sortable : false,
									resize : false,
									// formatter : 'actions'
									formatter : function(cellvalue, options,
											rowObject) {
										var deleteDictDetailFunction = "deleteDictDetail('"
												+ rowObject.did + "')";
										var actions = '<a href="#"  class="btn btn-danger" onclick="'
												+ deleteDictDetailFunction
												+ '" title="删除属性">'
												+ '<i class="fa fa-trash" aria-hidden="true"></i></a>';
										return actions;
									}
								}, {
									label : '数据字典ID',
									name : 'did',
									index : 'did',
									hidden : true
								} ],
						// pager : "#pager_detail_list",
						viewrecords : true,
						hidegrid : false,
						loadComplete : function() {

							var re_records = $("#table_detail_list")
									.getGridParam('records');
							if (re_records == 0 || re_records == null) {
								if ($(".norecords").html() == null) {
									$("#table_detail_list")
											.parent()
											.append(
													"<div class=\"norecords\">没有符合数据</div>");
								}
								$(".norecords").show();
							}
						}
					});

	// 自应高度
	/*
	 * var newHeight = $(window).height() - 265; $(".ui-jqgrid
	 * .ui-jqgrid-bdiv").css("cssText", "height: " + newHeight +
	 * "px!important;");
	 */

	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_list').setGridWidth(width);
	});
}
/**
 * 重新加载表格
 */
function reloadDetailGrid() {
	$(".norecords").hide();
	jQuery("#table_detail_list").jqGrid('setGridParam', {
		datatype : 'json',
		url : 'findDictDetails',
		postData : {
			'did' : pdid,
		}
	}).trigger("reloadGrid");
}
/**
 * 删除数据字典属性
 * 
 * @param did
 * @returns {Boolean}
 */
function deleteDictDetail(did) {
	var param = {
		did : did
	};
	swal({
		title : '请确认是否删除此数据字典属性',
		text : "此数据字典属性被删除后将无法恢复!",
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
				url : "deleteDict",// 请求的action路径
				data : param,
				error : function() {// 请求失败处理函数
					swal('删除数据字典属性失败!', '', 'error');
				},
				success : function(data) { // 请求成功后处理函数。
					var result = data.result;
					if (result) {
						swal('删除数据字典属性成功!', '', 'success');
						reloadDetailGrid();
					} else {
						swal('删除数据字典属性失败!', '', 'error');
					}
				}
			});
		}
	});
	return false;
}

/**
 * 校验新增数据字典属性的form规则
 */
function validateDictDetailForm() {
	return $("#dictDetailAddForm").validate(
			{
				rules : {
					detail_name_add : "required",
					detail_key_add : "required",
					detail_value_add : "required",
					detail_sort_add : "required"
				},
				messages : {
					detail_name_add : "请输入属性名称！",
					detail_key_add : "请输入属性键！",
					detail_value_add : "请输入属性值！",
					detail_sort_add : "请输入属性顺序！"
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
 * 新增属性
 */
function addDictDetail(l) {
	l.start();
	var dname = $("#detail_name_add").val();
	var dkey = $("#detail_key_add").val();
	var dvalue = $("#detail_value_add").val();
	var dsort = $("#detail_sort_add").val();
	var remark = $("#detail_remark_add").val();
	var data = {
		"dname" : dname,
		"dkey" : dkey,
		"dvalue" : dvalue,
		"remark" : remark,
		"dsort" : dsort,
		"pdid" : pdid
	};
	$.ajax({
		type : 'POST',
		dataType : "json",
		contentType : 'application/json;charset=utf-8',
		url : "addDictDetail",// 请求的action路径
		data : JSON.stringify(data),
		error : function() {// 请求失败处理函数
			swal('新增数据字典属性失败!', '', 'error');
		},
		success : function(data) { // 请求成功后处理函数。
			var result = data.result;
			if (result) {
				swal('新增数据字典属性成功!', '', 'success');
				$('#dictDetailAddModal').modal('hide');
				reloadDetailGrid();
			} else {
				swal('新增数据字典属性失败!', '', 'error');
			}
		},
		complete : function() {
			l.stop();
		}
	});
}