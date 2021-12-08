package com.shooterj.query;

import com.shooterj.util.AppUtil;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.*;


class FieldConvertUtil {
    /**
     * 实体类属性转换为数据库列名
     *
     * @param property 实体类属性
     * @param clazz    实体类
     * @return 数据库列名
     */
    public static String property2Field(String property, Class<?> clazz) {
        SqlSessionTemplate sqlSessionTemplate = getSqlSessionTemplate();

        Configuration configuration = sqlSessionTemplate.getConfiguration();
        Collection<String> resultMapNames = configuration.getResultMapNames();

        List<ResultMap> resultMaps = new ArrayList<>();

        for (String resultMapName : resultMapNames) {
            if (resultMapName.contains(".")) {
                ResultMap resultMap = configuration.getResultMap(resultMapName);
                if (Objects.equals(resultMap.getType(), clazz)) {
                    resultMaps.add(resultMap);
                }
            }
        }

        resultMaps.sort(Comparator.comparing(resultMap -> resultMap.getId().length()));

        for (ResultMap resultMap : resultMaps) {
            Class<?> type = resultMap.getType();
            if (Objects.equals(type, clazz)) {
                for (ResultMapping resultMapping : resultMap.getPropertyResultMappings()) {
                    if (resultMapping.getProperty().equals(property)) {
                        property = resultMapping.getColumn();
                        return property;
                    }
                }
            }
        }
        return property;
    }

    /**
     * 数据库列名转换为实体类属性
     *
     * @param field 数据库列名
     * @param clazz 实体类
     * @return 实体类属性
     */
    public static String field2Property(String field, Class<?> clazz) {
        String classNameSpace = clazz.getName() + ".";
        SqlSessionTemplate sqlSessionTemplate = getSqlSessionTemplate();

        Configuration configuration = sqlSessionTemplate.getConfiguration();
        Collection<String> resultMapNames = configuration.getResultMapNames();
        for (String name : resultMapNames) {
            if (!name.contains(".")) {
                continue;
            }
            ResultMap resultMap = configuration.getResultMap(name);
            if (resultMap.getId().contains(classNameSpace)) {
                for (ResultMapping resultMapping : resultMap.getPropertyResultMappings()) {
                    if (resultMapping.getColumn().equals(field)) {
                        field = resultMapping.getProperty();
                        return field;
                    }
                }
            }
        }
        return field;
    }

    private static SqlSessionTemplate getSqlSessionTemplate() {

        SqlSessionTemplate sqlSessionTemplate = AppUtil.getBean(SqlSessionTemplate.class);

        Objects.requireNonNull(sqlSessionTemplate, "SqlSessionTemplate cannot be null!");

        return sqlSessionTemplate;
    }
}
