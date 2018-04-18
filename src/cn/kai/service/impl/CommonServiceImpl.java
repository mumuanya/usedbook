
package cn.kai.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.kai.dao.BookMapper;
import cn.kai.dao.FavorMapper;
import cn.kai.model.Book;
import cn.kai.model.Favor;
import cn.kai.service.CommonService;

/**
* @author HuangKai  

* @date 2018��4��14��  

* @version 1.0  

*/
@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Autowired
	BookMapper bookMapper;
	
	@Autowired
	FavorMapper favorMapper;
	
	@Override
	public List<Book> getAllBook(Integer limit) {
		List<Book> books = null;
		//���ж�limit��ֵ
		if(limit < 0 || limit == null) {
			limit = 999;
		}
		try {
			books = bookMapper.selectSome(limit);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR]: ��ѯʧ��");
		}
		return books;
	}

	@Override
	public Favor getFavorById(Integer favorid) {
		Favor favor = null;
		
		try {
			favor = favorMapper.selectByPrimaryKey(favorid);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("û���ҵ�favor");
		}
		return favor;
	}
}
