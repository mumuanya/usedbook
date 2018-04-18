
package cn.kai.service;

import java.util.List;

import cn.kai.model.Book;
import cn.kai.model.Favor;

public interface CommonService {
	
	/**
	 * 返回所有的书本信息，或者一部分书本信息
	 * @param limit 限制条数
	 * @return  书本信息列表
	 */
	List<Book> getAllBook(Integer limit);
	
	Favor getFavorById(Integer favorid);
	
}
