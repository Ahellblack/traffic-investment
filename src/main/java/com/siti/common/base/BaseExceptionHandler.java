package com.siti.common.base;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.siti.common.base.vo.ReturnValueVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**统一异常处理，并做日志记录
 * @author zhiyu
 * */
@ControllerAdvice
public class BaseExceptionHandler {

	/**捕获任何服务端异常,并作日志
	 * */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void commonHandler(Exception e){
		e.printStackTrace();
	}
	
	/**处理Form验证失败异常,不做日志记录
	 * */
	@ExceptionHandler(FormValidFailureException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ReturnValueVo formValidErrorHandler(FormValidFailureException e){
		Errors errors = e.getErrors();
		List<FieldError> list = errors.getFieldErrors();
		Map<String,String> map = new HashMap<>();
		for(FieldError fieldError : list){
			map.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return new ReturnValueVo(ReturnValueVo.ERROR,map);
	}
}