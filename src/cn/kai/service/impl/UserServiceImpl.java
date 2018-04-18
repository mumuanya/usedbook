
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

* @date 2018年4月14日  

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
			System.out.println("[ERROR]:查询用户失败");
		}
		//如果顺利登陆了则执行if中的代码
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
			System.out.println("[ERROR]:通过密钥查询用户失败！");
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
			System.out.println("[ERROR]: 更新用户信息时失败");
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
			System.out.println("[ERROR]: 查询用户收藏时失败");
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
			System.out.println("[ERROR]: 删除收藏失败");
		}
		return rs;
	}

	@Override
	public User regist(User user) {
		Integer rs = null;
		try {
			rs = userMapper.insertSelective(user);
			if(rs > 0) {//注册成功
				user.setId((userMapper.selectByMail(user.getMail())).getId());;
			}else {//注册失败
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR]:注册失败");
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
			System.out.println("查找该用户时出现错误" + mail);
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
			System.out.println("查询所有用户失败");
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
			System.out.println("删除用户发生异常！");
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
			System.out.println("查询用户失败" + userid);
		}
		return user;
	}

}
