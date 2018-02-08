// ztree设置
var setting;
// ztree节点
var treeNodes;

$(document).ready(function() {
	// 初始化按钮事件
	initButton();
	// 初始化树形菜单
	initFunctionTrees();
	
});

/**
 * 初始化按钮事件
 */
function initButton() {
	$("#btn_save_new").click(function(){
		// 初始化新增form校验规则
		if(validateNewFunctionForm().form()) {
			// 验证成功
			var l = Ladda.create(this);
			addFunction(l);
		}
	});
	

	$("#btn_save_update").click(function(){
		// 初始化修改form校验规则
		if(validateUpdateFunctionForm().form()) {
			// 验证成功
			var l = Ladda.create(this);
			updateFunction(l);
		}
	});
}

/**
 * 新增功能
 */
function addFunction(l) {
	 l.start();
	 var fname = $("#fname_add").val();
	 var fcode = $("#fcode_add").val();
	 var furl = $("#furl_add").val();
	 var ficon = $("#ficon_add").val();
	 var fsort = $("#fsort_add").val();
	 var pfid = $("#pfid_add").val();
	 var data = {"fname":fname,"fcode":fcode,"furl":furl,
			 "ficon":ficon,"fsort":fsort,"pfid":pfid};
	 $.ajax({
			type : 'POST',
			dataType : "json",
			contentType : 'application/json;charset=utf-8',
			url : "addFunction",// 请求的action路径
			data:JSON.stringify(data),
			error : function() {// 请求失败处理函数
				swal(
					      '新增菜单失败!',
					      '',
					      'error');
			},
			success : function(data) { // 请求成功后处理函数。
				var result = data.result;
				if(result) {
					swal(
				      '新增菜单成功!',
				      '',
				      'success'
				    );
					$('#functionModal').modal('hide');
					// refreshNode(pfid);
					initFunctionTrees();
				} else {
					swal(
				      '新增菜单失败!',
				      '',
				      'error');
				}
			},
			complete : function(){
				l.stop();
	        }
		});
}

/**
 * 修改功能
 */
function updateFunction(l) {
	 l.start();
	 var fname = $("#fname").val();
	 var fcode = $("#fcode").val();
	 var furl = $("#furl").val();
	 var ficon = $("#ficon").val();
	 var fsort = $("#fsort").val();
	 var fid = $("#fid").val();
	 var data = {"fname":fname,"fcode":fcode,"furl":furl,
			 "ficon":ficon,"fsort":fsort,"fid":fid};
	 $.ajax({
			type : 'POST',
			dataType : "json",
			contentType : 'application/json;charset=utf-8',
			url : "updateFunction",// 请求的action路径
			data:JSON.stringify(data),
			error : function() {// 请求失败处理函数
				swal(
					      '修改菜单失败!',
					      '',
					      'error');
			},
			success : function(data) { // 请求成功后处理函数。
				var result = data.result;
				if(result) {
					swal(
						      '修改菜单成功!',
						      '',
						      'success');
					initFunctionTrees();
				} else {
					swal(
						      '修改菜单失败!',
						      '',
						      'error');
				}
			},
			complete : function(){
				l.stop();
	        }
		});
}

/**
 * 初始化树形菜单
 */
function initFunctionTrees() {
	setting = {
		treeId : "function_tree",
		view : {
			addHoverDom : addHoverDom,
			removeHoverDom : removeHoverDom,
			selectedMulti : false
		},
		check : {
			enable : false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : 0
			}
		},
		edit : {
			enable : true,
			showRenameBtn : false,
			removeTitle : "删除功能菜单",
			showRemoveBtn : setRemoveBtn,
		},
		callback : {
			onClick : getFunction,
			beforeRemove: deleteFunction,
			beforeClick : isRootNode
		},
		async:{
			enable: true,
			url: "findFunctionTreesByPfid",
			autoParam: ["id"]
		}
	};

	$.ajax({
		async : false,
		type : 'POST',
		dataType : "json",
		url : "findFunctionTrees",// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('树形菜单加载失败');
		},
		success : function(data) { // 请求成功后处理函数。
			treeNodes = data; // 把后台封装好的简单Json格式赋给treeNodes
		}
	});
	$.fn.zTree.init($("#functionTree"), setting, treeNodes);
}

/**
 * 增加节点样式
 * 
 * @param treeId
 * @param treeNode
 */
function addHoverDom(treeId, treeNode) {
	var pTreeNode = treeNode.getParentNode();
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
		return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='增加子功能菜单' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.tId);
	if (btn)
		btn.bind("click", function() {
			$("#functionModalLabel").text("新增功能菜单");
			$('#functionModal').modal();
			$("#pFunctionName_add").html(treeNode.name);
			$("#pfid_add").val(treeNode.id);
			return false;
		});
}

/**
 * 隐藏节点样式
 * 
 * @param treeId
 * @param treeNode
 */
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
}

/**
 * 获取功能菜单详情
 * 
 * @param fid
 */
function getFunction(event, treeId, treeNode) {
	var pTreeNode = treeNode.getParentNode();
	var param = {
		fid : treeNode.id
	};

	$.ajax({
		type : 'POST',
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		url : "getFunction",// 请求的action路径
		data : JSON.stringify(param),
		error : function() {// 请求失败处理函数
			alert('获取[' + treeNode.name + ']功能菜单信息失败');
		},
		success : function(data) { // 请求成功后处理函数。
			$("#pFunctionName").html(pTreeNode.name);
			$("#fname").val(data.fname);
			$("#fcode").val(data.fcode);
			$("#fsort").val(data.fsort);
			$("#ficon").val(data.ficon);
			$("#furl").val(data.furl);
			$("#fid").val(data.fid);
		}
	});
}


