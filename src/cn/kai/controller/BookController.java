
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

* @date 2018��4��14��  

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
	 * ��ȡ�����鱾������һ�����鱾,ͨ���������ƶ���
	 * @param token   ���ƣ�����Ա��ͨ�û��Կ�
	 * @param limit    ��������
	 * @return   һ���鱾��Ϣ
	 */
	@ResponseBody
	@RequestMapping(value="/books",method=RequestMethod.GET)
	public String getBooks(String token, Integer limit) {
		//��Ȩ
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("δ��ѯ����token��Ϣ");
			return JSON.toJSONString(urb);
		}else {
			//����limit
			if(limit == null || limit < 0) limit = 999;
			//��ʼ��ѯ�鱾��Ϣ
			Integer userid = userService.findOneUserWithToken(token).getId();
			List<Book> books = bookService.getUsersBook(userid, limit);
			UserReturnBean<List<Book>> urb = new UserReturnBean<>();
			urb.withData(books);
			return JSON.toJSONString(urb);
		}
	}
	
	/**
	 * ����һ����
	 * @param token  ����
	 * @param name  ����
	 * @param degree  �۾ɶ�
	 * @param price  �۸�
	 * @param district   У��
	 * @param img1   ͼƬ1
	 * @param img2 ͼƬ2
	 * @param img3  ͼƬ3
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
		//��������
		if(name == null || name.equals("") || price == null || district == null || district.equals("")) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("���봫��Ĳ�������");
			return JSON.toJSONString(urb);
		}
		//��Ȩ
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("δ��ѯ����token��Ϣ");
			return JSON.toJSONString(urb);
		}else {
			//����book����
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
				//�����ɹ�
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.success("�����ɹ�");;
				return JSON.toJSONString(urb);
			}else {
				//����ʧ��
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("����ʧ��");
				return JSON.toJSONString(urb);
			}
		}
	}
	
	/**
	 * ɾ��һ����
	 * @param token  �û�������
	 * @param bookid  �û��������������
	 * @return   �ɹ���
	 */
	@ResponseBody
	@RequestMapping(value="/delete/{bookid}",method=RequestMethod.POST)
	public String deleteBook(@RequestParam String token,
			@PathVariable Integer bookid) {
		//��Ȩ
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("δ��ѯ����token��Ϣ");
			return JSON.toJSONString(urb);
		}else {
			//�ж����Ƿ������û�
			User user = userService.findOneUserWithToken(token);
			Book book = bookService.getOneBookByid(bookid);
			if(book != null && book.getUserid().intValue() == user.getId().intValue()) {
				//ȷ��book�����������û�
				if(bookService.deleteOneBook(bookid) > 0) {
					//ɾ���ɹ�
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.success("ɾ���ɹ�");
					return JSON.toJSONString(urb);
				}else {
					//ɾ��ʧ��
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.fail("ɾ��ʧ��");
					return JSON.toJSONString(urb);
				}
				
			}else if(book != null && book.getUserid().intValue() != user.getId().intValue()) {
				//����ڵ��ǲ����û���
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("�����û��������鱾�޷�ɾ��");
				return JSON.toJSONString(urb);
			}else {
				//������Ͳ�����
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("δ��ѯ��ͼ�����Ϣ");
				return JSON.toJSONString(urb);
			}
		}
	}
	
	/**
	 * �û��ղ�һ����
	 * @param token  �û�����
	 * @param bookid    �鱾id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/favor/{bookid}",method=RequestMethod.POST)
	public String favor(@RequestParam String token,
			@PathVariable Integer bookid) {
		//��Ȩ
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("δ��ѯ����token��Ϣ");
			return JSON.toJSONString(urb);
		}else {
			//��ʼ�ղ�
			User user = userService.findOneUserWithToken(token);
			Integer userid = user.getId();
			//�ж��Ƿ��Ѿ��ղع���
			List<Favor> favors = userService.getUsersFavor(userid);
			for(Favor favor:favors) {
				if(favor.getBookid().intValue() == bookid.intValue()) {
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.repeat("�ظ�����");
					return JSON.toJSONString(urb);
				}
			}
			if(bookService.favorOneBook(userid, bookid) > 0) {
				//�ղسɹ�
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.success("�ղسɹ�");
				return JSON.toJSONString(urb);
			}else {
				//�ղ�ʧ��
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("�ղ�ʧ��");
				return JSON.toJSONString(urb);
			}
		}
	}
	
	
}
