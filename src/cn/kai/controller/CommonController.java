
package cn.kai.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.kai.bean.userbean.UserReturnBean;
import cn.kai.model.Book;
import cn.kai.model.User;
import cn.kai.service.BookService;
import cn.kai.service.CommonService;
import cn.kai.service.UserService;
import cn.kai.util.MD5Encoder;

/**
* @author HuangKai  

* @date 2018年4月14日  

* @version 1.0  

*/
@Controller
public class CommonController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	CommonService commonService;
	
	/**
	 * 任何人都可以请求，返回一部分书或者所有书
	 * @param limit   书的本数
	 * @return   一堆书的json格式
	 */
	@ResponseBody
	@RequestMapping(value="/home/books",method=RequestMethod.GET)
	public String getBooks(@RequestParam(required=false) Integer limit) {
		//对limit做处理
		if(limit == null || limit < 0)
			limit = 999;
		
		List<Book> books = commonService.getAllBook(limit);
		
		UserReturnBean<List<Book>> urb = new UserReturnBean<>();
		urb.withData(books);
		return JSON.toJSONString(urb);
	}
	
	/**
	 * 登录
	 * @param mail   邮箱
	 * @param password  密码
	 * @return   成功-token  失败 - 错误信息
	 */
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam String mail, @RequestParam String password) {
		
		//参数合法性验证
		if(mail.equals("") || password.equals("")) {
			//传入了空串
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("非法参数的传入");
			return JSON.toJSONString(urb);
		}
		User user = userService.login(mail, password);
		if(user != null) {
			//登录成功
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.success(user.getToken());
			return JSON.toJSONString(urb);
		}else {
			//登录失败
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("登录失败了！");
			return JSON.toJSONString(urb);
		}
	}
	
	/**
	 *   注册
	 * @param neckname
	 * @param mail
	 * @param pic
	 * @param password
	 * @param phone
	 * @param addr
	 * @param district
	 * @return 注册是否成功  成功-返回token   失败-失败
	 */
	@ResponseBody
	@RequestMapping(value="/regist",method=RequestMethod.POST)
	public String regist(@RequestParam(required=false) String neckname,
			@RequestParam String mail,
			@RequestParam(required=false) String pic,
			@RequestParam String password,
			@RequestParam(required=false) String phone,
			@RequestParam(required=false) String addr,
			@RequestParam String district) {
		//参数合法性验证
		if(mail.equals("") || password.equals("") || district.equals("")) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("必须传入参数有误");
			return JSON.toJSONString(urb);
		}
		//判断是否mail重复
		if(userService.findOneUserWithMail(mail) != null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("mail已经被人注册了！");
			return JSON.toJSONString(urb);
		}
		//检查district是否合法
		if(!district.equals("YaAn") && !district.equals("ChengDu") && !district.equals("DuJiangyan")) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("地址不合法");
			return JSON.toJSONString(urb);
		}
		//开始生成注册信息
		User user = new User();
		if(neckname != null && !neckname.equals("")) user.setNeckname(neckname);
		user.setMail(mail);
		if(pic != null && !pic.equals("")) user.setPic(pic);
		user.setPassword(password);
		if(phone != null && !phone.equals("")) user.setPhone(phone);
		user.setDistrict(district);
		if(addr != null && !addr.equals("")) user.setAddr(addr);
		//生成token并注入  使用时间戳的MD5加密作为唯一的token
		String token = MD5Encoder.md5Password(Long.toString(new Date().getTime()));
		user.setToken(token);
		User user2 = userService.regist(user);
		if(user2 != null) {
			//注册成功
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.success(user2.getToken());
			return JSON.toJSONString(urb);
		}else {
			//注册失败
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("注册失败了！");
			return JSON.toJSONString(urb);
		}
	}
}
