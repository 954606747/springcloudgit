package com.yanshaohui.springcloud.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yanshaohui.springcloud.auth.jwt.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    
    @Value("${management.context-path:/}")
    private static String manageUrl;
    
    public static final String[] ALLOWGETURL = new String[]{
    		"/h2", //H2数据库管理控制台，临时开启
    		"/h2/**", //H2数据库管理控制台，临时开启
            "/error",
            "/*.html",
            "/webjars/**",
            "/**/favicon.ico",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/**/*.jpg",
            "/**/*.jpeg",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.bmp",
            manageUrl + "**"
    };
    
    public static final String[] ALLOWPOSTURL = new String[]{
    		"/h2", //H2数据库管理控制台，临时开启
    		"/h2/**", //H2数据库管理控制台，临时开启
            "/login",
            manageUrl + "**"
    };
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        ALLOWGETURL
                ).permitAll()
                .antMatchers(
                        HttpMethod.POST,
                        ALLOWPOSTURL
                ).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        

        // 禁用缓存
        httpSecurity.headers().cacheControl();
        httpSecurity.headers().frameOptions().sameOrigin();
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
