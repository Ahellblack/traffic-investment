package com.siti.system.valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.siti.system.db.User;
import com.siti.system.mapper.UserMapper;

@Component
public class UserValid implements Validator {

	@Autowired
	private UserMapper userDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(User.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User)target;
		if(userDao.isExistName(user.getUserName(), user.getId())){
			errors.rejectValue("userName", "User.userName.repeat", "用户名重复");
		}
	}
}
