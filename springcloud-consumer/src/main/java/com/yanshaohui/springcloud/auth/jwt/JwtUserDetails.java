package com.yanshaohui.springcloud.auth.jwt;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final String loginAccount;
    private final String username;
    private final String password;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Date tokenCreateDate;
    private final Date lastPasswordResetDate;
    
    public JwtUserDetails(
            String loginAccount,
            String username,
            String password,
            String email,
            List<String> authorities,
            Date tokenCreateDate,
            Date lastPasswordResetDate) {
        this.loginAccount = loginAccount;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        this.tokenCreateDate = tokenCreateDate;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    
    public String getLoginAccount() {
        return loginAccount;
    }

    
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    
    public String getEmail() {
		return email;
	}
    
    
    public Date getTokenCreateDate() {
		return tokenCreateDate;
	}

	
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
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
}