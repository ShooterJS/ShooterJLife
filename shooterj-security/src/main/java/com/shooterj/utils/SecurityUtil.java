package com.shooterj.utils;

import com.shooterj.service.CustomPasswordEncoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * spring security 工具类
 *
 * @author heyifan
 * @company 广州宏天软件股份有限公司
 * @email heyf@jee-soft.cn
 * @date 2018年7月6日
 */
public class SecurityUtil {
    private static SessionRegistry sessionRegistry;


    /**
     * 登录系统。
     *
     * @param request
     * @param userName    用户名
     * @param pwd         密码
     * @param isIgnorePwd 是否忽略密码。
     * @return
     */
    public static Authentication login(HttpServletRequest request, String userName, String pwd, boolean isIgnorePwd) {
        AuthenticationManager authenticationManager = AppUtil.getBean(AuthenticationManager.class);
        CustomPasswordEncoder.setIgnore(isIgnorePwd);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, pwd);
        authRequest.setDetails(new WebAuthenticationDetails(request));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = authenticationManager.authenticate(authRequest);
        securityContext.setAuthentication(auth);
        CustomPasswordEncoder.removeIgnore();
        return auth;
    }

    /**
     * 踢出用户
     *
     * @param account 账号
     */
    public static void kickoutUser(String account) {
        if (StringUtils.isEmpty(account)) return;
        if (sessionRegistry == null) {
            sessionRegistry = AppUtil.getBean(SessionRegistry.class);
        }
        List<Object> objects = sessionRegistry.getAllPrincipals();
        for (Object o : objects) {
            User user = (User) o;
            if (account.equals(user.getUsername())) {
                List<SessionInformation> sis = sessionRegistry.getAllSessions(o, false);
                if (sis != null) {
                    for (SessionInformation si : sis) {
                        si.expireNow();
                    }
                }
            }
        }
    }
}
