
package cn.kai.service;

import java.util.List;

import cn.kai.model.Favor;
import cn.kai.model.User;

public interface UserService {
	
	/**
	 * 用户登录的方法定义
	 * @param mail 邮箱
	 * @param password  密码
	 * @return
	 */
	User login(String mail,String password);
	
	/**
	 * 用户注册
	 * @param user
	 * @return  User对象
	 */
	User regist(User user);
	
	/**
	 * 查找一个用户
	 * @param token  令牌
	 * @return 返回一个用户的信息
	 */
	User findOneUserWithToken(String token);
	
	User findOneUserWithMail(String mail);
	
	/**
	 * 更新用户的信息
	 * @param user
	 * @return 1-成功  0-失败
	 */
	Integer updateOneUser(User user);
	
	/**
	 *  获取收藏的信息
	 * @param userid
	 * @return 收藏的记录
	 */
	List<Favor> getUsersFavor(Integer userid);
	
	/**
	 * 删除一个收藏
	 * @param favorid  收藏id
	 * @return 1-删除公共   0-删除失败
	 */
	Integer deleteFavor(Integer favorid);
	
	/**
	 * 获得所有用户
	 * @return
	 */
	List<User> selectAllUser();
	
	/**
	 * 删除一个用户
	 * @param userid
	 * @return
	 */
	Integer deleteOneUser(Integer userid);
	
	/**
	 * 查询一个用户
	 * @return
	 */
	User getOneUserById(Integer userid);
}
