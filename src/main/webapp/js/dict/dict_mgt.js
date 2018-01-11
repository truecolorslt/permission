$(document).ready(function() {
	initDicts();

	initButton();
});

/**
 * 初始化表格
 */
function initDicts() {
	$.jgrid.defaults.styleUI = 'Bootstrap';

	$("#table_list").jqGrid({
		// data : data,
		url : "findDicts",
		mtype : "POST",
		rownumbers : true,
		datatype : "json",
		height : 370,
		autowidth : true,
		shrinkToFit : true,
		// viewrecords:true,
		rowNum : 10,
		rowList : [ 10, 20, 50 ],
		colNames : [ '数据字典名称', '数据字典编码', '备注', '修改时间', '修改人', '操作' ],
		colModel : [ {
			name : 'dname',
			index : 'dname',
			editable : true,
			width : 90,
		}, {
			name : 'dcode',
			index : 'dcode',
			editable : true,
			width : 100
		}, {
			name : 'remark',
			index : 'remark',
			editable : true,
			width : 80,
		}, {
			name : 'modifiedTime',
			index : 'modifiedTime',
			editable : true,
			width : 80,
			sorttype : "date",
			formatter : "date"
		}, {
			name : 'modifier',
			index : 'modifier',
			editable : true,
			width : 80,
		}, {
			name : 'operate',
			index : 'operate',
			editable : true,
			width : 80,
		} ],
		pager : "#pager_list",
		viewrecords : true,
		// caption : "数据字典列表",
		add : true,
		edit : true,
		addtext : 'Add',
		edittext : 'Edit',
		hidegrid : false
	});

	// Add selection
	$("#table_list").setSelection(4, true);

	// Setup buttons
	$("#table_list").jqGrid('navGrid', '#pager_list', {
		edit : true,
		add : true,
		del : true,
		search : true
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
	$("#find_btn").click(function() {
		jQuery("#table_list").jqGrid('setGridParam', {
			url : 'findDicts',
			postData : {
				'dname' : $("#dname").val(),
				'dcode' : $("#dcode").val()
			},
			page : 1
		}).trigger("reloadGrid");
	});
}