/**
 * 删除功能
 * 
 * @param event
 * @param treeId
 * @param treeNode
 */
function deleteFunction(treeId, treeNode) {
	var isParent = treeNode.isParent;
	if(isParent) {
		swal(
			      '不允许删除该菜单!',
			      '该菜单为父级菜单，其下还存在子菜单，请先删除子菜单！',
			      'warning'
			    );
		return false;
	}
	var param = {
			fid : treeNode.id
		};
	swal({
		  title: '请确认是否删除此功能菜单',
		  text: "此功能菜单被删除后将无法恢复!",
		  type: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: '确认',
		  cancelButtonText:'取消'
		}).then(function(isConfirm) {
		  if (isConfirm) {
			  $.ajax({
					type : 'POST',
					dataType : "json",
					contentType : 'application/json;charset=utf-8',
					url : "deleteFunction",// 请求的action路径
					data:JSON.stringify(param),
					error : function() {// 请求失败处理函数
						swal(
					      '删除菜单失败!',
					      '',
					      'error'
					    );
					},
					success : function(data) { // 请求成功后处理函数。
						var result = data.result;
						if(result) {
							swal(
						      '删除菜单成功!',
						      '',
						      'success'
						    );
							initFunctionTrees();
						} else {
							swal(
						      '删除菜单失败!',
						      '',
						      'error'
						    );
						}
					}
				});
		  }
		});
	return false;
}

/**
 * 判断是否为根节点
 * 
 * @param treeId
 * @param treeNode
 * @param clickFlag
 */
function isRootNode(treeId, treeNode, clickFlag) {
	return treeNode.id != "0";
}

/**
 * 判断为顶级节点
 * 
 * @param treeId
 * @param treeNode
 * @returns {Boolean}
 */
function setRemoveBtn(treeId, treeNode) {
	// 判断为顶级节点则不显示删除按钮
	if (treeNode.level == 0) {
		return false;
	} else {
		return true;
	}
}

/**
 * 校验修改菜单的form规则
 */
function validateUpdateFunctionForm() {
	return $("#updateFunctionForm").validate({
		rules: {
			fname:"required",
			fcode:"required",
			fsort:{
				digits:true,
				range:[1,100]
			}
	    },
	    messages: {
	    	fname:"请输入功能名称！",
			fcode:"请输入功能编码！",
			fsort:{
				digits:"请输入正整数！",
				range:"数字范围：1～100！"
			}
	     },
		// the errorPlacement has to take the table layout into account
		errorPlacement: function(error, element) {
			if (element.is(":radio")) {
				error.appendTo(element.parent().next());
			} else if (element.is(":checkbox")){
				error.appendTo(element.next());
			} else {
				error.appendTo(element.parent().next());
			}
		},
		// set this class to error-labels to indicate valid fields
		success: function(label) {
			// set &nbsp; as text for IE
			label.html("&nbsp;").addClass("checked");
			// label.addClass("valid").text("Ok!")
		},
		highlight: function(element, errorClass) {
			$(element).parent().next().find("." + errorClass).removeClass("checked");
		}
	});
}

/**
 * 校验新增菜单的form规则
 */
function validateNewFunctionForm() {
	return $("#newFunctionForm").validate({
		rules: {
			fname_add:"required",
			fcode_add:"required",
			// furl:"required",
			fsort_add:{
				digits:true,
				range:[1,100]
			}
			// ficon:"required"
	    },
	    messages: {
	    	fname_add:"请输入功能名称！",
			fcode_add:"请输入功能编码！",
			fsort_add:{
				digits:"请输入正整数！",
				range:"数字范围：1～100！"
			}
			// furl:"请输入功能链接",
			// fsort_add:"请输入正整数！"
			// ficon:"required"
	     },
		// the errorPlacement has to take the table layout into account
		errorPlacement: function(error, element) {
			if (element.is(":radio")) {
				error.appendTo(element.parent().next());
			} else if (element.is(":checkbox")){
				error.appendTo(element.next());
			} else {
				error.appendTo(element.parent().next());
			}
		},
		// set this class to error-labels to indicate valid fields
		success: function(label) {
			// set &nbsp; as text for IE
			label.html("&nbsp;").addClass("checked");
			// label.addClass("valid").text("Ok!")
		},
		highlight: function(element, errorClass) {
			$(element).parent().next().find("." + errorClass).removeClass("checked");
		}
	});
}


// 刷新父节点
function rereshParentNode(id){  
    var treeObj = $.fn.zTree.getZTreeObj("functionTree");  
    var nownode = treeObj.getNodesByParam("id", id, null);  
    var parent=nownode[0].getParentNode();  
    treeObj.reAsyncChildNodes(parent, "refresh");  
}  
  
// 刷新当前节点
function refreshNode(id){  
    var treeObj = $.fn.zTree.getZTreeObj("functionTree");  
    var nownode = treeObj.getNodesByParam("id", id, null);
    treeObj.reAsyncChildNodes(nownode[0], "refresh");  
}