
package cn.kai.bean.userbean;
/**
* @author HuangKai  

* @date 2018��4��14��  

* @version 1.0  
* <p>�������ݵ�ǰ̨ʱ�Ĺ���bean
* <p>���磺
*  {'code':0,'success':true,'data':'xxx'}
*/
public class UserReturnBean<T> {

	private Integer code;
	private boolean success;
	private T data;
	
	/**
	 * ���سɹ���Ϣ���������Ӧ����String���͵���ʾ��Ϣ.codeΪ0,,successΪtrue
	 * @param data ����ɹ�����Ϣ
	 * @return none
	 */
	public void success(T data) {
		this.code = 0;
		this.success = true;
		this.data = data;
	}
	/**
	 * ����ʧ����Ϣ
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
