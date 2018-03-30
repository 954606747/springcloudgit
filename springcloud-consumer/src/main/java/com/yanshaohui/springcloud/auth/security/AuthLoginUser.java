package com.yanshaohui.springcloud.auth.security;

public class AuthLoginUser {
    private String loginAccount;
    private String password;

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
