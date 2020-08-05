package com.siti.common.base;

import org.springframework.validation.Errors;

/**Form表单验证失败异常
 * 当form表单验证失败时需抛出此异常
 * */
public class FormValidFailureException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private Errors errors;

	public FormValidFailureException(Errors errors) {
		this.errors = errors;
	}

	public Errors getErrors() {
		return errors;
	}
	
}