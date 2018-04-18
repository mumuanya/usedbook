
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
import cn.kai.model.Favor;
import cn.kai.model.User;
import cn.kai.service.BookService;
import cn.kai.service.CommonService;
import cn.kai.service.UserService;

/**
* @author HuangKai  

* @date 2018年4月14日  

* @version 1.0  

*/
@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	UserService userService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	CommonService commonService;
	
	/**
	 * 获取所有书本，或者一部分书本,通过参数控制多少
	 * @param token   令牌，管理员普通用户皆可
	 * @param limit    限制条数
	 * @return   一堆书本信息
	 */
	@ResponseBody
	@RequestMapping(value="/books",method=RequestMethod.GET)
	public String getBooks(String token, Integer limit) {
		//鉴权
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("未查询到此token信息");
			return JSON.toJSONString(urb);
		}else {
			//处理limit
			if(limit == null || limit < 0) limit = 999;
			//开始查询书本信息
			Integer userid = userService.findOneUserWithToken(token).getId();
			List<Book> books = bookService.getUsersBook(userid, limit);
			UserReturnBean<List<Book>> urb = new UserReturnBean<>();
			urb.withData(books);
			return JSON.toJSONString(urb);
		}
	}
	
	/**
	 * 发布一本书
	 * @param token  令牌
	 * @param name  书名
	 * @param degree  折旧度
	 * @param price  价格
	 * @param district   校区
	 * @param img1   图片1
	 * @param img2 图片2
	 * @param img3  图片3
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/publish",method=RequestMethod.POST)
	public String publish(@RequestParam String token,
			@RequestParam String name,
			@RequestParam(required=false) String degree,
			@RequestParam Double price,
			@RequestParam String district,
			@RequestParam(required=false) String img1,
			@RequestParam(required=false) String img2,
			@RequestParam(required=false) String img3) {
		//参数鉴定
		if(name == null || name.equals("") || price == null || district == null || district.equals("")) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("必须传入的参数有误！");
			return JSON.toJSONString(urb);
		}
		//鉴权
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("未查询到此token信息");
			return JSON.toJSONString(urb);
		}else {
			//传入book数据
			User user = userService.findOneUserWithToken(token);
			Book book = new Book();
			book.setUserid(user.getId());
			if(degree != null && !degree.equals("")) book.setDegree(degree);
			book.setName(name);
			book.setPrice(price);
			book.setDistrict(district);
			if(img1 != null && !img1.equals("")) book.setImg1(img1);
			if(img2 != null && !img2.equals("")) book.setImg2(img2);
			if(img3 != null && !img3.equals("")) book.setImg3(img3);
			if(bookService.publishOneBook(book) > 0) {
				//发布成功
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.success("发布成功");;
				return JSON.toJSONString(urb);
			}else {
				//发布失败
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("发布失败");
				return JSON.toJSONString(urb);
			}
		}
	}
	
	/**
	 * 删除一本书
	 * @param token  用户的令牌
	 * @param bookid  用户发布的书的令牌
	 * @return   成功否
	 */
	@ResponseBody
	@RequestMapping(value="/delete/{bookid}",method=RequestMethod.POST)
	public String deleteBook(@RequestParam String token,
			@PathVariable Integer bookid) {
		//鉴权
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("未查询到此token信息");
			return JSON.toJSONString(urb);
		}else {
			//判断书是否属于用户
			User user = userService.findOneUserWithToken(token);
			Book book = bookService.getOneBookByid(bookid);
			if(book != null && book.getUserid().intValue() == user.getId().intValue()) {
				//确认book存在且属于用户
				if(bookService.deleteOneBook(bookid) > 0) {
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
				
			}else if(book != null && book.getUserid().intValue() != user.getId().intValue()) {
				//书存在但是不是用户的
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("不是用户发布的书本无法删除");
				return JSON.toJSONString(urb);
			}else {
				//书根本就不存在
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("未查询到图书的信息");
				return JSON.toJSONString(urb);
			}
		}
	}
	
	/**
	 * 用户收藏一本书
	 * @param token  用户令牌
	 * @param bookid    书本id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/favor/{bookid}",method=RequestMethod.POST)
	public String favor(@RequestParam String token,
			@PathVariable Integer bookid) {
		//鉴权
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("未查询到此token信息");
			return JSON.toJSONString(urb);
		}else {
			//开始收藏
			User user = userService.findOneUserWithToken(token);
			Integer userid = user.getId();
			//判断是否已经收藏过了
			List<Favor> favors = userService.getUsersFavor(userid);
			for(Favor favor:favors) {
				if(favor.getBookid().intValue() == bookid.intValue()) {
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.repeat("重复操作");
					return JSON.toJSONString(urb);
				}
			}
			if(bookService.favorOneBook(userid, bookid) > 0) {
				//收藏成功
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.success("收藏成功");
				return JSON.toJSONString(urb);
			}else {
				//收藏失败
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("收藏失败");
				return JSON.toJSONString(urb);
			}
		}
	}
	
	
}
