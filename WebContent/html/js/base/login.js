function ajaxlogin () {
	//首先获取密码
	var mail = $('input[name=mail]').val();
	var password = $('input[name=password]').val();
	//点击按钮时发送ajax请求
	$.ajax({
		type:"post",
		url:"/usedbook/admin/login",
		async:true,
		data:{
			'mail':mail,
			'password':password
		},
		dataType:'json',
		success:function(datas){
			//判断登录状态
			if(datas.code == 0){
				//登录成功
				var storage = window.localStorage;
				storage.setItem("token",datas.data);
				window.location.href = '/usedbook/pages/index.html';
			}else if(datas.code == -1){
				//登录失败
				alert(datas.data);
			}
		}
	});
}
