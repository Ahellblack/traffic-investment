package com.siti.system.login.mapper;

import org.apache.ibatis.annotations.Select;
import com.siti.system.login.entity.SysDictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 *
 * @since 2018-12-28
 */
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {
    @Select("SELECT * FROM sys_dict_item WHERE DICT_ID = #{mainId} order by sort_order asc, item_value asc")
    public List<SysDictItem> selectItemsByMainId(String mainId);
}
