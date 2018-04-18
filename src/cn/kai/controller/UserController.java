
package cn.kai.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.kai.bean.favorbean.FavorBean;
import cn.kai.bean.userbean.UserReturnBean;
import cn.kai.model.Book;
import cn.kai.model.Favor;
import cn.kai.model.User;
import cn.kai.service.BookService;
import cn.kai.service.CommonService;
import cn.kai.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	CommonService commonService;
	
	/**
	 * get用户的个人信息
	 * @param token 传入用户的令牌
	 * @return 返回个人信息的json信息
	 */
	@RequestMapping(value="/info",method=RequestMethod.GET)
	@ResponseBody
	public String getUserInfo(@RequestParam String token) {
		//鉴权
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("未查询到此token信息");
			return JSON.toJSONString(urb);
		}else {
			//用户在数据库中存在
			User user = userService.findOneUserWithToken(token);
			//封装
			UserReturnBean<User> urb = new UserReturnBean<>();
			urb.withData(user);
			return JSON.toJSONString(urb);
		}
	}
	
	/**
	 * 更新用户基本信息
	 * @param token  用户秘钥
	 * @param neckname
	 * @param pic
	 * @param phone
	 * @param addr
	 * @param district
	 * @return   是否成功
	 */
	@ResponseBody
	@RequestMapping(value="/info",method=RequestMethod.POST)
	public String updateUserInfo(@RequestParam(required=true) String token,
			@RequestParam(required=false) String neckname,
			@RequestParam(required=false) String pic,
			@RequestParam(required=false) String phone,
			@RequestParam(required=false) String addr,
			@RequestParam(required=false) String district) {
		//鉴权
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("未查询到此token信息");
			return JSON.toJSONString(urb);
		}else {
			User user = userService.findOneUserWithToken(token);
			if(neckname != null && !neckname.equals("")) user.setNeckname(neckname);
			if(pic != null && !pic.equals("")) user.setPic(pic);
			if(phone != null && !phone.equals("")) user.setPhone(phone);
			if(addr != null && !addr.equals("")) user.setAddr(addr);
			if(district != null && !district.equals("")) user.setDistrict(district);	
			System.out.println("即将存储的user：" + user);
			if(userService.updateOneUser(user) > 0) {
				UserReturnBean<User> urb = new UserReturnBean<>();
				urb.withData(user);
				return JSON.toJSONString(urb);
			}else {
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("更新用户信息失败");
				return JSON.toJSONString(urb);
			}
		}
	}
	
	/**
	 * 查看用户收藏的书本信息
	 * @param token  秘钥
	 * @return   收藏的书本json数组
	 */
	@ResponseBody
	@RequestMapping(value="/favor",method=RequestMethod.GET)
	public String favor(@RequestParam String token) {
		//鉴权
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("未查询到此token信息");
			return JSON.toJSONString(urb);
		}else {
			Integer userid = userService.findOneUserWithToken(token).getId();
			List<Favor> favors = userService.getUsersFavor(userid);
			//遍历favors，找到对应的书的信息
			List<FavorBean> favorBeans = new ArrayList<>();
			FavorBean favorBean;
			for(Favor favor:favors) {
				favorBean = new FavorBean();
				//找到书
				Book book = bookService.getOneBookByid(favor.getBookid());
				favorBean.setBookid(favor.getBookid());
				favorBean.setFavorid(favor.getId());
				favorBean.setImg1(book.getImg1());
				favorBean.setName(book.getName());
				favorBean.setPrice(book.getPrice());
				favorBeans.add(favorBean);
			}
			//返回书的信息
			UserReturnBean<List<FavorBean>> urb = new UserReturnBean<>();
			urb.withData(favorBeans);
			return JSON.toJSONString(urb);
		}
	}
	
	/**
	 * 删除用户收藏的书本
	 * @param token  秘钥
	 * @param favorid
	 * @return   是否删除成功
	 */
	@ResponseBody
	@RequestMapping(value="/favor/delete/{favorid}",method=RequestMethod.GET)
	public String deleteFavor(String token,@PathVariable Integer favorid) {
		//鉴权
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("未查询到此token信息");
			return JSON.toJSONString(urb);
		}else {
			Favor favor = commonService.getFavorById(favorid);
			if(favor.getUserid().intValue() != userService.findOneUserWithToken(token).getId().intValue()) {
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("不是您的书籍无法删除");
				return JSON.toJSONString(urb);
			}
			if(userService.deleteFavor(favorid) > 0) {
				//删除成功
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.success("删除成功");
				return JSON.toJSONString(urb);
			}else {
				//删除失败
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("删除失败");
				return JSON.toJSONString(urb);
			}
		}
	}
	
	
}
