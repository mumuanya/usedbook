
package cn.kai.bean.favorbean;
/**
* @author HuangKai  

* @date 2018年4月14日  

* @version 1.0  
* <p>把favorid和书本的具体信息整合在一起，然后输
* <p>出给用户，即可查看到收藏的书的详细信息
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
