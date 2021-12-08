package com.shooterj.query;

import com.shooterj.util.AppUtil;
import org.mybatis.spring.SqlSessionTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;


public class QueryField {
    private String property;
    private QueryOP operation = QueryOP.EQUAL;
    private Object value;
    private FieldRelation relation = FieldRelation.AND;
    private String group = "main";
    private Boolean hasInitValue = false;

    public QueryField() {}


    public QueryField(String property, Object value) {
        this(property, value, QueryOP.EQUAL, FieldRelation.AND);

    }


    public QueryField(String property, Object value, QueryOP operation) {
        this(property, value, operation, FieldRelation.AND);
    }


    public QueryField(String property, Object value, FieldRelation relation) {
        this(property, value, QueryOP.EQUAL, relation);
    }


    public QueryField(String property, Object value, QueryOP operation, FieldRelation relation) {
        this(property, value, operation, relation, null);
    }


    public QueryField(String property, Object value, QueryOP operation, FieldRelation relation, String group) {
        this.property = property;
        this.value = value;
        this.operation = operation;
        this.relation = relation;
        if (group != null && !Objects.equals(group, "")) {
            this.group = group;
        }
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public QueryOP getOperation() {
        return operation;
    }

    public void setOperation(QueryOP operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Boolean isGroup() {
        return false;
    }

    public FieldRelation getRelation() {
        return relation;
    }

    public void setRelation(FieldRelation relation) {
        this.relation = relation;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Boolean getHasInitValue() {
        return hasInitValue;
    }

    public void setHasInitValue(Boolean hasInitValue) {
        this.hasInitValue = hasInitValue;
    }

    private void initSqlValue() {
        if (hasInitValue) {
            // 初始化值的操作只允许执行一次
            return;
        }
        hasInitValue = true;
        if (QueryOP.IN.equals(operation) || QueryOP.NOT_IN.equals(operation)) {
            this.value = getInValueSql();
        }
        if (value != null && !Objects.equals("", value)) {
            if (QueryOP.LIKE.equals(operation) && !value.toString().startsWith("%") && !value.toString().endsWith("%")) {
                this.value = "%" + value + "%";
            } else if (QueryOP.NOT_LIKE.equals(operation) && !value.toString().startsWith("%") && !value.toString().endsWith("%")) {
                this.value = "%" + value + "%";
            } else if (QueryOP.LEFT_LIKE.equals(operation) && !value.toString().startsWith("%")) {
                this.value = "%" + value;
            } else if (QueryOP.RIGHT_LIKE.equals(operation) && !value.toString().endsWith("%")) {
                this.value = value + "%";
            } else if (QueryOP.EQUAL_IGNORE_CASE.equals(operation)) {
                this.value = this.value.toString().toUpperCase();
            }
        }
    }


    public String toSql(Class<?> clazz) {
        initSqlValue();
        if (operation == null) {
            operation = QueryOP.EQUAL;
        }
        String fieldParam;
        if (property == null) {
            throw new RuntimeException("The 'property' in QueryField can not be empty.");
        }
        if (property.contains(".")) {
            fieldParam = "#{" + property.substring(property.indexOf(".") + 1) + "}";
        } else {
            fieldParam = "#{" + property + "}";
        }
        String sql = FieldConvertUtil.property2Field(property, clazz);
        if (QueryOP.EQUAL.equals(operation)) {
            sql += " = " + fieldParam;
        } else if (QueryOP.EQUAL_IGNORE_CASE.equals(operation)) {
            sql = "upper(" + sql + ")" + " = " + fieldParam;
        } else if (QueryOP.LESS.equals(operation)) {
            sql += " < " + fieldParam;
        } else if (QueryOP.LESS_EQUAL.equals(operation)) {
            sql += " <= " + fieldParam;
        } else if (QueryOP.GREAT.equals(operation)) {
            sql += " > " + fieldParam;
        } else if (QueryOP.GREAT_EQUAL.equals(operation)) {
            sql += " >= " + fieldParam;
        } else if (QueryOP.NOT_EQUAL.equals(operation)) {
            sql += " != " + fieldParam;
        } else if (QueryOP.LEFT_LIKE.equals(operation)) {
            sql += " like " + fieldParam;
        } else if (QueryOP.RIGHT_LIKE.equals(operation)) {
            sql += " like  " + fieldParam;
        } else if (QueryOP.LIKE.equals(operation)) {
            sql += " like  " + fieldParam;
        } else if (QueryOP.NOT_LIKE.equals(operation)) {
            sql += " not like  " + fieldParam;
        } else if (QueryOP.IS_NULL.equals(operation)) {
            sql += " is null ";
        } else if (QueryOP.NOTNULL.equals(operation)) {
            sql += " is not null";
        } else if (QueryOP.IN.equals(operation) || QueryOP.NOT_IN.equals(operation)) {
            String valueStr = this.value.toString();
            if (valueStr.startsWith("(") && valueStr.endsWith(")")) {
                valueStr = valueStr.replace("(", "");
                valueStr = valueStr.replace(")", "");
                String[] strList = valueStr.split(",");
                int len = strList.length;
                //防止当in中元素大于1000时，执行语句报错
                if (len > 1000) {
                    sql = getOutLimitInSql(sql, strList);
                } else {
                    sql += " in  " + this.value;
                }
            } else {
                sql += " in  " + this.value;
            }

            if (QueryOP.NOT_IN.equals(operation)) {
                sql = sql.replace(" in  ", " not in  ");
            }
        } else if (QueryOP.BETWEEN.equals(operation)) {
            sql += getBetweenSql();
        } else if (QueryOP.REGEXP.equals(operation)) {
            sql += " REGEXP " + this.value;
        } else {
            sql += " =  " + fieldParam;
        }
        return sql;
    }

    private String getInValueSql() {
        String inValueSql = "";
        if (value instanceof String) { // 字符串形式，通过逗号分隔
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            StringTokenizer st = new StringTokenizer(value.toString(), ",");
            while (st.hasMoreTokens()) {
                sb.append("'");
                sb.append(st.nextToken());
                sb.append("'");
                sb.append(",");
            }
            sb = new StringBuilder(sb.substring(0, sb.length() - 1));
            sb.append(")");
            inValueSql = sb.toString();
        } else if (value instanceof List) { // 列表形式

            Object[] objList = ((List<?>) value).toArray();
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (Object obj : objList) {
                sb.append("'");
                sb.append(obj.toString());
                sb.append("'");
                sb.append(",");
            }
            sb = new StringBuilder(sb.substring(0, sb.length() - 1));
            sb.append(")");
            inValueSql = sb.toString();
        } else if (value instanceof String[]) { // 列表形式
            String[] objList = (String[]) value;
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (Object obj : objList) {
                sb.append("'");
                sb.append(obj.toString());
                sb.append("'");
                sb.append(",");
            }
            sb = new StringBuilder(sb.substring(0, sb.length() - 1));
            sb.append(")");
            inValueSql = sb.toString();
        }
        return inValueSql;
    }

    private String getBetweenSql() {
        StringBuilder sb = new StringBuilder();
        sb.append(" between ");
        String dbType = getDbType();
        if (this.value instanceof List) {
            Object[] objList = ((List<?>) value).toArray();
            for (int i = 0; i < objList.length; i++) {
                Object obj = objList[i];
                if (i == 1) {
                    sb.append(" and ");

                }
                if (obj instanceof LocalDateTime) {
                    String dateString = this.dateTimeFormat(obj);
                    if (SQLConst.DB_ORACLE.equals(dbType)) {
                        sb.append("TO_DATE(substr('").append(dateString).append("',1,19),'yyyy-mm-dd hh24:mi:ss')");
                    } else {
                        sb.append("'").append(dateString).append("'");
                    }
                } else {
                    String dataStr = obj.toString();
                    if (dataStr.endsWith("Z")) {
                        try {
                            dataStr = dataStr.replace("Z", " UTC");//UTC是本地时间
                            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                            LocalDateTime date = LocalDateTime.parse(dataStr, format);
                            dataStr = this.dateTimeFormat(date);
                        } catch (Exception ignored) {}
                    }
                    if (SQLConst.DB_ORACLE.equals(dbType)) {
                        sb.append("TO_DATE(substr('").append(dataStr).append("',1,19),'yyyy-mm-dd hh24:mi:ss')");
                    } else {
                        sb.append("'").append(dataStr).append("'");
                    }
                }
            }
        }
        sb.append(" ");
        return sb.toString();
    }

    /**
     * 当in语句中包含元素大于1000个时的处理
     *
     * @param sql
     * @param strList
     * @return
     */
    private String getOutLimitInSql(String sql, String[] strList) {
        int index = 0;
        int times = 0;
        int i = 0;
        int len = strList.length;
        String field = sql;
        String[] newValue = new String[1000];
        StringBuilder newSql = new StringBuilder();
        newSql.append(" ( ");
        for (String str : strList) {
            newValue[index] = str;
            index++;
            i++;
            if (index % 1000 == 0) {
                if (times > 0) {
                    newSql.append(" or ");
                }
                times++;
                newSql.append(" ");
                newSql.append(field);
                newSql.append(" in ");
                newSql.append("(");
                newSql.append(join(newValue));
                newSql.append(")");
                newSql.append(" ");
                int size = len - 1000 * times;
                newValue = size >= 1000 ? new String[1000] : new String[size];
                index = 0;
            } else {
                if (i == len) {
                    if (times > 0) {
                        newSql.append(" or ");
                    }
                    times++;
                    newSql.append(" ");
                    newSql.append(field);
                    newSql.append(" in ");
                    newSql.append("(");
                    newSql.append(join(newValue));
                    newSql.append(")");
                    newSql.append(" ");
                }
            }
        }
        newSql.append(" ) ");
        return newSql.toString();
    }

    private String getDbType() {

        SqlSessionTemplate sqlSessionTemplate = AppUtil.getBean(SqlSessionTemplate.class);

        Objects.requireNonNull(sqlSessionTemplate, "SqlSessionTemplate cannot be null!");

        return sqlSessionTemplate.getConfiguration().getDatabaseId();
    }

    private String dateTimeFormat(Object obj) {
        if (obj == null) {
            return "";
        }
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format((TemporalAccessor) obj);
    }

    private String join(String[] arr) {
        if (arr == null || arr.length == 0) {
            return "";
        }
        return String.join(",", arr);
    }
}
