package com.siti.construction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siti.construction.entity.BusinessInvestPlan;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Solarie on 2020/6/18.
 */
public interface InvestPlanMapper extends BaseMapper<BusinessInvestPlan> {

    @Select("SELECT * FROM `business_invest_plan` where ym = #{ym}  and  construction_code = #{constructionCode} and type = #{type} limit 1 ")
    BusinessInvestPlan getByYmAndConstructionCode(@Param("ym") String ym, @Param("constructionCode") String constructionCode, @Param("type") Integer type);

    @Update("UPDATE `traffic_invest`.`business_invest_plan` " +
            "SET `content` = #{plan.content} " +
            "WHERE `construction_code` = #{plan.constructionCode} AND `ym` = #{plan.ym} AND `type` = #{plan.type} ")
    void updateByYm(@Param("plan") BusinessInvestPlan plan);
}
