package com.shooterj.service;


import com.shooterj.query.PageBean;
import com.shooterj.query.PageList;
import com.shooterj.query.QueryFilter;
import org.omg.CORBA.SystemException;

import java.io.Serializable;
import java.util.List;


public interface Manager<PK extends Serializable, T> {
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
     * @param entityId 实体主键
     */
    void remove(PK entityId);

    /**
     * 按实体主键获取实体
     *
     * @param entityId 实体主键
     * @return 实体
     */
    T get(PK entityId);

    /**
     * 按实体ID获取实体
     *
     * @param entityIds 实体主键集合
     * @param idColumn  主键列名
     * @return 结果列表
     * @throws SystemException
     */
    List<T> getByIds(List<PK> entityIds, String idColumn);

    /**
     * 按实体IDs删除记录
     *
     * @param ids 主键集合
     */
    @SuppressWarnings("unchecked")
    void removeByIds(PK... ids);

    /**
     * 查询实体对象
     *
     * @param queryFilter 通用查询对象
     * @return 分页结果
     */
    PageList<T> query(QueryFilter queryFilter);

    /**
     * 查询单个实体对象
     *
     * @param column 列名
     * @param value  列值
     * @return 单个结果
     */
    T getUnique(String column, Object value);

    /**
     * 取得所有查询对象
     *
     * @return 所有结果列表
     */
    List<T> getAll();

    /**
     * 取得所有查询对象并分页查询
     *
     * @param pageBean 分页查询对象
     * @return 分页查询结果
     */
    PageList<T> getAllByPage(PageBean pageBean);

    /**
     * 开始执行分页查询
     * <pre>
     * 在接下来的查询语句中执行分页查询
     * </pre>
     *
     * @param pageBean 分页查询参数
     */
    void startPageBean(PageBean pageBean);
}