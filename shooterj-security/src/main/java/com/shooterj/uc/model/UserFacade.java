package com.shooterj.uc.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Author: FangGan
 * @Email: libayiv@163.com
 * @Date: 2020/12/05 14:02
 **/
public class UserFacade implements UserDetails, Serializable {

    private static final long serialVersionUID = -3941375299298937698L;
    /** 用户ID */
    protected String userId;
    /** 用户名 */
    protected String username;
    /** 密码 */
    protected String password;
    /** 邮箱 */
    protected String email;
    /** 手机号码 */
    protected String mobile;
    /** 微信OpenId */
    protected String openId;
    /** 微信号 */
    protected String wechat;
    /** 用户授权信息 */
    private Collection<SimpleGrantedAuthority> authorities;


    /** 客户ID */
    protected String customerId;
    /** 客户名 */
    protected String customerName;

    /** 用户编号 */
    protected String userCode;


    public void setAuthorities(Collection<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getOpenId() {
        return openId;
    }

    public String getWechat() {
        return wechat;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getUserCode() {
        return userCode;
    }
}
