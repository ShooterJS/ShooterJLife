package com.shooterj.query;

import java.util.*;

public class QueryFilter {
    /**
     * 条件SQL的KEY
     */
    public static final String WHERE_SQL_TAG = "whereSql";
    /**
     * 排序SQL的KEY
     */
    public static final String ORDER_SQL_TAG = "orderBySql";

    private PageBean pageBean;
    private List<FieldSort> sorter = new ArrayList<>();
    private Map<String, Object> params = new LinkedHashMap<>();
    private List<QueryField> querys = new ArrayList<>();

    private Class<?> clazz;

    public PageBean getPageBean() {
        return pageBean;
    }

    private QueryFilter() {}

    private QueryFilter(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static QueryFilter build() {
        return new QueryFilter();
    }

    /**
     * 构造器
     *
     * @param clazz 当前通用查询所对应实体类
     * @return
     */
    public static QueryFilter build(Class<?> clazz) {
        return new QueryFilter(clazz);
    }

    public QueryFilter withDefaultPage() {
        this.pageBean = new PageBean();
        return this;
    }

    public QueryFilter withPage(PageBean pageBean) {
        this.pageBean = pageBean;
        return this;
    }

    public QueryFilter withSorter(FieldSort fieldSort) {
        this.sorter.add(fieldSort);
        return this;
    }

    public QueryFilter withQuery(QueryField queryField) {
        this.querys.add(queryField);
        return this;
    }

    public QueryFilter withParam(String key, Object value) {
        this.params.put(key, value);
        return this;
    }

    public Map<String, Object> getParams() {
        // 生成SQL语句到params中
        generatorSQL();
        // 初始化查询元素中的查询参数到params中
        initParams(querys);
        return params;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public QueryField findQueryField(String property) {
        if (querys != null && !querys.isEmpty()) {
            for (QueryField query : querys) {
                if (Objects.equals(query.getProperty(), property)) {
                    return query;
                }
            }
        }
        return null;
    }

    public void removeQueryField(String... property) {
        if (querys != null && !querys.isEmpty()) {
            Set<String> propertySet = new HashSet<>(Arrays.asList(property));
            querys.removeIf(queryField -> propertySet.contains(queryField.getProperty()));
        }
    }

    public QueryFilter addFilter(String property, Object value, QueryOP operation, FieldRelation relation) {
        QueryField queryField = new QueryField(property, value, operation, relation);
        querys.add(queryField);
        return this;
    }

    public QueryFilter addFilter(String property, Object value, QueryOP operation, FieldRelation relation, String group) {
        QueryField queryField = new QueryField(property, value, operation, relation, group);
        querys.add(queryField);
        return this;
    }

    public QueryFilter addFilter(String property, Object value, QueryOP operation) {
        QueryField queryField = new QueryField(property, value, operation, FieldRelation.AND);
        querys.add(queryField);
        return this;
    }

    public void addParams(String property, Object value) {
        params.put(property, value);
    }

    public List<FieldSort> getSorter() {
        return sorter;
    }

    public void setSorter(List<FieldSort> sorter) {
        this.sorter = sorter;
    }

    public List<QueryField> getQuerys() {
        return querys;
    }

    public void setQuerys(List<QueryField> querys) {
        this.querys = querys;
    }

    /**
     * 获取当前通用查询所对应的实体类
     *
     * @return 实体类
     */
    public Class<?> getClazz() {
        return this.clazz;
    }

    /**
     * 设置当前通用查询缩对应的实体类
     *
     * @param clazz 实体类
     */
    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    private void generatorSQL() {
        // 生成查询的SQL语句
        String querySQL = generatorQuerySQL();
        if (querySQL != null && !Objects.equals("", querySQL)) {
            params.put(WHERE_SQL_TAG, querySQL);
        }
        int orderSize = sorter.size();
        if (orderSize > 0) {
            StringBuffer sb = new StringBuffer();
            int i = 0;
            for (FieldSort fieldSort : sorter) {
                if (i++ > 0) {
                    sb.append(",");
                }
                sb.append(fieldSort.toSql(clazz));
            }
            params.put(ORDER_SQL_TAG, sb.toString());
        }
    }

    private String generatorQuerySQL() {
        int size = querys.size();
        if (size == 0) return "";
        if (size == 1) {
            return querys.get(0).toSql(clazz);
        } else {
            Map<String, List<QueryField>> map = new HashMap<>();
            // 按照分组归类查询条件
            for (QueryField element : querys) {
                String group = element.getGroup();
                List<QueryField> list = map.get(group);
                if (list == null || list.isEmpty()) {
                    list = new ArrayList<>();
                    map.put(group, list);
                }
                list.add(element);
            }
            List<StringBuffer> sbList = new ArrayList<>();

            for (String s : map.keySet()) {
                StringBuffer sqlBuf = new StringBuffer();
                String group = s;
                List<QueryField> list = map.get(group);
                QueryField firstField = list.get(0);
                String relation = firstField.getRelation().value();
                int fieldList = list.size();
                for (int i = 0; i < fieldList; i++) {
                    if (i > 0) {
//						sqlBuf.append(" " + relation + " ");
                        sqlBuf.append(" ").append(list.get(i).getRelation().value()).append(" ");
                    }
                    sqlBuf.append(list.get(i).toSql(clazz));
                }
                if (fieldList > 1) {
                    sqlBuf.insert(0, " (");
                    sqlBuf.append(") ");
                }
                sbList.add(sqlBuf);
            }

            StringBuffer result = new StringBuffer();
            for (int i = 0; i < sbList.size(); i++) {
                if (i > 0) {
                    result.append(" ").append(FieldRelation.AND.value()).append(" ");
                }
                result.append(sbList.get(i).toString());
            }
            return result.toString();
        }
    }

    //初始化参数
    private void initParams(List<QueryField> querys) {
        if (querys == null || querys.isEmpty()) return;
        for (QueryField element : querys) {
            QueryField queryField = (QueryField) element;
            QueryOP operation = queryField.getOperation();
            if (QueryOP.IS_NULL.equals(operation) || QueryOP.NOTNULL.equals(operation)
                    || QueryOP.IN.equals(operation) || QueryOP.NOT_IN.equals(operation)) {
                continue;
            }
            String property = queryField.getProperty();
            if (property.contains(".")) {
                property = property.substring(property.indexOf(".") + 1);
            }
            Object value = queryField.getValue();
            this.params.put(property, value);
        }
    }
}
