package com.siti.common;

import com.siti.bussiness.entity.*;

/**
 * Created by 12293 on 2020/9/8.
 */
public class BussiEntityConfig {


    //转大写ide快捷键 ctrl+ shift + u
    public static final String BUSINESS_CONTRACT = "BusinessContract";
    public static final String BUSINESS_TENDER_APPLY = "BusinessTenderApply";
    public static final String BUSINESS_TENDER_APPROVAL = "BusinessTenderApproval";
    public static final String BUSINESS_TENDER_CHOOSE = "BusinessTenderChoose";
    public static final String BUSINESS_TENDER_PLAN = "BusinessTenderPlan";

    public static BusinessContract getBUSINESS_CONTRACT() {
        return new BusinessContract();
    }

    public static BusinessTenderApply getBUSINESS_TENDER_APPLY() {
        return new BusinessTenderApply();
    }

    public static BusinessTenderApproval getBUSINESS_TENDER_APPROVAL() {
        return new BusinessTenderApproval();
    }

    public static BusinessTenderChoose getBUSINESS_TENDER_CHOOSE() {
        return new BusinessTenderChoose();
    }

    public static BusinessTenderPlan getBUSINESS_TENDER_PLAN() {
        return new BusinessTenderPlan();
    }


}
