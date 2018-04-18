
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

* @date 2018��4��14��  

* @version 1.0  

*/
@Service("bookService")
public class BookServiceImpl implements BookService {

	//bookMapper,��������ݿ��ȡbook��Ϣ
	@Autowired
	BookMapper bookMapper;
	
	@Autowired
	FavorMapper favorMapper;
	
	@Override
	public List<Book> getUsersBook(Integer userid, Integer limit) {
		//�������������쳣
		if(limit < 0 || limit == null) {//������Ʋ���С��0����Ϊ��
			limit = 999;
		}
		
		List<Book> books = null;
		try {
			books = bookMapper.selectByUserid(userid, limit);
		} catch (Exception e) {
			System.out.println("[LOG]:" + "��ѯ�û���������ʱ����");
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
			System.out.println("[ERROR]:����һ��������쳣");
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
			System.out.println("[ERROR]:ɾ���鱾��Ϣʧ�ܣ�");
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
			System.out.println("[ERROR]:�ղ��鱾ʧ�ܣ�");
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
			System.out.println("�����鱾����");
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
			System.out.println("��ѯ�鼮ʱ����");
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
			System.out.println("����ͼ����Ϣʧ��");
		}
		return rs;
	}
}
