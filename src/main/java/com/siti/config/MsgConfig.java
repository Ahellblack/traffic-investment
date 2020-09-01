package com.siti.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by zyw on 2019/9/10.
 */
@Configuration
@Component
public class MsgConfig {

    //短信模板配置
    //@Value("${message.accountSId}")
    public static String ACCOUNT_SID = "8a216da855826478015599e3f66e1411";
    //@Value("${message.accountToken}")
    public static String AUTH_TOKEN = "71a6619327734d81957e60f2eeaa2626";
    //@Value("${message.appId}")
    public static String APPID = "8aaf0708732220a6017409a113c962ee";
    //@Value("${message.serverIp}")
    public static String SERVERIP = "app.cloopen.com";
    //@Value("${message.serverPort}")
    public static String SERVERPORT = "8883";
    ////模板号
    //@Value("${message.templateId}")
    public static String TEMPLATEID = "632437";//验证码编码
    public static String TEMPLATEID2 = "492507";//验证码编码


}
