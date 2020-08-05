package com.siti.common.base.vo;

/**封装数据异步验证是否通过
 * */
public class ValidVo {

	private boolean valid;

	public ValidVo(){}
	
	public ValidVo(boolean valid) {
		this.valid = valid;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
