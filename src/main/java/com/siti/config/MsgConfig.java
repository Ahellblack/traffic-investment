package com.siti.config;

import org.springframework.context.annotation.Configuration;

/**
 * Created by zyw on 2019/9/10.
 */
@Configuration
public class MsgConfig {

    //短信模板配置
    public static final String ACCOUNT_SID = "8a216da855826478015599e3f66e1411";
    public static final String AUTH_TOKEN = "71a6619327734d81957e60f2eeaa2626";
    public static final String APPID = "8a216da86c8a1a54016c8dc74f1c0182";
    public static final String SERVERIP = "app.cloopen.com";
    public static final String SERVERPORT = "8883";
    //模板号
    public static final String TEMPLATEID = "492507";//异常报警模板
    public static final String TEMPLATEID2 = "564567";//数据中断模板
    public static final String TEMPLATEID3 = "586478";//短信恢复模板
    public static final String TEMPLATEID4 = "603951";//每日总览模板

}
