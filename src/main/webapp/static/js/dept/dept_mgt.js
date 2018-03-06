// ztree设置
var setting;
// ztree节点
var treeNodes;

$(document).ready(function() {
	// 初始化树形菜单
	initDepartmentTrees();
	// 初始化按钮事件
	initButton();
});


/**
 * 初始化树形菜单
 */
function initDepartmentTrees() {
	setting = {
		treeId : "department_tree",
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
			removeTitle : "删除部门菜单",
			showRemoveBtn : setRemoveBtn,
		},
		callback : {
			onClick : getDepartment,
			beforeRemove: deleteDepartment,
			beforeClick : isRootNode
		},
		async:{
// enable: true,
// url: "findDepartmentTreesByPfid",
// autoParam: ["id"]
		}
	};

	var param = {
		src : "owner"
	};
	$.ajax({
		data : param,
		async : false,
		type : 'POST',
		dataType : "json",
		url : "findDepartmentTrees",// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('树形菜单加载失败');
		},
		success : function(data) { // 请求成功后处理函数。
			treeNodes = data; // 把后台封装好的简单Json格式赋给treeNodes
		}
	});
	$.fn.zTree.init($("#departmentTree"), setting, treeNodes);
}

/**
 * 初始化按钮事件
 */
function initButton() {
	$("#btn_save_new").click(function(){
		// 初始化新增form校验规则
		if(validateDepartmentForm().form()) {
			// 验证成功
			var l = Ladda.create(this);
			addDepartment(l);
		}
	});

	$("#btn_save_update").click(function(){
		// 初始化修改form校验规则
		if(validateUpdateDepartmentForm().form()) {
			// 验证成功
			var l = Ladda.create(this);
			updateDepartment(l);
		}
	});
}

/**
 * 校验新增菜单的form规则
 */
function validateDepartmentForm() {
	return $("#departmentForm").validate({
		rules: {
			dname_add:"required",
			dcode_add:"required",
			dsort_add:{
				digits:true,
				range:[1,100]
			}
	    },
	    messages: {
	    	dname_add:"请输入部门名称！",
			dcode_add:"请输入部门编码！",
			dsort_add:{
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
function validateUpdateDepartmentForm() {
	return $("#updateDepartmentForm").validate({
		rules: {
			dname:"required",
			dcode:"required",
			dsort:{
				digits:true,
				range:[1,100]
			}
	    },
	    messages: {
	    	dname:"请输入部门名称！",
			dcode:"请输入部门编码！",
			dsort:{
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
			+ "' title='增加子部门菜单' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.tId);
	if (btn)
		btn.bind("click", function() {
			$("#departmentModalLabel").text("新增部门菜单");
			$('#departmentModal').modal();
			$("#pDepartmentName_add").html(treeNode.name);
			$("#pdid_add").val(treeNode.id);
			$("#dname_add").val("");
			$("#dcode_add").val("");
			$("#dsort_add").val("");
			$(".mblack").html("");
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
 * 获取部门详情
 * 
 * @param fid
 */
function getDepartment(event, treeId, treeNode) {
	$(".mblack").html("");
	var pTreeNode = treeNode.getParentNode();
	var param = {
		did : treeNode.id
	};

	$.ajax({
		type : 'POST',
		dataType : "json",
		data : param,
		url : "getDepartment",// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('获取[' + treeNode.name + ']部门信息失败');
		},
		success : function(data) { // 请求成功后处理函数。
			$("#pDepartmentName").html(pTreeNode.name);
			$("#dname").val(data.dname);
			$("#dcode").val(data.dcode);
			$("#dsort").val(data.dsort);
			$("#did").val(data.did);
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
function deleteDepartment(treeId, treeNode) {
	var isParent = treeNode.isParent;
	if(isParent) {
		swal(
			      '不允许删除该部门!',
			      '该菜单为父级部门，其下还存在子部门，请先删除子部门！',
			      'warning'
			    );
		return false;
	}
	var param = {
			did : treeNode.id
		};
	swal({
		  title: '请确认是否删除此部门',
		  text: "此部门被删除后将无法恢复!",
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
					// contentType : 'application/json;charset=utf-8',
					url : "deleteDepartment",// 请求的action路径
					data : param,
					error : function() {// 请求失败处理函数
						swal(
					      '删除部门失败!',
					      '',
					      'error'
					    );
					},
					success : function(data) { // 请求成功后处理函数。
						var result = data.result;
						if(result) {
							swal(
						      '删除部门成功!',
						      '',
						      'success'
						    );
							initDepartmentTrees();
						} else {
							swal(
						      '删除部门失败!',
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
 * 新增功能
 */
function addDepartment(l) {
	 l.start();
	 var dname = $("#dname_add").val();
	 var dcode = $("#dcode_add").val();
	 var dsort = $("#dsort_add").val();
	 var pdid = $("#pdid_add").val();
	 var data = {"dname":dname,"dcode":dcode,"dsort":dsort,"pdid":pdid};
	 $.ajax({
			type : 'POST',
			dataType : "json",
			contentType : 'application/json;charset=utf-8',
			url : "addDepartment",// 请求的action路径
			data:JSON.stringify(data),
			error : function() {// 请求失败处理函数
				swal(
					      '新增部门失败!',
					      '',
					      'error');
			},
			success : function(data) { // 请求成功后处理函数。
				var result = data.result;
				if(result) {
					swal(
				      '新增部门成功!',
				      '',
				      'success'
				    );
					$('#departmentModal').modal('hide');
					// refreshNode(pfid);
					initDepartmentTrees();
				} else {
					swal(
				      '新增部门失败!',
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
function updateDepartment(l) {
	 l.start();
	 var dname = $("#dname").val();
	 var dcode = $("#dcode").val();
	 var dsort = $("#dsort").val();
	 var did = $("#did").val();
	 var data = {"dname":dname,"dcode":dcode,
			"dsort":dsort,"did":did};
	 $.ajax({
			type : 'POST',
			dataType : "json",
			contentType : 'application/json;charset=utf-8',
			url : "updateDepartment",// 请求的action路径
			data:JSON.stringify(data),
			error : function() {// 请求失败处理函数
				swal(
					      '修改部门失败!',
					      '',
					      'error');
			},
			success : function(data) { // 请求成功后处理函数。
				var result = data.result;
				if(result) {
					swal(
						      '修改部门成功!',
						      '',
						      'success');
					initDepartmentTrees();
				} else {
					swal(
						      '修改部门失败!',
						      '',
						      'error');
				}
			},
			complete : function(){
				l.stop();
	        }
		});
}
