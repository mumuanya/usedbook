
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
	 * get�û��ĸ�����Ϣ
	 * @param token �����û�������
	 * @return ���ظ�����Ϣ��json��Ϣ
	 */
	@RequestMapping(value="/info",method=RequestMethod.GET)
	@ResponseBody
	public String getUserInfo(@RequestParam String token) {
		//��Ȩ
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("δ��ѯ����token��Ϣ");
			return JSON.toJSONString(urb);
		}else {
			//�û������ݿ��д���
			User user = userService.findOneUserWithToken(token);
			//��װ
			UserReturnBean<User> urb = new UserReturnBean<>();
			urb.withData(user);
			return JSON.toJSONString(urb);
		}
	}
	
	/**
	 * �����û�������Ϣ
	 * @param token  �û���Կ
	 * @param neckname
	 * @param pic
	 * @param phone
	 * @param addr
	 * @param district
	 * @return   �Ƿ�ɹ�
	 */
	@ResponseBody
	@RequestMapping(value="/info",method=RequestMethod.POST)
	public String updateUserInfo(@RequestParam(required=true) String token,
			@RequestParam(required=false) String neckname,
			@RequestParam(required=false) String pic,
			@RequestParam(required=false) String phone,
			@RequestParam(required=false) String addr,
			@RequestParam(required=false) String district) {
		//��Ȩ
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("δ��ѯ����token��Ϣ");
			return JSON.toJSONString(urb);
		}else {
			User user = userService.findOneUserWithToken(token);
			if(neckname != null && !neckname.equals("")) user.setNeckname(neckname);
			if(pic != null && !pic.equals("")) user.setPic(pic);
			if(phone != null && !phone.equals("")) user.setPhone(phone);
			if(addr != null && !addr.equals("")) user.setAddr(addr);
			if(district != null && !district.equals("")) user.setDistrict(district);	
			System.out.println("�����洢��user��" + user);
			if(userService.updateOneUser(user) > 0) {
				UserReturnBean<User> urb = new UserReturnBean<>();
				urb.withData(user);
				return JSON.toJSONString(urb);
			}else {
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("�����û���Ϣʧ��");
				return JSON.toJSONString(urb);
			}
		}
	}
	
	/**
	 * �鿴�û��ղص��鱾��Ϣ
	 * @param token  ��Կ
	 * @return   �ղص��鱾json����
	 */
	@ResponseBody
	@RequestMapping(value="/favor",method=RequestMethod.GET)
	public String favor(@RequestParam String token) {
		//��Ȩ
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("δ��ѯ����token��Ϣ");
			return JSON.toJSONString(urb);
		}else {
			Integer userid = userService.findOneUserWithToken(token).getId();
			List<Favor> favors = userService.getUsersFavor(userid);
			//����favors���ҵ���Ӧ�������Ϣ
			List<FavorBean> favorBeans = new ArrayList<>();
			FavorBean favorBean;
			for(Favor favor:favors) {
				favorBean = new FavorBean();
				//�ҵ���
				Book book = bookService.getOneBookByid(favor.getBookid());
				favorBean.setBookid(favor.getBookid());
				favorBean.setFavorid(favor.getId());
				favorBean.setImg1(book.getImg1());
				favorBean.setName(book.getName());
				favorBean.setPrice(book.getPrice());
				favorBeans.add(favorBean);
			}
			//���������Ϣ
			UserReturnBean<List<FavorBean>> urb = new UserReturnBean<>();
			urb.withData(favorBeans);
			return JSON.toJSONString(urb);
		}
	}
	
	/**
	 * ɾ���û��ղص��鱾
	 * @param token  ��Կ
	 * @param favorid
	 * @return   �Ƿ�ɾ���ɹ�
	 */
	@ResponseBody
	@RequestMapping(value="/favor/delete/{favorid}",method=RequestMethod.GET)
	public String deleteFavor(String token,@PathVariable Integer favorid) {
		//��Ȩ
		if(userService.findOneUserWithToken(token) == null) {
			UserReturnBean<String> urb = new UserReturnBean<>();
			urb.fail("δ��ѯ����token��Ϣ");
			return JSON.toJSONString(urb);
		}else {
			Favor favor = commonService.getFavorById(favorid);
			if(favor.getUserid().intValue() != userService.findOneUserWithToken(token).getId().intValue()) {
				UserReturnBean<String> urb = new UserReturnBean<>();
				urb.fail("���������鼮�޷�ɾ��");
				return JSON.toJSONString(urb);
			}
			if(userService.deleteFavor(favorid) > 0) {
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
		}
	}
	
	
}
