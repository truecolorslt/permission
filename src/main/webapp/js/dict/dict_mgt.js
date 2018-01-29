$(document).ready(function() {
	// 初始化表格
	initDictsTable();
	// 初始化按钮
	initButton();
});

var lastsel;
var lastselCol;

/**
 * 初始化表格
 */
function initDictsTable() {
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
						// onSelectRow : editSelectRow,
						onCellSelect : editSelectRow,
						rowNum : 10,
						rowList : [ 10, 20, 50 ],
						colModel : [
								{
									label : '数据字典名称<font color=gray size=1px>（单击编辑）</font>',
									name : 'dname',
									index : 'dname',
									editable : true,
									width : 85,
									sortable : false
								},
								{
									label : '数据字典编码',
									name : 'dcode',
									index : 'dcode',
									editable : false,
									width : 80
								},
								{
									label : '备注<font color=gray size=1px>（单击编辑）</font>',
									name : 'remark',
									index : 'remark',
									editable : true,
									width : 80,
									sortable : false
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
										var editFunction = "";
										// 调用删除方法
										var deleteFunction = "deleteDict('"
												+ rowObject.did + "')";

										var actions = '<a href="#" onclick="" title="查看明细">'
												+ '<i class="fa fa-file-text-o" aria-hidden="true"></i></a>';
										actions += '&nbsp;&nbsp;&nbsp;&nbsp;';
										actions += '<a href="#" onclick="'
												+ deleteFunction
												+ '" title="删除数据">'
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
						hidegrid : false
					});

	// 自应高度
	var newHeight = $(window).height() - 265;
	$(".ui-jqgrid .ui-jqgrid-bdiv").css("cssText",
			"height: " + newHeight + "px!important;");

	// Add selection
	$("#table_list").setSelection(4, true);

	// Setup buttons
	$("#table_list").jqGrid('navGrid', '#pager_list', {
		edit : false,
		add : true,
		del : true,
		search : false
	}, {
		height : 200,
		reloadAfterSubmit : true
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
}

/**
 * 重新加载表格
 */
function reloadGrid() {
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
 * 保存上一行数据
 * 
 * @param id
 */
function editSelectRow(rowid, iCol, cellcontent) {
	if (iCol == 1 || iCol == 3) {
		if (rowid
				&& iCol
				&& ((rowid != lastsel) || (rowid == lastsel && iCol != lastselCol))) {
			// 保存上一行数据
			saveDict();

			// 启用当前行为编辑状态
			jQuery('#table_list').jqGrid('restoreRow', lastsel);
			jQuery('#table_list').jqGrid('editRow', rowid, true);
			// 当前选中行,临时存储当前选中行
			lastsel = rowid;
		}
	} else {
		lastselCol = iCol;
		jQuery('#table_list').jqGrid('restoreRow', lastsel);
	}
}

/**
 * 执行保存
 */
function saveDict() {
	// 原选中行ID
	var oldSelectRowId = lastsel;
	if (oldSelectRowId != null && oldSelectRowId != ""
			&& oldSelectRowId.length > 0) {
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
