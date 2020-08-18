package com.siti.system.ctrl;

import com.siti.common.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* Created by zyh on 2017/9/18.
* 登录
*/
@RestController
@RequestMapping("login")
public class LoginCtrl {

   /**
    * 获取当前登录用户
    * @return LoginUser
    */
    @PostMapping
    public static LoginUser getLoginUserInfo() {
        //获取登录用户信息
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return sysUser;
    }

}
