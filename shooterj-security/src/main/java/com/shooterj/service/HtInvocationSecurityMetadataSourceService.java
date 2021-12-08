package com.shooterj.service;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

@Service
public class HtInvocationSecurityMetadataSourceService implements
        FilterInvocationSecurityMetadataSource {

    // security认证对象的线程变量 Authentication
    private static ThreadLocal<HashMap<String, Collection<ConfigAttribute>>> mapThreadLocal = new ThreadLocal<HashMap<String, Collection<ConfigAttribute>>>();

//
//    @Resource
//    private MethodAuthService methodAuthService;


    /**
     * 加载权限表中所有权限
     * <p>
     * 同一个方法可能多个角色都具有访问权限
     */
    public void loadResourceDefine() {
        HashMap<String, Collection<ConfigAttribute>> map = getMapThreadLocal();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;


    }

    /**
     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
     * 返回空 ， 则不用经过 decide 方法 判断权限， 直接具有访问权限了
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        loadResourceDefine();
        // 从线程中获取保证线程安全
        HashMap<String, Collection<ConfigAttribute>> map = getMapThreadLocal();
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    private static HashMap<String, Collection<ConfigAttribute>> getMapThreadLocal() {
        HashMap<String, Collection<ConfigAttribute>> hashMap = mapThreadLocal.get();
        if (hashMap == null) {
            hashMap = new HashMap<String, Collection<ConfigAttribute>>();
            mapThreadLocal.set(hashMap);
        }
        return hashMap;
    }
}