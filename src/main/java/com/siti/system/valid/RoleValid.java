package com.siti.system.valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.siti.system.db.Role;
import com.siti.system.mapper.RoleMapper;

@Component
public class RoleValid implements Validator {

	@Autowired
	private RoleMapper roleDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Role.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Role role = (Role)target;
		if(roleDao.findExistByCode(role.getCode(), role.getId())){
			errors.rejectValue("code", "Role.code.repeat", "角色编码重复");
		}
		if(roleDao.findExistByName(role.getName(), role.getId())){
			errors.rejectValue("name", "Role.name.repeat", "角色名称重复");
		}
	}
}
