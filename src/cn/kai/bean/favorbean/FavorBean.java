
package cn.kai.bean.favorbean;
/**
* @author HuangKai  

* @date 2018��4��14��  

* @version 1.0  
* <p>��favorid���鱾�ľ�����Ϣ������һ��Ȼ����
* <p>�����û������ɲ鿴���ղص������ϸ��Ϣ
*/
public class FavorBean {
	
	private Integer favorid;
	
	private Integer bookid;
	
	private String name;
	
	private String img1; 
	
	private Double price;

	public Integer getFavorid() {
		return favorid;
	}

	public void setFavorid(Integer favorid) {
		this.favorid = favorid;
	}

	public Integer getBookid() {
		return bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
