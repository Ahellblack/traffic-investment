package com.siti.common.base.vo;

/**封装JSON的返回值
 * */
public class ReturnValueVo {

	public static final int SUCCESS = 200;
	public static final int REPEAT = 300;
	public static final int ERROR = 500;
	public static final int EXCEPTION = 501;

	public ReturnValueVo() {
	}

	public ReturnValueVo(int status, Object value) {
		super();
		this.status = status;
		this.value = value;
	}

	//返回值的状态
	private int status;
	//返回的值
	private Object value;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
