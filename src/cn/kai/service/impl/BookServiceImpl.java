
package cn.kai.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.kai.dao.BookMapper;
import cn.kai.dao.FavorMapper;
import cn.kai.model.Book;
import cn.kai.model.Favor;
import cn.kai.service.BookService;

/**
* @author HuangKai  

* @date 2018年4月14日  

* @version 1.0  

*/
@Service("bookService")
public class BookServiceImpl implements BookService {

	//bookMapper,负责从数据库获取book信息
	@Autowired
	BookMapper bookMapper;
	
	@Autowired
	FavorMapper favorMapper;
	
	@Override
	public List<Book> getUsersBook(Integer userid, Integer limit) {
		//检查参数，以免异常
		if(limit < 0 || limit == null) {//如果限制参数小于0或者为空
			limit = 999;
		}
		
		List<Book> books = null;
		try {
			books = bookMapper.selectByUserid(userid, limit);
		} catch (Exception e) {
			System.out.println("[LOG]:" + "查询用户发布的书时出错");
			e.printStackTrace();
		}
		return books;
	}

	@Override
	public Integer publishOneBook(Book book) {
		Integer rs = null;
		try {
			rs = bookMapper.insertSelective(book);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR]:插入一本书出现异常");
		}
		return rs;
	}

	@Override
	public Integer deleteOneBook(Integer bookid) {
		Integer rs = null;
		try {
			rs = bookMapper.deleteByPrimaryKey(bookid);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR]:删除书本信息失败！");
		}
		return rs;
	}

	@Override
	public Integer favorOneBook(Integer userid, Integer bookid) {
		Integer rs = null;
		Favor favor = new Favor();
		favor.setBookid(bookid);
		favor.setUserid(userid);
		try {
			rs = favorMapper.insert(favor);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR]:收藏书本失败！");
		}
		return rs;
	}

	@Override
	public Book getOneBookByid(Integer bookid) {
		Book book = null;
		try {
			book = bookMapper.selectByPrimaryKey(bookid);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("查找书本出错！");
		}
		return book;
	}

	@Override
	public List<Book> getAllBooks() {
		List<Book> books = null;
		
		try {
			books = bookMapper.selectSome(9999);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("查询书籍时出错！");
		}
		return books;
	}

	@Override
	public Integer updateOneBook(Book book) {
		Integer rs = null;
		
		try {
			rs = bookMapper.updateByPrimaryKeySelective(book);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("更新图书信息失败");
		}
		return rs;
	}
}
