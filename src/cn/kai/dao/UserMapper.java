package cn.kai.dao;

import java.util.List;

import cn.kai.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);
    
    User selectByMail(String mail);
    
    User selectByToken(String token);
    
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	List<User> selectAll();
    
    
}