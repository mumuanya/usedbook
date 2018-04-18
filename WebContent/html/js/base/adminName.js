$(document).ready(function() {
	//获取用户的基本信息
	$.ajax({
		type : "get",
		url : "/usedbook/user/info",
		async : true,
		data : {
			'token' : window.localStorage.getItem("token")
		},
		dataType : 'json',
		success : function(datas) {
			//判断登录状态
			if (datas.code == 0) {
				//获取用户信息成功
				$("#admin").text(datas.data.neckname);
			}
		}
	});
});