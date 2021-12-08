package com.shooterj;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.shooterj.util.JsonUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * 用于获取用户信息
 */
public class AuthenticationUtil {

    /** 当前用户的主组织编码 */
    public final static String CURRENT_USER_MAIN_ORG_CODE = "CURRENT_USER_MAIN_ORG_CODE";

    /** 当前用户的主组织等级 */
    public final static String CURRENT_USER_MAIN_ORG_GRADE = "CURRENT_USER_MAIN_ORG_GRADE";

    /** 当前用户的主组织维度id */
    public final static String CURRENT_USER_MAIN_ORG_DEM_ID = "CURRENT_USER_MAIN_ORG_DEM_ID";
    /**
     * 当前用户的主组织
     */
    public final static String CURRENT_USER_MAIN_ORGID = "CURRENT_USER_MAIN_ORGID";
    /**
     * 当前用户的组织idS 1,2,3
     */
    public final static String CURRENT_USER_ORGIDS = "CURRENT_USER_ORGIDS";
    /**
     * 当前用户的组织codes 1,2,3
     */
    public final static String CURRENT_USER_ORG_CODES = "CURRENT_USER_ORG_CODES";

    /**
     * 当前用户的组织idS 1,2,3 以及下级组织id
     */
    public final static String CURRENT_USER_SUB_ORGIDS = "CURRENT_USER_SUB_ORGIDS";

    /**
     * 当前用户的组织codes   以及下级组织code
     */
    public final static String CURRENT_USER_SUB_CODES = "CURRENT_USER_SUB_CODES";

    // 用户信息的线程变量
    private static ThreadLocal<JsonNode> userThreadLocal = new ThreadLocal<>();

    // security认证对象的线程变量 Authentication
    private static ThreadLocal<Authentication> authenticationThreadLocal = new ThreadLocal<>();

    // 数据过滤的线程变量
    private static ThreadLocal<String[]> msIdsThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<Map<String, Object>> mapThreadLocal = new ThreadLocal<>();

    public static void setMapThreadLocal(Map<String, Object> map) {
        mapThreadLocal.set(map);
    }

    public static Map<String, Object> getMapThreadLocal() {
        Map<String, Object> resultMap = mapThreadLocal.get();
        if (resultMap == null) {
            resultMap = new HashMap<>();
        }
        return resultMap;
    }

    public static void removeMapThreadLocal() {
        mapThreadLocal.remove();
    }

    public static void setMsIdsThreadLocal(String[] msIds) {
        msIdsThreadLocal.set(msIds);
    }

    public static String[] getMsIdsThreadLocal() {
        return msIdsThreadLocal.get();
    }

    public static void removeMsIdsThreadLocal() {
        msIdsThreadLocal.remove();
    }

    public static JsonNode getUserThreadLocal() {
        JsonNode jsonNode = userThreadLocal.get();
        if (jsonNode == null) {
            return JsonUtil.getMapper().createObjectNode();
        }
        return jsonNode;
    }

    public static void setAuthentication(Authentication authentication) {
        authenticationThreadLocal.set(authentication);

        Object principal = authentication.getPrincipal();
        try {
            if (principal instanceof UserDetails) {
                UserDetails ud = (UserDetails) principal;
                String json = JSON.toJSONString(ud);
                JsonNode jsonNode = JsonUtil.toJsonNode(json);
                userThreadLocal.set(jsonNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public static String getCurrentUserId() {
        JsonNode jsonNode = getUserThreadLocal().get("customerId");
        if (jsonNode == null || !jsonNode.isTextual()) {
            return null;
        }
        return jsonNode.asText();
    }

    /**
     * 获取当前用户的名称
     *
     * @return
     */
    public static String getCurrentUserFullname() {
        JsonNode jsonNode = getUserThreadLocal().get("customerName");
        if (jsonNode == null || !jsonNode.isTextual()) {
            return null;
        }
        return jsonNode.asText();
    }

    /**
     * 获取当前用户的账号信息
     *
     * @return
     */
    public static String getCurrentUsername() {
        JsonNode jsonNode = getUserThreadLocal().get("customerId");
        if (jsonNode == null || !jsonNode.isTextual()) { return null; }
        return jsonNode.asText();
    }


    /**
     * 获取当前用户的员工编码
     *
     * @return
     */
    public static String getCurrentUserCode() {
        JsonNode jsonNode = getUserThreadLocal().get("userCode");
        if (jsonNode == null || !jsonNode.isTextual()) { return null; }
        return jsonNode.asText();
    }

    /**
     * 获取当前用户的主组织
     *
     * @return
     */
    public static String getCurrentUserMainOrgId() {
        JsonNode jsonNode = getUserThreadLocal().get("attributes");
        if (jsonNode != null && jsonNode.has(CURRENT_USER_MAIN_ORGID)) {
            return jsonNode.get(CURRENT_USER_MAIN_ORGID).asText();
        }
        return null;
    }

    /**
     * 获取当前用户所有组织 1,2,3
     *
     * @return
     */
    public static String getCurrentUserOrgIds() {
        JsonNode jsonNode = getUserThreadLocal().get("attributes");
        if (jsonNode != null && jsonNode.has(CURRENT_USER_ORGIDS)) {
            return jsonNode.get(CURRENT_USER_ORGIDS).asText();
        }
        return null;
    }

    /**
     * 获取当前用户所有组织 1,2,3以及下级组织
     *
     * @return
     */
    public static String getCurrentUserSubOrgIds() {
        JsonNode jsonNode = getUserThreadLocal().get("attributes");
        if (jsonNode != null && jsonNode.has(CURRENT_USER_SUB_ORGIDS)) {
            return jsonNode.get(CURRENT_USER_SUB_ORGIDS).asText();
        }
        return null;
    }

/*****************************************************************************************/
    /**
     * code获取当前用户所有组织 1,2,3
     *
     * @return
     */
    public static String getCurrentUserOrgCodes() {
        JsonNode jsonNode = getUserThreadLocal().get("attributes");
        if (jsonNode != null && jsonNode.has(CURRENT_USER_ORG_CODES)) {
            return jsonNode.get(CURRENT_USER_ORG_CODES).asText();
        }
        return null;
    }

    /**
     * code获取当前用户所有组织 1,2,3以及下级组织
     *
     * @return
     */
    public static String getCurrentUserSubOrgCodes() {
        JsonNode jsonNode = getUserThreadLocal().get("attributes");
        if (jsonNode != null && jsonNode.has(CURRENT_USER_SUB_CODES)) {
            return jsonNode.get(CURRENT_USER_SUB_CODES).asText();
        }
        return null;
    }
/*****************************************************************************************/

    /**
     * 获取当前用户具有的角色别名
     *
     * @return
     */
    public static Set<String> getCurrentUserRolesAlias() {
        Authentication authentication = authenticationThreadLocal.get();
        Set<String> set = new HashSet<>();
        if (authentication == null) {
            return set;
        }
        @SuppressWarnings("unchecked")
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
        for (SimpleGrantedAuthority simpleGrantedAuthority : authorities) {
            set.add(simpleGrantedAuthority.getAuthority());
        }
        return set;
    }

    /**
     *
     */
    public static void removeAll() {
        userThreadLocal.remove();
        authenticationThreadLocal.remove();
    }

    /**
     * 判断当前是否为匿名请求
     *
     * @param authentication
     * @return
     */
    public static boolean isAnonymous(Authentication authentication) {
        if (authentication == null) { return true; }
        if (authentication instanceof AnonymousAuthenticationToken) { return true; }
        return false;
    }
}
