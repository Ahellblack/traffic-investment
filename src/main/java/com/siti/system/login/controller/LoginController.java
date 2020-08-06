package com.siti.system.login.controller;


import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.siti.common.EncryptedString;
import com.siti.common.Result;
import com.siti.common.constant.CacheConstant;
import com.siti.common.constant.CommonConstant;
import com.siti.common.constant.DefContants;
import com.siti.common.vo.LoginUser;
import com.siti.system.biz.OrgBiz;
import com.siti.system.biz.RoleBiz;
import com.siti.system.db.Auth;
import com.siti.system.db.Org;
import com.siti.system.db.Role;
import com.siti.system.login.entity.SysDepart;
import com.siti.system.login.entity.SysUser;
import com.siti.system.login.model.SysLoginModel;
import com.siti.system.login.service.ISysBaseAPI;
import com.siti.system.login.service.ISysDepartService;
import com.siti.system.login.service.ISysLogService;
import com.siti.system.login.service.ISysUserService;
import com.siti.system.login.util.RandImageUtil;
import com.siti.system.mapper.AuthMapper;
import com.siti.system.mapper.OrgMapper;
import com.siti.system.mapper.RoleMapper;
import com.siti.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @since 2018-12-17
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "用户登录")
@Slf4j
public class LoginController {
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysBaseAPI sysBaseAPI;
    @Resource
    private ISysLogService logService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ISysDepartService sysDepartService;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private OrgMapper orgMapper;
    @Resource
    private AuthMapper authMapper;
    @Resource
    private RoleBiz roleBiz;
    @Resource
    private OrgBiz orgBiz;
    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    @ApiOperation("登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<JSONObject> login(@RequestBody SysLoginModel sysLoginModel) {
        Result<JSONObject> result = new Result<JSONObject>();
        String username = sysLoginModel.getUsername();
        String password = sysLoginModel.getPassword();
        //update-begin--Author:scott  Date:20190805 for：暂时注释掉密码加密逻辑，有点问题
        //前端密码加密，后端进行密码解密
        //password = AesEncryptUtil.desEncrypt(sysLoginModel.getPassword().replaceAll("%2B", "\\+")).trim();//密码解密
        //update-begin--Author:scott  Date:20190805 for：暂时注释掉密码加密逻辑，有点问题

        //update-begin-author:taoyan date:20190828 for:校验验证码
        //以redis记录验证码
        /* String captcha = sysLoginModel.getCaptcha();
        if(captcha==null){
            result.error500("验证码无效");
            return result;
        }
        String lowerCaseCaptcha = captcha.toLowerCase();
		String realKey = MD5Util.MD5Encode(lowerCaseCaptcha+sysLoginModel.getCheckKey(), "utf-8");
		Object checkCode = redisUtil.get(realKey);
		if(checkCode==null || !checkCode.equals(lowerCaseCaptcha)) {
			result.error500("验证码错误");
			return result;
		}*/
        //update-end-author:taoyan date:20190828 for:校验验证码

        //1. 校验用户是否有效
        SysUser sysUser = sysUserService.getUserByName(username);
        result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }

        //2. 校验用户名或密码是否正确
        String userpassword = PasswordUtil.encrypt(username, password, sysUser.getSalt());
        String syspassword = sysUser.getPassword();
        if (!syspassword.equals(userpassword)) {
            result.error500("用户名或密码错误");
            return result;
        }

        //用户登录信息
        userInfo(sysUser, result);
        sysBaseAPI.addLog("用户名: " + username + ",登录成功！", CommonConstant.LOG_TYPE_1, null);

