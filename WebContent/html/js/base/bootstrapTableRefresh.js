$(document).ready(function(){
	$.ajax({
		type:"get",
		url:"/usedbook/admin/users",
		async:true,
		data:{
			'token':window.localStorage.getItem("token")
		},
		dataType:'json',
		success:function(data){
			$('#usertable').bootstrapTable('load', data);
		}
	});
	$.ajax({
		type:"get",
		url:"/usedbook/admin/books",
		async:true,
		data:{
			'token':window.localStorage.getItem("token")
		},
		dataType:'json',
		success:function(data){
			$('#booktable').bootstrapTable('load', data);
		}
	});
});