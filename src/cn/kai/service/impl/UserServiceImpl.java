
package cn.kai.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.kai.dao.FavorMapper;
import cn.kai.dao.UserMapper;
import cn.kai.model.Favor;
import cn.kai.model.User;
import cn.kai.service.UserService;

/**
* @author HuangKai  

* @date 2018��4��14��  

* @version 1.0  

*/
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	FavorMapper favorMapper;
	
	@Override
	public User login(String mail, String password) {
		User user = null;
		try {
			user = userMapper.selectByMail(mail);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR]:��ѯ�û�ʧ��");
		}
		//���˳����½����ִ��if�еĴ���
		if(user != null) {
			if(user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public User findOneUserWithToken(String token) {
		User user = null;
		try {
			user = userMapper.selectByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR]:ͨ����Կ��ѯ�û�ʧ�ܣ�");
		}
		return user;
	}

	@Override
	public Integer updateOneUser(User user) {
		Integer rs = null;
		try {
			rs = userMapper.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR]: �����û���Ϣʱʧ��");
		}
		return rs;
	}

	@Override
	public List<Favor> getUsersFavor(Integer userid) {
		List<Favor> favors = null;
		
		try {
			favors = favorMapper.selectByUserId(userid);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR]: ��ѯ�û��ղ�ʱʧ��");
		}
		return favors;
	}

	@Override
	public Integer deleteFavor(Integer favorid) {
		Integer rs = null;
		try {
			rs = favorMapper.deleteByPrimaryKey(favorid);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR]: ɾ���ղ�ʧ��");
		}
		return rs;
	}

	@Override
	public User regist(User user) {
		Integer rs = null;
		try {
			rs = userMapper.insertSelective(user);
			if(rs > 0) {//ע��ɹ�
				user.setId((userMapper.selectByMail(user.getMail())).getId());;
			}else {//ע��ʧ��
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR]:ע��ʧ��");
		}
		return user;
	}

	@Override
	public User findOneUserWithMail(String mail) {
		User user = null;
		try {
			user = userMapper.selectByMail(mail);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���Ҹ��û�ʱ���ִ���" + mail);
		}
		return user;
	}

	@Override
	public List<User> selectAllUser() {
		List<User> users = null;
		try {
			users = userMapper.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��ѯ�����û�ʧ��");
		}
		return users;
	}

	@Override
	public Integer deleteOneUser(Integer userid) {
		Integer rs = null;
		try {
			rs = userMapper.deleteByPrimaryKey(userid);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ɾ���û������쳣��");
		}
		return rs;
	}

	@Override
	public User getOneUserById(Integer userid) {
		User user = null;
		try {
			user = userMapper.selectByPrimaryKey(userid);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��ѯ�û�ʧ��" + userid);
		}
		return user;
	}

}
