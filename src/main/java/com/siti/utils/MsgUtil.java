package com.siti.utils;


import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by 12293 on 2020/8/18.
 */
public class MsgUtil {
    //生产环境请求地址：app.cloopen.com
    static String serverIp = "app.cloopen.com";
    //请求端口
    static String serverPort = "8883";
    //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
    static String accountSId = "8a216da855826478015599e3f66e1411";
    static String accountToken = "71a6619327734d81957e60f2eeaa2626";
    //请使用管理控制台中已创建应用的APPID
    static String appId = "8a216da86c8a1a54016c8dc74f1c0182";

    public static void sendMsg(String number,String verificationCode){
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        String to = number;
        String templateId= "492507";
        String[] datas = {verificationCode};
        //String subAppend="1234";  //可选 扩展码，四位数字 0~9999
        //String reqId="fadfafas";  //可选 第三方自定义消息id，最大支持32位英文数字，同账号下同一自然天内不允许重复
        //HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
        HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas/*,subAppend,reqId*/);
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }
    }



}
