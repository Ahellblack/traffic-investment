package com.siti.common.base;


import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public class BaseBiz<DAO extends Mapper, T> {

    @Autowired
    protected DAO dao;

    public void save(T entity) {
        dao.insert(entity);
    }

    public void update(T entity) {
        dao.updateByPrimaryKey(entity);
    }

    public void updateNotNull(T entity) {
        dao.updateByPrimaryKeySelective(entity);
    }

    public void delete(T entity) {
        dao.delete(entity);
    }

    public void deleteById(Object id) {
        dao.deleteByPrimaryKey(id);
    }

    public T selectOne(T entity) {
        return (T) dao.selectOne(entity);
    }

    public List<T> select(T entity) {
        return dao.select(entity);
    }

    public List<T> selectByExample(Example example) {
        return dao.selectByExample(example);
    }

    /**
     * 根据实体属性统计总数
     */
    public Integer selectCount(T entity) {
        return dao.selectCount(entity);
    }

    public Integer selectCountByExample(Example example) {
        return dao.selectCountByExample(example);
    }

    /**
     * 判断是否有添加权限
     */
    public void addAuthJudge() {
        String ranges = null;//authBiz.getAuthRange("ADD_" + bizClassNameToUnderLine());
        if (ranges == null) {    //无添加权限
            throw new RuntimeException("您无添加权限，请联系管理员！");
        }
    }

    /**
     * Biz层类名，去掉Biz后缀，转换为下划线且全大写
     * eg：ContractReviewBiz -> CONTRACT_REVIEW
     */
    protected String bizClassNameToUnderLine() {
        String classSimpleName = this.getClass().getSimpleName();
        classSimpleName = classSimpleName.substring(0, classSimpleName.length() - 3);
        char[] chars = classSimpleName.toCharArray();
        StringBuilder sb = new StringBuilder();
        sb.append(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            char s = chars[i];
            if (s + 1 > 65 && s + 1 < 91) {
                sb.append('_');
                sb.append(s);
                continue;
            }
            sb.append(s);
        }
        return sb.toString().toUpperCase();
    }
}
