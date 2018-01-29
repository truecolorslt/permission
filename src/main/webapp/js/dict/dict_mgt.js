$(document).ready(function() {
	// 初始化表格
	initDictsTable();
	// 初始化按钮
	initButton();
});

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
						rowNum : 10,
						rowList : [ 10, 20, 50 ],
						colModel : [
								{
									label : '数据字典名称',
									name : 'dname',
									index : 'dname',
									editable : true,
									width : 80,
								},
								{
									label : '数据字典编码',
									name : 'dcode',
									index : 'dcode',
									editable : false,
									width : 80
								},
								{
									label : '备注',
									name : 'remark',
									index : 'remark',
									editable : true,
									width : 80,
								},
								{
									label : '修改时间',
									name : 'modifiedTime',
									index : 'modifiedTime',
									editable : false,
									width : 80,
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
									name : 'did',
									index : 'did',
									width : 100,
									fixed : true,
									sortable : false,
									resize : false,
									// formatter : 'actions'
									formatter : function(cellvalue, options,
											rowObject) {
										// 调用删除方法
										var deleteFunction = "deleteDict('"
												+ cellvalue + "')";

										var actions = '<a href="#" onclick="" title="编辑数据">'
												+ '<i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>';
										actions += '&nbsp;&nbsp;&nbsp;&nbsp;';
										actions += '<a href="#" onclick="" title="查看明细">'
												+ '<i class="fa fa-file-text-o" aria-hidden="true"></i></a>';
										actions += '&nbsp;&nbsp;&nbsp;&nbsp;';
										actions += '<a href="#" onclick="'
												+ deleteFunction
												+ '" title="删除数据">'
												+ '<i class="fa fa-trash" aria-hidden="true"></i></a>';
										return actions;
									}
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
		jQuery("#table_list").jqGrid('setGridParam', {
			datatype : 'json',
			url : 'findDicts',
			postData : {
				'dname' : $("#dname").val(),
				'dcode' : $("#dcode").val()
			},
			page : 1
		}).trigger("reloadGrid");
	});

	// 重置按钮事件
	$("#resetBtn").click(function() {
		$("#dname").val("");
		$("#dcode").val("");
		return false;
	});
}

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
				//contentType : 'application/json;charset=utf-8',
				url : "deleteDict",// 请求的action路径
				data : param,
				error : function() {// 请求失败处理函数
					swal('删除数据字典失败!', '', 'error');
				},
				success : function(data) { // 请求成功后处理函数。
					var result = data.result;
					if (result) {
						swal('删除数据字典成功!', '', 'success');
						jQuery("#table_list").jqGrid('setGridParam', {
							datatype : 'json',
							url : 'findDicts',
							postData : {
								'dname' : $("#dname").val(),
								'dcode' : $("#dcode").val()
							},
							page : 1
						}).trigger("reloadGrid");
					} else {
						swal('删除数据字典失败!', '', 'error');
					}
				}
			});
		}
	});
	return false;
}