$(window).ready(function () {
    //以下是删除用户按钮 修改客户按钮 查询宠物按钮 的开与闭
    $('#booktable').on('check.bs.table',function (row,elem) {
        //先打开按钮
        openBtn();

        //then do someting here
        //修改update modal 中的数据,以便修改的时候更改
        var rows = $('#booktable').bootstrapTable('getSelections');
        var uelems = $("#bookupdateinfo").children('input');
        $(uelems[0]).val($(rows).last()[0].id);
        $(uelems[1]).val($(rows).last()[0].name);
        $(uelems[2]).val($(rows).last()[0].creatime);
        $(uelems[3]).val($(rows).last()[0].price);
        $(uelems[4]).val($(rows).last()[0].userid);
        $(uelems[5]).val($(rows).last()[0].img1);
        $(uelems[6]).val($(rows).last()[0].img2);
        $(uelems[7]).val($(rows).last()[0].img3);
        
      //设置select的默认值
        var selects = $("#bookupdateinfo").children('select');
        if($(rows).last()[0].degree == '9'){
        	$(selects[0]).find('option[value="9"]').attr("selected",true);
        	$(selects[0]).find('option[value="8"]').removeAttr("selected");
        	$(selects[0]).find('option[value="7"]').removeAttr("selected");
        	$(selects[0]).find('option[value="0"]').removeAttr("selected");
        }else if($(rows).last()[0].degree == '8'){
        	$(selects[0]).find('option[value="8"]').attr("selected",true);
        	$(selects[0]).find('option[value="9"]').removeAttr("selected");
        	$(selects[0]).find('option[value="7"]').removeAttr("selected");
        	$(selects[0]).find('option[value="0"]').removeAttr("selected");
        }else if($(rows).last()[0].degree == '7'){
        	$(selects[0]).find('option[value="7"]').attr("selected",true);
        	$(selects[0]).find('option[value="9"]').removeAttr("selected");
        	$(selects[0]).find('option[value="8"]').removeAttr("selected");
        	$(selects[0]).find('option[value="0"]').removeAttr("selected");
        }else if($(rows).last()[0].degree == '0'){
        	$(selects[0]).find('option[value="0"]').attr("selected",true);
        	$(selects[0]).find('option[value="9"]').removeAttr("selected");
        	$(selects[0]).find('option[value="8"]').removeAttr("selected");
        	$(selects[0]).find('option[value="7"]').removeAttr("selected");
        }
        //校区
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
    });


    $('#booktable').on('uncheck.bs.table', function (row,elem) {
        closeBtn();
    });
}
);

//删除一个vip用户
function deletebook() {
    //获取选中元素的id值
    var rows = $('#booktable').bootstrapTable('getSelections');
    var id = $(rows).last()[0].id;

    //使用ajax删除
    $.ajax({
        url:'/usedbook/admin/book/delete/' + id,
        type:'post',
        dataType:'json',
        data:{
            token:window.localStorage.getItem("token")
        },
        async:'true',
        success:function (datas) {
        	alert(datas.data);
            closeBtn();
            $("#deletebook").modal('hide');
            refreshbook();
        }
    });
}
//更新用户的基本信息
function updatebookpriminfo() {
    //获取选中元素的id值和name phone等值
	var updateinfo = $('#bookupdateinfo').children('input');
    var bookid = updateinfo[0].value;
    var name =updateinfo[1].value;
    var price = updateinfo[3].value;
    var img1 =updateinfo[5].value;
    var img2 = updateinfo[6].value;
    var img3 = updateinfo[7].value;
    
    var updateinfo2 = $('#bookupdateinfo').children('select');
    var degree = updateinfo2[0].value;
    var district = updateinfo2[1].value;

    //发送修改请求
    $.ajax({
        url:"/usedbook/admin/book/update",
        type:'post',
        dataType:'json',
        data:{
        	bookid: bookid,
        	name: name,
        	price: price,
        	img1: img1,
        	img2: img2,
        	img3: img3,
            degree: degree,
            district: district,
            token: window.localStorage.getItem("token")
        },
        async:true,
        success:function (datas) {
            alert(datas.data);
            closeBtn();
            $("#updatebook").modal('hide');
            refreshbook();
        }
    });

}

function closeBtn() {
    $("button[name='deletebook']").attr('disabled', 'disabled');
    $("button[name='updatebook']").attr('disabled', 'disabled');
}
function openBtn() {
    $("button[name='deletebook']").removeAttr('disabled');
    $("button[name='updatebook']").removeAttr('disabled');
}

function refreshbook(){
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
}