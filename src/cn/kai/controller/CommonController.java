
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

* @date 2018��4��14��  

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
	 * �κ��˶��������󣬷���һ���������������
	 * @param limit   ��ı���
	 * @return   һ�����json��ʽ
	 */
	@ResponseBody
	@RequestMapping(value="/home/books",method=RequestMethod.GET)
	public String getBooks(@RequestParam(required=false) Integer limit) {
		//��limit������
		if(limit == null || limit < 0)
			limit = 999;
		
		List<Book> books = commonService.getAllBook(limit);
		
		UserReturnBean<List<Book>> urb = new UserReturnBean<>();
		urb.withData(books);
		return JSON.toJSONString(urb);
	}
	
	/**
	 * ��¼
	 * @param mail   ����
	 * @param password  ����
	 * @return   �ɹ�-token  ʧ�� - ������Ϣ
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
		if(user != null) {
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
	 *   ע��
	 * @param neckname
	 * @param mail
	 * @param pic
	 * @param password
	 * @param phone
	 * @param addr
	 * @param district
	 * @return ע���Ƿ�ɹ�  �ɹ�-����token   ʧ��-ʧ��
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
		//�����Ϸ�����֤
		if(mail.equals("") || password.equals("") || district.equals("")) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("���봫���������");
			return JSON.toJSONString(urb);
		}
		//�ж��Ƿ�mail�ظ�
		if(userService.findOneUserWithMail(mail) != null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("mail�Ѿ�����ע���ˣ�");
			return JSON.toJSONString(urb);
		}
		//���district�Ƿ�Ϸ�
		if(!district.equals("YaAn") && !district.equals("ChengDu") && !district.equals("DuJiangyan")) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("��ַ���Ϸ�");
			return JSON.toJSONString(urb);
		}
		//��ʼ����ע����Ϣ
		User user = new User();
		if(neckname != null && !neckname.equals("")) user.setNeckname(neckname);
		user.setMail(mail);
		if(pic != null && !pic.equals("")) user.setPic(pic);
		user.setPassword(password);
		if(phone != null && !phone.equals("")) user.setPhone(phone);
		user.setDistrict(district);
		if(addr != null && !addr.equals("")) user.setAddr(addr);
		//����token��ע��  ʹ��ʱ�����MD5������ΪΨһ��token
		String token = MD5Encoder.md5Password(Long.toString(new Date().getTime()));
		user.setToken(token);
		User user2 = userService.regist(user);
		if(user2 != null) {
			//ע��ɹ�
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.success(user2.getToken());
			return JSON.toJSONString(urb);
		}else {
			//ע��ʧ��
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("ע��ʧ���ˣ�");
			return JSON.toJSONString(urb);
		}
	}
}
