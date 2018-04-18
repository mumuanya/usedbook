
package cn.kai.bean.userbean;
/**
* @author HuangKai  

* @date 2018年4月14日  

* @version 1.0  
* <p>返回数据到前台时的工具bean
* <p>例如：
*  {'code':0,'success':true,'data':'xxx'}
*/
public class UserReturnBean<T> {

	private Integer code;
	private boolean success;
	private T data;
	
	/**
	 * 返回成功信息，传入参数应该是String类型的提示信息.code为0,,success为true
	 * @param data 传入成功的信息
	 * @return none
	 */
	public void success(T data) {
		this.code = 0;
		this.success = true;
		this.data = data;
	}
	/**
	 * 返回失败信息
	 * @param data
	 * @return none
	 */
	public  void fail(T data) {
		this.code = -1;
		this.success = false;
		this.data = data;
	}
	
	public  void repeat(T data) {
		this.code = -2;
		this.success = false;
		this.data = data;
	}
	
	public void withData(T data) {
		this.code = 0;
		this.success = true;
		this.data = data;
	}

	//getter and setter
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
