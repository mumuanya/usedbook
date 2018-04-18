
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

* @date 2018��4��14��  

* @version 1.0  
* ����Ա����ʱ����������
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
	 * ����Ա��¼������
	 * @param mail  ����Ա�ĵ����ʼ�
	 * @param password ����Ա������
  	 * @return  ��¼�ɹ�-���سɹ���session ʧ��-���ص�¼ʧ��
	 */
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam String mail, @RequestParam String password) {
		
		//�����Ϸ�����֤
		if(mail.equals("") || password.equals("")) {
			//�����˿մ�
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("�Ƿ������Ĵ���");
			return JSON.toJSONString(urb);
		}
		User user = userService.login(mail, password);
		if(user != null && user.getRole() == 1) {
			//��¼�ɹ�
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.success(user.getToken());
			return JSON.toJSONString(urb);
		}else {
			//��¼ʧ��
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("��¼ʧ���ˣ�");
			return JSON.toJSONString(urb);
		}
	}
	
	/**
	 * ����Ա��ѯ���е��û���Ϣ
	 * @param token  ����Ա������
	 * @return  ���е��û���Ϣ json��ʽ
	 */
	@ResponseBody
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public String allUser(@RequestParam String token) {
		//��Ȩ
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("δ��ѯ����token��Ϣ");
			return JSON.toJSONString(urb);
		}else {
			User user = userService.findOneUserWithToken(token);
			if(user.getRole() != 1) {
				//���ǹ���Ա
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("�Ƿ��û�");
				return JSON.toJSONString(urb);
			}else {
				//�ǹ���Ա
				List<User> users = userService.selectAllUser();
				return JSON.toJSONString(users);
			}
		}
	}
	
	/**
	 * ����Ա��ѯ���е��鱾��Ϣ
	 * @param token ����Աtoken
	 * @return   �������鱾��Ϣ
	 */
	@ResponseBody
	@RequestMapping(value="/books",method=RequestMethod.GET)
	public String allBooks(@RequestParam String token) {
		//��Ȩ
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("δ��ѯ����token��Ϣ");
			return JSON.toJSONString(urb);
		}else {
			User user = userService.findOneUserWithToken(token);
			if(user.getRole() != 1) {
				//���ǹ���Ա
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("�Ƿ��û�");
				return JSON.toJSONString(urb);
			}else {
				//�ǹ���Ա
				List<Book> books = bookService.getAllBooks();
				return JSON.toJSONString(books);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/user/delete/{userid}",method=RequestMethod.POST)
	public String deleteUser(@RequestParam String token, @PathVariable Integer userid) {
		//��Ȩ
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("δ��ѯ����token��Ϣ");
			return JSON.toJSONString(urb);
		}else {
			User user = userService.findOneUserWithToken(token);
			if(user.getRole() != 1) {
				//���ǹ���Ա
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("�Ƿ��û�");
				return JSON.toJSONString(urb);
			}else {
				//�ǹ���Ա
				if(userService.deleteOneUser(userid) > 0) {
					//ɾ���ɹ�
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.success("ɾ���û��ɹ�");
					return JSON.toJSONString(urb);
				}else {
					//ɾ��ʧ��
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.fail("ɾ���û�ʧ��");
					return JSON.toJSONString(urb);
				}
			}
		}
	}
	
	/**
	 * ����Ա�����û�����Ϣ
	 * @param token  ����Աtoken��������֤����Ա���
	 * @param userid  �û���id
	 * @param neckname �ǳ�
	 * @param pic ͼƬ
	 * @param password  �û�������
	 * @param usertoken  �û�������
	 * @param phone   �û��绰
	 * @param addr  �û���ַ
	 * @param role   �û���ɫ
	 * @param district   �û�У��
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
		//��Ȩ
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("δ��ѯ����token��Ϣ");
			return JSON.toJSONString(urb);
		}else {
			User user = userService.findOneUserWithToken(token);
			if(user.getRole() != 1) {
				//���ǹ���Ա
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("�Ƿ��û�");
				return JSON.toJSONString(urb);
			}else {
				//�ǹ���Ա
				//�ҵ��û������û�ֵ��������
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
					//���³ɹ�
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.success("���³ɹ�");
					return JSON.toJSONString(urb);
				}else {
					//����ʧ��
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.fail("����ʧ��");
					return JSON.toJSONString(urb);
				}
			}
		}
	}
	
    /**
     * ����Աɾ��ĳһ����
     * @param token  ����Ա��Կ
     * @param bookid  �鱾id
     * @return �Ƿ�ɾ���ɹ�
     */
	@ResponseBody
	@RequestMapping(value="/book/delete/{bookid}",method=RequestMethod.POST)
	public String deleteBook(@RequestParam String token, @PathVariable Integer bookid) {
		//��Ȩ
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("δ��ѯ����token��Ϣ");
			return JSON.toJSONString(urb);
		}else {
			User user = userService.findOneUserWithToken(token);
			if(user.getRole() != 1) {
				//���ǹ���Ա
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("�Ƿ��û�");
				return JSON.toJSONString(urb);
			}else {
				//�ǹ���Ա
				if(bookService.deleteOneBook(bookid) > 0) {
					//ɾ���ɹ�
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.success("ɾ���鱾�ɹ�");
					return JSON.toJSONString(urb);
				}else {
					//ɾ��ʧ��
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.fail("ɾ���鱾ʧ��");
					return JSON.toJSONString(urb);
				}
			}
		}
	}
	
	/**
	 * ����Ա�����鱾��Ϣ
	 * @param token  ����Աtoken 	
	 * @param bookid   �鱾id
	 * @param name   �鱾����
	 * @param price   �۸�
	 * @param img1   ͼƬ1
	 * @param img2  ͼƬ2
	 * @param img3  ͼƬ3
	 * @param degree  �۾ɶ�
	 * @param district  У��
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
		//��Ȩ
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("δ��ѯ����token��Ϣ");
			return JSON.toJSONString(urb);
		}else {
			User user = userService.findOneUserWithToken(token);
			if(user.getRole() != 1) {
				//���ǹ���Ա
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("�Ƿ��û�");
				return JSON.toJSONString(urb);
			}else {
				//�ǹ���Ա
				//�ҵ��û������û�ֵ��������
				Book book = bookService.getOneBookByid(bookid);
				
				if(name != null && !name.equals("")) book.setName(name);
				if(price != null) book.setPrice(price);
				if(img1 != null && !img1.equals("")) book.setImg1(img1);
				if(img2 != null && !img2.equals("")) book.setImg2(img2);
				if(img3 != null && !img3.equals("")) book.setImg3(img3);
				if(degree != null && !degree.equals("")) book.setDegree(degree);
				if(district != null && !district.equals("") && (district.equals("YaAn") || district.equals("ChengDu") || district.equals("DuJiangyan"))) book.setDistrict(district);
				if(bookService.updateOneBook(book) > 0) {
					//���³ɹ�
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.success("����ͼ��ɹ�");
					return JSON.toJSONString(urb);
				}else {
					//����ʧ��
					UserReturnBean<String> urb = new UserReturnBean<>();
					urb.fail("����ͼ��ʧ��");
					return JSON.toJSONString(urb);
				}
			}
		}
	}
}
