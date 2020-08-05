package com.siti.common.base;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

public class BaseCtrl<BIZ extends BaseBiz, T> {

    @Autowired
    protected BIZ biz;

    @PostMapping("save")
    public T save(T entity) {
        setCreateBy(entity);
        setUpdateBy(entity);
        biz.save(entity);
        return entity;
    }

    @PutMapping("update")
    public T update(T entity) {
        setUpdateBy(entity);
        biz.update(entity);
        return entity;
    }

    @PutMapping("updateNotNull")
    public T updateNotNull(T entity) {
        biz.updateNotNull(entity);
        return entity;
    }

    /**
     * @param id 主键
     * @return 0:删除失败，1:删除成功
     */
    @DeleteMapping("delete/{id}")
    public Integer delete(@PathVariable Integer id) {
        try {
            biz.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**
     * 分页查询所有数据
     *
     * @param page     当前页
     * @param pageSize 每一页的数量
     * @return
     */
    @GetMapping("getAllByPage")
    public PageInfo<T> getAllByPage(int page, int pageSize, T entity) {
        PageHelper.startPage(page, pageSize);
        List<T> list = biz.select(entity);
        return new PageInfo<T>(list);
    }

    /**
     * 对拥有createBy, createTime属性的类设置值
     */
    private T setCreateBy(T entity) {
        //Integer loginedUserId = LoginCtrl.getLoginUserInfo().getId();
        Method method;
        try {
            method = entity.getClass().getMethod("setCreateBy", Integer.class);
          //  method.invoke(entity, loginedUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            method = entity.getClass().getMethod("setCreateTime", Date.class);
            method.invoke(entity, new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    /**
     * 对拥有updateBy, updateTime属性的类设置值
     */
    private T setUpdateBy(T entity) {
       // Integer loginedUserId = LoginCtrl.getLoginUserInfo().getId();
        Method method;
        try {
            method = entity.getClass().getMethod("setUpdateBy", Integer.class);
         //   method.invoke(entity, loginedUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            method = entity.getClass().getMethod("setUpdateTime", Date.class);
            method.invoke(entity, new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
}
