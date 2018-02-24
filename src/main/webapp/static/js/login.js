$(document).ready(function() {
	$("#username").val("lantian")
	$("#password").val("lantian")
	// 初始化登录验证规则
	initValidateForm();

	// 初始化按钮
	initButton();
});

/**
 * 初始化登录验证规则
 */
function initValidateForm() {
	$('#loginForm').bootstrapValidator({
		message : '输入值验证失败',
		feedbackIcons : {
			// valid : 'glyphicon glyphicon-ok',
			valid : '',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			username : {
				message : '用户账号验证失败',
				validators : {
					notEmpty : {
						message : '用户账号不能为空'
					},
					// stringLength : {
					// min : 6,
					// max : 30,
					// message : '用户账号长度必须在6到30位之间'
					// },
					regexp : {
						regexp : /^[a-zA-Z0-9_\.]+$/,
						message : '用户账号只能包含大写、小写、数字和下划线'
					}
				}
			},
			password : {
				validators : {
					notEmpty : {
						message : '用户密码不能为空'
					},
					regexp : {
						regexp : /^[a-zA-Z0-9_\.]+$/,
						message : '用户密码只能包含大写、小写、数字和下划线'
					}
				}
			}
		}
	});
}

/**
 * 初始化按钮
 */
function initButton() {
	// 新增保存按钮
	$("#loginBtn").click(function() {
		var flag = $("#loginForm").data("bootstrapValidator").isValid();
		if (flag) {
			var username = $("#username").val();
			var password = $("#password").val();
			var data = {
				"username" : username,
				"password" : password
			};
			var l = Ladda.create(this);
			l.start();
			// 登录信息验证通过，执行登录操作
			$.ajax({
				type : 'POST',
				// async : false,
				dataType : "json",
				contentType : 'application/json;charset=utf-8',
				url : "doLogin",// 请求的action路径
				data : JSON.stringify(data),
				error : function() {// 请求失败处理函数
					// swal('新增数据字典失败!', '', 'error');
				},
				success : function(data) { // 请求成功后处理函数。
					var result = data.result;
					if (result) {
						$.notify({
							// options
							icon : "glyphicon glyphicon-ok",
							message : "登录成功",
						}, {
							// settings
							type : "success",
							placement : {
								from : "top",
								align : "right"
							}
						});
						setTimeout(function() {
							window.location.href = 'main';
						}, 500);

					} else {
						var msg = data.msg;
						$.notify({
							// options
							icon : "glyphicon glyphicon-warning-sign",
							title : "登录失败：",
							message : msg,
						}, {
							// settings
							type : "danger",
							placement : {
								from : "bottom",
								align : "right"
							}
						});
					}
				},
				complete : function() {
					l.stop();
				}
			});
		}
	});
}