package cn.kai.dao;

import java.util.List;

import cn.kai.model.Favor;

public interface FavorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Favor record);

    int insertSelective(Favor record);

    Favor selectByPrimaryKey(Integer id);

    List<Favor> selectByUserId(Integer userid);
    
    int updateByPrimaryKeySelective(Favor record);

    int updateByPrimaryKey(Favor record);
}