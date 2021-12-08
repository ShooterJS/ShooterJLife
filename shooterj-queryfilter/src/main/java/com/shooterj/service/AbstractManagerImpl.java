package com.shooterj.service;

import com.github.pagehelper.PageHelper;
import com.shooterj.AuthenticationUtil;
import com.shooterj.dao.MyBatisDao;
import com.shooterj.model.BaseModel;
import com.shooterj.query.*;
import com.shooterj.util.UniqueIdUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Transactional
public abstract class AbstractManagerImpl<PK extends Serializable, T extends Serializable> implements Manager<PK, T> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractManagerImpl.class);
    //获取基础类
    protected abstract MyBatisDao<PK, T> getDao();

    /**
     * 获取当前泛型的类型
     *
     * @return 类型
     */
    @SuppressWarnings("unchecked")
    private Class<? super T> getTypeClass() {
        // 获取第二个泛型(T)对应的class
        Class<? super T> rawType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return rawType;
    }

    /**
     * 检查通用查询对象中的实体类型
     * <pre>
     * 1.若实体类型为空，使用当前的泛型补充；
     * 2.若实体类不为空，使用当前泛型类检查，不一致则抛出异常。
     * </pre>
     *
     * @param queryFilter
     * @throws SystemException
     */
    public void handleQueryFilter(QueryFilter queryFilter) {
        if (queryFilter == null) {
            throw new RuntimeException("QueryFilter通用查询对象不能为空.");
        }
        Class<?> clazz = queryFilter.getClazz();
        Class<? super T> typeClass = getTypeClass();
        if (clazz == null) {
            // 所传入的通用查询器未指定 对应实体类时，从当前泛型中获取
            queryFilter.setClazz(typeClass);
        } else {
            if (!clazz.equals(typeClass)) {
                throw new RuntimeException(String.format("QueryFilter中的实体类:%s与Dao泛型中的实体类:%s不一致.", clazz, typeClass));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void create(T entity) {
        if (entity instanceof BaseModel) {
            BaseModel<PK> model = (BaseModel<PK>) entity;
            if (model.getId() == null) {
                if (model.getClass().getGenericSuperclass() == Long.class) {
                    model.setId((PK) UniqueIdUtil.getSuid());
                }
            }
            if (model.getCreateTime() == null) {
                model.setCreateTime(LocalDateTime.now());
            }
            if (StringUtils.isEmpty(model.getCreateBy())) {
                model.setCreateBy(AuthenticationUtil.getCurrentUserId());
            }
            if (StringUtils.isEmpty(model.getCreateOrgId())) {
                model.setCreateOrgId(AuthenticationUtil.getCurrentUserMainOrgId());
            }
            // 增加自定义的新增公共字段
            if (StringUtils.isEmpty(model.getCreateName())) {
                model.setCreateName(AuthenticationUtil.getCurrentUserFullname());
            }
            if (StringUtils.isEmpty(model.getCreateOrgName())) {
                model.setCreateOrgName(AuthenticationUtil.getCurrentUserMainOrgId());
            }
            if (StringUtils.isEmpty(model.getOrgVersion())) {
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                model.setOrgVersion("V".concat(sdf.format(now)));
            }

        }
        getDao().create(entity);
    }

    @SuppressWarnings("unchecked")
    public void update(T entity) {
        if (entity instanceof BaseModel) {
            BaseModel<T> model = (BaseModel<T>) entity;
            if (model.getUpdateTime() == null) {
                model.setUpdateTime(LocalDateTime.now());
            }
            if (StringUtils.isEmpty(model.getUpdateBy())) {
                model.setUpdateBy(AuthenticationUtil.getCurrentUserId());
            }
            // 增加自定义的新增公共字段
            if (StringUtils.isEmpty(model.getUpdateName())) {
                model.setUpdateName(AuthenticationUtil.getCurrentUserFullname());
            }
        }
        getDao().update(entity);
    }

    public void remove(PK entityId) {
        getDao().remove(entityId);
    }

    public T get(PK entityId) {
        return getDao().get(entityId);
    }

    public List<T> getByIds(List<PK> entityIds, String idColumn) {
        String idProp = StringUtils.isEmpty(idColumn) ? "id" : idColumn;
        QueryFilter queryFilter = QueryFilter.build()
                .withQuery(new QueryField(idProp, entityIds, QueryOP.IN));
        PageList<T> query = this.query(queryFilter);
        return query.getRows();
    }

    @SuppressWarnings("unchecked")
    public void removeByIds(PK... ids) {
        if (ids != null) {
            for (PK pk : ids) {
                remove(pk);
            }
        }
    }

    public PageList<T> query(QueryFilter queryFilter) {
        handleQueryFilter(queryFilter);
        PageBean pageBean = queryFilter.getPageBean();
        if (pageBean == null) {
            PageHelper.startPage(1, 1000000, true);
        } else {
            PageHelper.startPage(pageBean.getPage(), pageBean.getPageSize(), pageBean.showTotal());
        }
        List<T> query = getDao().query(queryFilter.getParams());
        return new PageList<>(query);
    }

    public T getUnique(String column, Object value) {
        QueryFilter queryFilter = QueryFilter.build()
                .withDefaultPage()
                .withQuery(new QueryField(column, value));
        PageList<T> query = this.query(queryFilter);
        long total = query.getTotal();
        if (total <= 0) {
            return null;
        } else if (total == 1) {
            return query.getRows().get(0);
        } else {
            throw new TooManyResultsException(String.format("符合查询条件的记录为:%s条，超过1条.", total));
        }
    }

    public List<T> getAll() {
        QueryFilter queryFilter = QueryFilter.build();
        PageList<T> query = this.query(queryFilter);
        return query.getRows();
    }

    public PageList<T> getAllByPage(PageBean pageBean) {
        QueryFilter queryFilter = QueryFilter.build()
                .withPage(pageBean);
        PageList<T> query = this.query(queryFilter);
        return query;
    }

    @Override
    public void startPageBean(PageBean pageBean) {
        if (pageBean == null) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage(), pageBean.getPageSize(), pageBean.showTotal());
        }
    }




}
