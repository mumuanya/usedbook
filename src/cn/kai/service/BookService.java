
package cn.kai.service;

import java.util.List;

import cn.kai.model.Book;

public interface BookService {

	/**
	 * 返回用户发布的图书信息列表
	 * @param userid  用户的id
	 * @param limit  限制条数
	 * @return  图书信息列表
	 */
	List<Book> getUsersBook(Integer userid,Integer limit);
	
	List<Book> getAllBooks();
	/**
	 * 发布一本二手图书信息
	 * @param book
	 * @return 返回是否发布成功 1  0
	 */
	Integer publishOneBook(Book book);
	
	/**
	 * 删除一本书
	 * @param bookid 书 的ID
	 * @return 返回是否删除成功
	 */
	Integer deleteOneBook(Integer bookid);
	
	/**
	 * 收藏一本书
	 * @param userid  用户id
	 * @param bookid  二手书本id
	 * @return  是否收藏成功
	 */
	Integer favorOneBook(Integer userid,Integer bookid);
	
	Book getOneBookByid(Integer bookid);
	
	Integer updateOneBook(Book book);
	
	
}
