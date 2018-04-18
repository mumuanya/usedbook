
package cn.kai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

/**
* @author HuangKai  

* @date 2018年4月14日  

* @version 1.0  
* 管理员操作时的请求处理类
*/
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService userService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	CommonService commonService;
	
	/**
	 * 管理员登录处理方法
	 * @param mail  管理员的电子邮件
	 * @param password 管理员的密码
  	 * @return  登录成功-返回成功和session 失败-返回登录失败
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
		if(user != null && user.getRole() == 1) {
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
	 * 管理员查询所有的用户信息
	 * @param token  管理员的令牌
	 * @return  所有的用户信息 json格式
	 */
	@ResponseBody
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public String allUser(@RequestParam String token) {
		//鉴权
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("未查询到此token信息");
			return JSON.toJSONString(urb);
		}else {
			User user = userService.findOneUserWithToken(token);
			if(user.getRole() != 1) {
				//不是管理员
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("非法用户");
				return JSON.toJSONString(urb);
			}else {
				//是管理员
				List<User> users = userService.selectAllUser();
				return JSON.toJSONString(users);
			}
		}
	}
	
	/**
	 * 管理员查询所有的书本信息
	 * @param token 管理员token
	 * @return   所有书书本信息
	 */
	@ResponseBody
	@RequestMapping(value="/books",method=RequestMethod.GET)
	public String allBooks(@RequestParam String token) {
		//鉴权
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("未查询到此token信息");
			return JSON.toJSONString(urb);
		}else {
			User user = userService.findOneUserWithToken(token);
			if(user.getRole() != 1) {
				//不是管理员
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("非法用户");
				return JSON.toJSONString(urb);
			}else {
				//是管理员
				List<Book> books = bookService.getAllBooks();
				return JSON.toJSONString(books);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/user/delete/{userid}",method=RequestMethod.POST)
	public String deleteUser(@RequestParam String token, @PathVariable Integer userid) {
		//鉴权
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("未查询到此token信息");
			return JSON.toJSONString(urb);
		}else {
			User user = userService.findOneUserWithToken(token);
			if(user.getRole() != 1) {
				//不是管理员
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("非法用户");
				return JSON.toJSONString(urb);
			}else {
				//是管理员
				if(userService.deleteOneUser(userid) > 0) {
					//删除成功
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.success("删除用户成功");
					return JSON.toJSONString(urb);
				}else {
					//删除失败
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.fail("删除用户失败");
					return JSON.toJSONString(urb);
				}
			}
		}
	}
	
	/**
	 * 管理员更新用户的信息
	 * @param token  管理员token，用于验证管理员身份
	 * @param userid  用户的id
	 * @param neckname 昵称
	 * @param pic 图片
	 * @param password  用户的密码
	 * @param usertoken  用户的令牌
	 * @param phone   用户电话
	 * @param addr  用户地址
	 * @param role   用户角色
	 * @param district   用户校区
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/update",method=RequestMethod.POST)
	public String updateUser(@RequestParam String token,
			@RequestParam Integer userid,
			@RequestParam(required=false) String neckname,
			@RequestParam(required=false) String pic,
			@RequestParam(required=false) String password,
			@RequestParam(required=false) String usertoken,
			@RequestParam(required=false) String phone,
			@RequestParam(required=false) String addr,
			@RequestParam(required=false) Integer role,
			@RequestParam(required=false) String district) {
		//鉴权
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("未查询到此token信息");
			return JSON.toJSONString(urb);
		}else {
			User user = userService.findOneUserWithToken(token);
			if(user.getRole() != 1) {
				//不是管理员
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("非法用户");
				return JSON.toJSONString(urb);
			}else {
				//是管理员
				//找到用户并对用户值进行设置
				User user2 = userService.getOneUserById(userid);
				if(neckname != null && !neckname.equals("")) user2.setNeckname(neckname);
				if(pic != null && !pic.equals("")) user2.setPic(pic);
				if(password != null && !password.equals("")) user2.setPassword(password);
				if(usertoken != null && !usertoken.equals("")) user2.setToken(usertoken);
				if(phone != null && !phone.equals("")) user2.setPhone(phone);
				if(addr != null && !addr.equals("")) user2.setAddr(addr);
				if(role != null) user2.setRole(role);
				if(district != null && !district.equals("") && (district.equals("YaAn") || district.equals("ChengDu") || district.equals("DuJiangyan"))) user2.setDistrict(district);
				if(userService.updateOneUser(user2) > 0) {
					//更新成功
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.success("更新成功");
					return JSON.toJSONString(urb);
				}else {
					//更新失败
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.fail("更新失败");
					return JSON.toJSONString(urb);
				}
			}
		}
	}
	
    /**
     * 管理员删除某一本书
     * @param token  管理员密钥
     * @param bookid  书本id
     * @return 是否删除成功
     */
	@ResponseBody
	@RequestMapping(value="/book/delete/{bookid}",method=RequestMethod.POST)
	public String deleteBook(@RequestParam String token, @PathVariable Integer bookid) {
		//鉴权
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("未查询到此token信息");
			return JSON.toJSONString(urb);
		}else {
			User user = userService.findOneUserWithToken(token);
			if(user.getRole() != 1) {
				//不是管理员
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("非法用户");
				return JSON.toJSONString(urb);
			}else {
				//是管理员
				if(bookService.deleteOneBook(bookid) > 0) {
					//删除成功
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.success("删除书本成功");
					return JSON.toJSONString(urb);
				}else {
					//删除失败
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.fail("删除书本失败");
					return JSON.toJSONString(urb);
				}
			}
		}
	}
	
	/**
	 * 管理员更新书本信息
	 * @param token  管理员token 	
	 * @param bookid   书本id
	 * @param name   书本名字
	 * @param price   价格
	 * @param img1   图片1
	 * @param img2  图片2
	 * @param img3  图片3
	 * @param degree  折旧度
	 * @param district  校区
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/book/update",method=RequestMethod.POST)
	public String updateUser(@RequestParam String token,
			@RequestParam Integer bookid,
			@RequestParam(required=false) String name,
			@RequestParam(required=false) Double price,
			@RequestParam(required=false) String img1,
			@RequestParam(required=false) String img2,
			@RequestParam(required=false) String img3,
			@RequestParam(required=false) String degree,
			@RequestParam(required=false) String district) {
		//鉴权
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("未查询到此token信息");
			return JSON.toJSONString(urb);
		}else {
			User user = userService.findOneUserWithToken(token);
			if(user.getRole() != 1) {
				//不是管理员
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("非法用户");
				return JSON.toJSONString(urb);
			}else {
				//是管理员
				//找到用户并对用户值进行设置
				Book book = bookService.getOneBookByid(bookid);
				
				if(name != null && !name.equals("")) book.setName(name);
				if(price != null) book.setPrice(price);
				if(img1 != null && !img1.equals("")) book.setImg1(img1);
				if(img2 != null && !img2.equals("")) book.setImg2(img2);
				if(img3 != null && !img3.equals("")) book.setImg3(img3);
				if(degree != null && !degree.equals("")) book.setDegree(degree);
				if(district != null && !district.equals("") && (district.equals("YaAn") || district.equals("ChengDu") || district.equals("DuJiangyan"))) book.setDistrict(district);
				if(bookService.updateOneBook(book) > 0) {
					//更新成功
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.success("更新图书成功");
					return JSON.toJSONString(urb);
				}else {
					//更新失败
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.fail("更新图书失败");
					return JSON.toJSONString(urb);
				}
			}
		}
	}
}
