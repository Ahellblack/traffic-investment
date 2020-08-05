package com.siti.common.base;

import java.text.SimpleDateFormat;

/**
 * 公用常量类。
 *
 * @author chenjianhong
 */
public abstract class Constants {

    /**
     * 默认时间格式
     **/
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


    public static final String MANY_YEARS_LATER = "2099-01-01 00:00:00";
    /**
     * Date默认时区
     **/
    public static final String DATE_TIMEZONE = "GMT+8";

    /**
     * 分配给应用的AppKey
     */
    public static final String APP_KEY = "30000002";
    public static final String SDPT_APP_KEY = "30000002";

    /**
     * md5加密要用的APPSecret
     */
    public static final String APP_SECRET = "5d78481fr6c236b7f4a2ac416588278d";


    /**
     * 应用场景行业编号
     */
    public static final String SCENE_INDUSTRY_CODE = "jdcwx";

    public static final String DEPT_NAME = "建交委运管署汽修科";

    public static final String DEPT_CODE = "jjw";  // 单位编号，为一级部门

    public static final String DEPT = "建交委";  // 单位名称

    public static final String DEPT_BM = "运管署";  // 部门名称

    public static final String SYNC_MODE_APPEND = "1";  // 增量同步
    public static final String SYNC_MODE_UPDATE = "2";  // 全量同步

    public static final String DISPOSAL_METHOD_YJPX = "1";  // 应急排险
    public static final String DISPOSAL_METHOD_QXCC = "2";  // 全项彻查
    public static final String DISPOSAL_METHOD_ZXZZ = "3";  // 专项整治
    public static final String DISPOSAL_METHOD_ZDJC = "4";  // 重点检查
    public static final String DISPOSAL_METHOD_GZCZ = "5";  // 跟踪查证


    public static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);

    public static final String OT_BASE = "http://219.233.18.233/pdjg_plat";
    public static final String IT_BASE = "http://10.242.181.150:8081/pdjg_plat";

    public static final String OP_BASE = "http://219.233.18.233/pdjg_api";
    public static final String IP_BASE = "http://10.242.181.150:8081/pdjg_api";


    public static final String URL_PATH = "/api/intf/";
    // 适配 api协议1.5.3
    public static final String API_2_1_1 = "queryCompanyInfo";  // 企业全生命周期查询
    public static final String API_2_1_2 = "enty_basic_info";  // 主体基本信息
    public static final String API_2_1_3 = "enty_license_info";  // 主体许可证信息
    public static final String API_2_1_4 = "industry_credit_category";  // 行业信用指标类别
    public static final String API_2_1_5 = "industry_credit_item";  // 行业信用指标项
    public static final String API_2_1_6 = "industry_risk_category";  // 行业风险指标类别
    public static final String API_2_1_7 = "industry_risk_item";  // 行业风险点指标项
    public static final String API_2_1_8 = "supervise_item_category";  // 监管事项类别
    public static final String API_2_1_9 = "supervise_item";  // 监管事项
    public static final String API_2_1_10 = "warn_order";  // 风险预警工单
    public static final String API_2_1_11 = "check_task";  // 本单位检查任务
    public static final String API_2_1_12 = "check_order";  // 本单位检查工单
    public static final String API_2_1_13 = "check_order_child";  // 本单位检查工单(风险预警工单)多次复查信息
    public static final String API_2_1_14 = "check_order_modify";  // 本单位检查工单状态更新
    public static final String API_2_1_15 = "industry_credit_believe";  // 行业信用采信数据
    public static final String API_2_1_16 = "industry_credit_evaluate";  // 行业信用评估数据
    public static final String API_2_1_17 = "industry_credit_use";  // 行业信用用信数据
    public static final String API_2_1_18 = "industry_risk_evaluate";  // 行业风险评估数据
    public static final String API_2_1_19 = "adjust_frequency";  // 调整监管频次
    public static final String API_2_1_20 = "zero";  // 零上报

    public static final String API_2_1_21 = "synergism_item_list";  // 跨单位协同事项列表
    public static final String API_2_1_22 = "synergism_task_distribute";  // 跨单位协同派单-任务派单(派单单位->区级平台)
    public static final String API_2_1_23 = "synergism_task_receive";  // 跨单位协同派单-任务接收(协同单位->区级平台)
    public static final String API_2_1_24 = "synergism_task_feedback";  // 跨单位协同派单-任务反馈(协同单位->区级平台)
    public static final String API_2_1_25 = "synergism_task_confirm";  // 跨单位协同派单-任务签收确认(派单单位->区级平台)
    public static final String API_2_1_26 = "synergism_task_list";  // 跨单位协同派单-任务列表
    public static final String API_2_1_27 = "synergism_task_cancel";  // 跨单位协同派单-任务撤单


	// 需要推送的api接口
    public static final String[] API_ARRAY_NP = new String[]{
            API_2_1_2, API_2_1_3, API_2_1_4, API_2_1_5,
            API_2_1_6, API_2_1_7, API_2_1_8, API_2_1_9, API_2_1_10,
            API_2_1_11, API_2_1_12, API_2_1_13, API_2_1_14,API_2_1_15,
            API_2_1_16, API_2_1_17, API_2_1_18, API_2_1_19};

    public static String getUrl(String baseUrl, String api) {
        return baseUrl + URL_PATH + api;
    }
}
