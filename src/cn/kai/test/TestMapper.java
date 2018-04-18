
package cn.kai.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.kai.dao.UserMapper;
import cn.kai.model.User;

/**
* @author HuangKai  

* @date 2018Äê4ÔÂ14ÈÕ  

* @version 1.0  

*/

public class TestMapper {
	
	ApplicationContext a = null;

	@Before
	public void m1() {
		a = new ClassPathXmlApplicationContext("classpath:root-context.xml");
	}
	
	@Test
	public void m() {
		UserMapper userMapper = (UserMapper) a.getBean("userMapper");
		User user = new User();
		user.setId(1);
		user.setMail("xxxxxxx");
		System.out.println(userMapper.updateByPrimaryKeySelective(user));
	}
}
