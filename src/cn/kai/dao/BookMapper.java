package cn.kai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.kai.model.Book;

public interface BookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);
    
    List<Book> selectByUserid(@Param("userid")Integer userid,@Param("limit")Integer limit);
    
    List<Book> selectSome(Integer limit);
}