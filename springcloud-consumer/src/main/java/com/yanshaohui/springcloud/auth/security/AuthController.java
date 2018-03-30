package com.yanshaohui.springcloud.auth.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Value("${jwt.header:Authorization}")
    private String tokenHeader;

    @Value("${jwt.tokenHead:Bearer }")
    private String tokenHead;
    
	@Autowired
	private AuthService authService;
	
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody AuthLoginUser loginUser, HttpServletResponse response) throws AuthenticationException {
        String token = authService.login(loginUser.getLoginAccount(), loginUser.getPassword());
        response.addHeader(tokenHeader, tokenHead + token);
        return "success";
    }
}
