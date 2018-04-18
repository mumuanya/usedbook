
package cn.kai.service;

import java.util.List;

import cn.kai.model.Book;

public interface BookService {

	/**
	 * �����û�������ͼ����Ϣ�б�
	 * @param userid  �û���id
	 * @param limit  ��������
	 * @return  ͼ����Ϣ�б�
	 */
	List<Book> getUsersBook(Integer userid,Integer limit);
	
	List<Book> getAllBooks();
	/**
	 * ����һ������ͼ����Ϣ
	 * @param book
	 * @return �����Ƿ񷢲��ɹ� 1  0
	 */
	Integer publishOneBook(Book book);
	
	/**
	 * ɾ��һ����
	 * @param bookid �� ��ID
	 * @return �����Ƿ�ɾ���ɹ�
	 */
	Integer deleteOneBook(Integer bookid);
	
	/**
	 * �ղ�һ����
	 * @param userid  �û�id
	 * @param bookid  �����鱾id
	 * @return  �Ƿ��ղسɹ�
	 */
	Integer favorOneBook(Integer userid,Integer bookid);
	
	Book getOneBookByid(Integer bookid);
	
	Integer updateOneBook(Book book);
	
	
}
