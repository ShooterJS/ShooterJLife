package com.shooterj.service;

import com.winway.meetingbase.common.util.AuthenticationUtil;
import com.shooterj.constants.PlatformConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class HtDecisionManager implements AccessDecisionManager {

    @SuppressWarnings("unchecked")
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        if (configAttributes == null || configAttributes.isEmpty()) { return; }

        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication
                .getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            // 超级管理员
            if (PlatformConstants.ROLE_SUPER.equals(grantedAuthority.getAuthority())) {
                return;
            }
        }

        for (ConfigAttribute configAttribute : configAttributes) {
            if (configAttribute == null) {continue;}
            String configVal = configAttribute.toString();
            // 匿名资源允许访问
            if (PlatformConstants.PERMIT_All.equals(configVal)) {
                return;
            }
            // 受权限控制的资源
            else if (PlatformConstants.AUTHENTICATED.equals(configVal)) {
                // 匿名访问时抛出 401异常
                if (AuthenticationUtil.isAnonymous(authentication)) {
                    throw new InsufficientAuthenticationException("需要提供jwt授权码");
                } else {
                    // 如果该资源未加入权限管理列表，则允许访问
                    String attribute = configAttribute.getAttribute();
                    if (StringUtils.isEmpty(attribute)) {
                        return;
                    }
                }
            }
        }

        for (GrantedAuthority grantedAuthority : authorities) {
            for (ConfigAttribute configAttribute : configAttributes) {
                if (grantedAuthority.getAuthority().equals(
                        configAttribute.getAttribute())) {
                    // 有权限
                    return;
                }
            }
        }

        throw new AccessDeniedException("您没有权限， 请联系系统管理员");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
