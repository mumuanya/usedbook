
package cn.kai.service;

import java.util.List;

import cn.kai.model.Book;
import cn.kai.model.Favor;

public interface CommonService {
	
	/**
	 * �������е��鱾��Ϣ������һ�����鱾��Ϣ
	 * @param limit ��������
	 * @return  �鱾��Ϣ�б�
	 */
	List<Book> getAllBook(Integer limit);
	
	Favor getFavorById(Integer favorid);
	
}
