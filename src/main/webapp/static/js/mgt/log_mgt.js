$(document).ready(function() {
	// 初始化表格
	initTable();
	// 初始化按钮
	initButton();
	// 初始化下拉框
	initSelect();
	$('#startDate').fdatepicker({
		format : 'yyyy-mm-dd'
	});
	$('#endDate').fdatepicker({
		format : 'yyyy-mm-dd'
	});
	$("#startDate").dblclick(function() {
		$("#startDate").val("");
	});
	$("#endDate").dblclick(function() {
		$("#endDate").val("");
	});
});

/**
 * 初始化表格
 */
function initTable() {
	$.jgrid.defaults.styleUI = 'Bootstrap';
	$("#table_list").jqGrid(
			{
				url : "findLogs",
				mtype : "POST",
				rownumbers : true,
				datatype : "json",
				// height : 370,
//				width : 900,
				autowidth:true,
				// autowidth : true,
				viewrecords : true,
				rowNum : 10,
				rowList : [ 10, 20, 50 ],
				colModel : [ {
					label : '操作人员',
					name : 'creator',
					index : 'creator',
					editable : false,
					width : 120
				}, {
					label : '操作时间',
					name : 'createdTime',
					index : 'createdTime',
					width : 80,
					fixed : true,
					sortable : false
				}, {
					label : '日志类型',
					name : 'logType',
					index : 'logType',
					editable : false,
					width : 80,
					sortable : false
				}, {
					label : '日志描述',
					name : 'description',
					index : 'description',
					editable : false,
					width : 120
				}, {
					label : '相关功能名称',
					name : 'functionDetail',
					index : 'functionDetail',
					editable : true,
					width : 200,
					sortable : false
				}, {
					label : '请求IP',
					name : 'requestIp',
					index : 'requestIp',
					editable : false,
					width : 120,
					sortable : false
				}, {
					label : '请求参数',
					name : 'param',
					index : 'param',
					width : 400,
					formatter : function(cellvalue, options, rowObject) {
						return JSON.stringify(rowObject.param, null, "\t");
					}
				} ],
				shrinkToFit : false,
				autoScroll : true,
				pager : "#pager_list",
				// pagerpos : 'center',
				// recordpos : 'right',
				viewrecords : true,
				hidegrid : false,
				loadComplete : function() {
					var re_records = $("#table_list").getGridParam('records');
					if (re_records == 0 || re_records == null) {
						if ($(".norecords").html() == null) {
							$("#table_list").parent().append(
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
	//水平方向scrollbar
	$(".ui-jqgrid .ui-jqgrid-bdiv").css("overflow-x",
			"scroll");
	

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
		$("#logType").val("");
		$("#creator").val("");
		$("#startDate").val("");
		$("#endDate").val("");
		return false;
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
		url : 'findLogs',
		postData : {
			'logType' : $("#logType").val(),
			'creator' : $("#creator").val(),
			'startDate' : $("#startDate").val(),
			'endDate' : $("#endDate").val(),
		},
		page : 1
	}).trigger("reloadGrid");
}

/**
 * 初始化下拉菜单
 */
function initSelect() {
	// 初始化数据字典：性别
	var param = {
		code : "LOG_TYPE"
	};
	$.ajax({
		data : param,
		type : 'POST',
		url : "../dict/getDictAttrsByCode",
		dataType : "json",
		success : function(data) {
			$.each(data, function(index, element) {
				$("#logType").append(
						"<option value=" + element.value + ">" + element.text
								+ "</option>");

			});
		},

		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
}