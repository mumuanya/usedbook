$(window).ready(function () {
    //以下是删除用户按钮 修改客户按钮 查询宠物按钮 的开与闭
    $('#usertable').on('check.bs.table',function (row,elem) {
        //先打开按钮
        openBtn();

        //then do someting here
        //修改update modal 中的数据,以便修改的时候更改
        var rows = $('#usertable').bootstrapTable('getSelections');
        var uelems = $("#userupdateinfo").children('input');
        $(uelems[1]).val($(rows).last()[0].mail);
        $(uelems[2]).val($(rows).last()[0].neckname);
        $(uelems[0]).val($(rows).last()[0].id);
        $(uelems[3]).val($(rows).last()[0].pic);
        $(uelems[4]).val($(rows).last()[0].password);
        $(uelems[5]).val($(rows).last()[0].token);
        $(uelems[6]).val($(rows).last()[0].phone);
        $(uelems[7]).val($(rows).last()[0].addr);
        
        //设置select的默认值
        var selects = $("#userupdateinfo").children('select');
        if($(rows).last()[0].district == 'YaAn'){
        	$(selects[1]).find('option[value="YaAn"]').attr("selected",true);
        	$(selects[1]).find('option[value="ChengDu"]').removeAttr("selected");
        	$(selects[1]).find('option[value="DuJiangyan"]').removeAttr("selected");
        }else if($(rows).last()[0].district == 'ChengDu'){
        	$(selects[1]).find('option[value="ChengDu"]').attr("selected",true);
        	$(selects[1]).find('option[value="YaAn"]').removeAttr("selected");
        	$(selects[1]).find('option[value="DuJiangyan"]').removeAttr("selected");
        }else if($(rows).last()[0].district == 'DuJiangyan'){
        	$(selects[1]).find('option[value="DuJiangYan"]').attr("selected",true);
        	$(selects[1]).find('option[value="ChengDu"]').removeAttr("selected");
        	$(selects[1]).find('option[value="YaAn"]').removeAttr("selected");
        }
        //角色
        if($(rows).last()[0].role == '1'){
        	$(selects[0]).find('option[value=1]').attr("selected",true);
        	$(selects[0]).find('option[value=0]').removeAttr("selected");
        }else if($(rows).last()[0].role == '0'){
        	$(selects[0]).find('option[value=0]').attr("selected",true);
        	$(selects[0]).find('option[value=1]').removeAttr("selected");
        }
    });
    
    $('#usertable').on('uncheck.bs.table', function (row,elem) {
        closeBtn();
    });
}
);

//下面是modal的函数了.
//删除一个vip用户
function deleteuser() {
    //获取选中元素的id值
    var rows = $('#usertable').bootstrapTable('getSelections');
    var id = $(rows).last()[0].id;

    //使用ajax删除
    $.ajax({
        url:"/usedbook/admin/user/delete/" + id,
        type:'post',
        dataType:'json',
        data:{
            token:window.localStorage.getItem("token")
        },
        async:'true',
        success:function (datas) {
            if(datas.code == 0){
                alert("删除成功!");

            }else if(datas.code == -1){
                alert(datas.data);
            }
            closeBtn();
            $("#deleteuser").modal('hide');
            refreshuser();
        }
    });
}
//更新用户的基本信息
function updateuserpriminfo() {
    //获取选中元素的id值和name phone等值
    var updateinfo = $('#userupdateinfo').children('input');
    var userid = updateinfo[0].value;
    var neckname =updateinfo[2].value;
    var pic = updateinfo[3].value;
    var password = updateinfo[4].value;
    var usertoken = updateinfo[5].value;
    var phone = updateinfo[6].value;
    var addr = updateinfo[7].value;
    
    var updateinfo2 = $('#userupdateinfo').children('select');
    var role = updateinfo2[0].value;
    var district = updateinfo2[1].value;

    //发送修改请求
    $.ajax({
        url:"/usedbook/admin/user/update",
        type:'post',
        dataType:'json',
        data:{
        	userid: userid,
        	neckname: neckname,
        	pic: pic,
        	password: password,
        	usertoken: usertoken,
        	phone: phone,
        	addr: addr,
        	role: role,
        	district: district,
            token: window.localStorage.getItem("token")
        },
        async:true,
        success:function (datas) {
        	if(datas.code == 0){
        		alert(datas.data);
        	}else if(datas.code == -1){
        		alert(datas.data);
        	}
            closeBtn();
            $("#updateuser").modal('hide');
            refreshuser();
        }
    });

}

function closeBtn() {
    $("button[name='deleteuser']").attr('disabled', 'disabled');
    $("button[name='updateuser']").attr('disabled', 'disabled');
}
function openBtn() {
    $("button[name='deleteuser']").removeAttr('disabled');
    $("button[name='updateuser']").removeAttr('disabled');
}

function refreshuser(){
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
}