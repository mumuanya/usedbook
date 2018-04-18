//如果是登录状态的显示退出登录,如果未登录显示登录
$(window).ready(function () {
    if(!window.localStorage.getItem('token')){
        window.location.href = '/usedbook/pages/login.html';
    }
});
//注销登录的函数
function loginoff() {
    //清除令牌信息,相当于注销登录
    window.localStorage.clear();
    window.location.href = '/usedbook/pages/login.html';
}