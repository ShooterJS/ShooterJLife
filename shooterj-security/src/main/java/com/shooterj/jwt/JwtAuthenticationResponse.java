package com.shooterj.jwt;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    private final String username;

    private final String account;

    private final String userId;

    private final String email;

    private final String mobile;

    private final String companyStation;

    private final String photo;

    private final String gender;





    public JwtAuthenticationResponse(String token, String username, String account, String userId, String email, String mobile, String companyStation, String photo, String gender) {
        this.token = token;
        this.username = username;
        this.account = account;
        this.userId = userId;
        this.email = email;
        this.mobile = mobile;
        this.companyStation = companyStation;
        this.photo = photo;
        this.gender = gender;
    }

    public String getToken() {
        return this.token;
    }

    public String getUsername() {
        return this.username;
    }

    public String getAccount() {
        return account;
    }

    public String getUserId() {
        return userId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPhoto() {
        return photo;
    }

    public String getCompanyStation() {
        return companyStation;
    }

    public String getGender() {
        return gender;
    }
}