        return result;
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logout")
    public Result<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        //用户退出逻辑
        String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
        if (oConvertUtils.isEmpty(token)) {
            return Result.error("退出登录失败！");
        }
        String username = JwtUtil.getUsername(token);
        LoginUser sysUser = sysBaseAPI.getUserByName(username);
        if (sysUser != null) {
            sysBaseAPI.addLog("用户名: " + sysUser.getRealname() + ",退出成功！", CommonConstant.LOG_TYPE_1, null);
            log.info(" 用户名:  " + sysUser.getRealname() + ",退出成功！ ");
            //清空用户登录Token缓存
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
            //清空用户登录Shiro权限缓存
            redisUtil.del(CommonConstant.PREFIX_USER_SHIRO_CACHE + sysUser.getId());
            //清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
            redisUtil.del(String.format("%s::%s", CacheConstant.SYS_USERS_CACHE, sysUser.getUsername()));
            //调用shiro的logout
            SecurityUtils.getSubject().logout();
            return Result.ok("退出登录成功！");
        } else {
            return Result.error("Token无效!");
        }
    }

    /**
     * 获取访问量
     *
     * @return
     */
    @GetMapping("loginfo")
    public Result<JSONObject> loginfo() {
        Result<JSONObject> result = new Result<JSONObject>();
        JSONObject obj = new JSONObject();
        //update-begin--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
        // 获取一天的开始和结束时间
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dayStart = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date dayEnd = calendar.getTime();
        // 获取系统访问记录
        Long totalVisitCount = logService.findTotalVisitCount();
        obj.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = logService.findTodayVisitCount(dayStart, dayEnd);
        obj.put("todayVisitCount", todayVisitCount);
        Long todayIp = logService.findTodayIp(dayStart, dayEnd);
        //update-end--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
        obj.put("todayIp", todayIp);
        result.setResult(obj);
        result.success("登录成功");
        return result;
    }

    /**
     * 获取访问量
     *
     * @return
     */
    @GetMapping("visitInfo")
    public Result<List<Map<String, Object>>> visitInfo() {
        Result<List<Map<String, Object>>> result = new Result<List<Map<String, Object>>>();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dayEnd = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date dayStart = calendar.getTime();
        List<Map<String, Object>> list = logService.findVisitCount(dayStart, dayEnd);
        result.setResult(oConvertUtils.toLowerCasePageList(list));
        return result;
    }


    /**
     * 手机号登录接口
     *
     * @param jsonObject
     * @return
     */
    @ApiOperation("手机号登录接口")
    @PostMapping("/phoneLogin")
    public Result<JSONObject> phoneLogin(@RequestBody JSONObject jsonObject) {
        Result<JSONObject> result = new Result<JSONObject>();
        String phone = jsonObject.getString("mobile");

        //校验用户有效性
        SysUser sysUser = sysUserService.getUserByPhone(phone);
        result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }

        String smscode = jsonObject.getString("captcha");
        Object code = redisUtil.get(phone);
        if (!smscode.equals(code)) {
            result.setMessage("手机验证码错误");
            return result;
        }
        //用户信息
        userInfo(sysUser, result);
        //添加日志
        sysBaseAPI.addLog("用户名: " + sysUser.getUsername() + ",登录成功！", CommonConstant.LOG_TYPE_1, null);

        return result;
    }


    /**
     * 用户信息
     *
     * @param sysUser
     * @param result
     * @return
     */
    private Result<JSONObject> userInfo(SysUser sysUser, Result<JSONObject> result) {
        String syspassword = sysUser.getPassword();
        String username = sysUser.getUsername();
        // 生成token
        String token = JwtUtil.sign(username, syspassword);
        // 设置token缓存有效时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);

        List<Role> roles = roleMapper.getByUserId(sysUser.getId());
        Set<Org> orgs = orgMapper.getByUserId(sysUser.getId());
        List<Integer> roleIds = new ArrayList<Integer>();
        for (Role role : roles) {
            roleIds.add(role.getId());
        }
        List<Auth> menuList = new ArrayList<>();
        List<Auth> authList = new ArrayList<>();
        if (roleIds.size() > 0) {
            menuList = authMapper.getMenu(roleIds);   //  获取角色的目录权限
            authList = authMapper.getPermissionByRoleId(roleIds);    //  获取所有角色的所有授权信息
        }
        // 获取用户部门信息
        JSONObject obj = new JSONObject();
        /*List<SysDepart> departs = sysDepartService.queryUserDeparts(sysUser.getId());
		obj.put("departs", departs);
		if (departs == null || departs.size() == 0) {
			obj.put("multi_depart", 0);
		} else if (departs.size() == 1) {
			sysUserService.updateUserDepart(username, departs.get(0).getOrgCode());
			obj.put("multi_depart", 1);
		} else {
			obj.put("multi_depart", 2);
		}*/
        obj.put("org", orgs);
        obj.put("role", roles);
        obj.put("menu", menuList);
        obj.put("auth", authList);
        obj.put("token", token);
        obj.put("userInfo", sysUser);
        result.setResult(obj);
        result.success("登录成功");
        return result;
    }

    /**
     * 获取加密字符串
     *
     * @return
     */
    @GetMapping(value = "/getEcncryptedString")
    public Result<Map<String, String>> getEncryptedString() {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", EncryptedString.key);
        map.put("iv", EncryptedString.iv);
        result.setResult(map);
        return result;
    }

    /**
     * 获取校验码
     */
    @ApiOperation("获取验证码")
    @GetMapping(value = "/getCheckCode")
    public Result<Map<String, String>> getCheckCode() {
        Result<Map<String, String>> result = new Result<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        try {
            String code = RandomUtil.randomString(BASE_CHECK_CODES, 4);
            String key = MD5Util.MD5Encode(code + System.currentTimeMillis(), "utf-8");
            redisUtil.set(key, code, 60);
            map.put("key", key);
            //update-begin-author：taoyan date:20200210 for:TASK #3391 【bug】安全问题，返回验证码不安全
            String encode = Base64.getEncoder().encodeToString(code.getBytes("UTF-8"));
            map.put("code", encode);
            //update-end-author：taoyan date:20200210 for:TASK #3391 【bug】安全问题，返回验证码不安全
            result.setResult(map);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 后台生成图形验证码
     *
     * @param response
     * @param key
     */
    @ApiOperation("获取验证码2")
    @GetMapping(value = "/randomImage/{key}")
    public Result<String> randomImage(HttpServletResponse response, @PathVariable String key) {
        Result<String> res = new Result<String>();
        try {
            String code = RandomUtil.randomString(BASE_CHECK_CODES, 4);
            String lowerCaseCode = code.toLowerCase();
            String realKey = MD5Util.MD5Encode(lowerCaseCode + key, "utf-8");
            redisUtil.set(realKey, lowerCaseCode, 60);
            String base64 = RandImageUtil.generate(code);
            res.setSuccess(true);
            res.setResult(base64);
        } catch (Exception e) {
            res.error500("获取验证码出错" + e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * app登录
     *
     * @param sysLoginModel
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mLogin", method = RequestMethod.POST)
    public Result<JSONObject> mLogin(@RequestBody SysLoginModel sysLoginModel) throws Exception {
        Result<JSONObject> result = new Result<JSONObject>();
        String username = sysLoginModel.getUsername();
        String password = sysLoginModel.getPassword();

        //1. 校验用户是否有效
        SysUser sysUser = sysUserService.getUserByName(username);
        result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }

        //2. 校验用户名或密码是否正确
        String userpassword = PasswordUtil.encrypt(username, password, sysUser.getSalt());
        String syspassword = sysUser.getPassword();
        if (!syspassword.equals(userpassword)) {
            result.error500("用户名或密码错误");
            return result;
        }

        String orgCode = sysUser.getOrgCode();
        if (oConvertUtils.isEmpty(orgCode)) {
            //如果当前用户无选择部门 查看部门关联信息
            List<SysDepart> departs = sysDepartService.queryUserDeparts(sysUser.getId() + "");
            if (departs == null || departs.size() == 0) {
                result.error500("用户暂未归属部门,不可登录!");
                return result;
            }
            orgCode = departs.get(0).getOrgCode();
            sysUser.setOrgCode(orgCode);
            this.sysUserService.updateUserDepart(username, orgCode);
        }
        JSONObject obj = new JSONObject();
        //用户登录信息
        obj.put("userInfo", sysUser);

        // 生成token
        String token = JwtUtil.sign(username, syspassword);
        // 设置超时时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
        //token 信息
        obj.put("token", token);
        result.setResult(obj);
        result.setSuccess(true);
        result.setCode(200);
        sysBaseAPI.addLog("用户名: " + username + ",登录成功[移动端]！", CommonConstant.LOG_TYPE_1, null);
        return result;
    }

}