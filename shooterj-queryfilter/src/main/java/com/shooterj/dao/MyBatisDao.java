package com.shooterj.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface MyBatisDao<PK extends Serializable, T> {
    /**
     * 创建实体对象
     *
     * @param entity 实体
     */
    void create(T entity);

    /**
     * 更新实体对象
     *
     * @param entity 实体
     */
    void update(T entity);

    /**
     * 按实体ID删除对象
     *
     * @param entityId 实体ID
     * @return 删除的数据条数
     */
    int remove(PK entityId);

    /**
     * 按实体ID获取实体
     *
     * @param entityId 实体ID
     * @return 实体
     */
    T get(PK entityId);

    /**
     * 查询实体对象
     *
     * @param map 通用查询对象
     * @return 分页结果
     */
    List<T> query(Map<String, Object> map);
}
