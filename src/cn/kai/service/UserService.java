
package cn.kai.service;

import java.util.List;

import cn.kai.model.Favor;
import cn.kai.model.User;

public interface UserService {
	
	/**
	 * �û���¼�ķ�������
	 * @param mail ����
	 * @param password  ����
	 * @return
	 */
	User login(String mail,String password);
	
	/**
	 * �û�ע��
	 * @param user
	 * @return  User����
	 */
	User regist(User user);
	
	/**
	 * ����һ���û�
	 * @param token  ����
	 * @return ����һ���û�����Ϣ
	 */
	User findOneUserWithToken(String token);
	
	User findOneUserWithMail(String mail);
	
	/**
	 * �����û�����Ϣ
	 * @param user
	 * @return 1-�ɹ�  0-ʧ��
	 */
	Integer updateOneUser(User user);
	
	/**
	 *  ��ȡ�ղص���Ϣ
	 * @param userid
	 * @return �ղصļ�¼
	 */
	List<Favor> getUsersFavor(Integer userid);
	
	/**
	 * ɾ��һ���ղ�
	 * @param favorid  �ղ�id
	 * @return 1-ɾ������   0-ɾ��ʧ��
	 */
	Integer deleteFavor(Integer favorid);
	
	/**
	 * ��������û�
	 * @return
	 */
	List<User> selectAllUser();
	
	/**
	 * ɾ��һ���û�
	 * @param userid
	 * @return
	 */
	Integer deleteOneUser(Integer userid);
	
	/**
	 * ��ѯһ���û�
	 * @return
	 */
	User getOneUserById(Integer userid);
}
