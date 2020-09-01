package com.siti.utils;


import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.siti.config.MsgConfig;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by 12293 on 2020/8/18.
 */
public class MsgUtil {

    public static void sendMsg(String number,String verificationCode){
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(MsgConfig.SERVERIP, MsgConfig.SERVERPORT);
        sdk.setAccount(MsgConfig.ACCOUNT_SID, MsgConfig.AUTH_TOKEN);
        sdk.setAppId(MsgConfig.APPID);
        sdk.setBodyType(BodyType.Type_JSON);
        String to = number;
        String templateId= MsgConfig.TEMPLATEID;
        String[] datas = {verificationCode,"2分钟"};
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
