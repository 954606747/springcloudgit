package com.yanshaohui.springcloud.auth.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.yanshaohui.springcloud.auth.security.WebSecurityConfig;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header:Authorization}")
    private String tokenHeader;

    @Value("${jwt.tokenHead:Bearer }")
    private String tokenHead;

    private List<RequestMatcher> matchers;

	public JwtAuthenticationTokenFilter() {
		matchers = new ArrayList<RequestMatcher>();
		for (String pattern : WebSecurityConfig.ALLOWGETURL) {
			matchers.add(new AntPathRequestMatcher(pattern, "GET"));
		}
		for (String pattern : WebSecurityConfig.ALLOWPOSTURL) {
			matchers.add(new AntPathRequestMatcher(pattern, "POST"));
		}
	}
	
	private boolean urlMatchers(HttpServletRequest request){
		return matchers.stream().anyMatch((a)->a.matches(request));
	}
	
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		boolean filterIt = urlMatchers(request);
		return filterIt;
	}
    
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	
    	String authHeader = request.getHeader(this.tokenHeader);
    	if (authHeader != null && authHeader.startsWith(tokenHead)){
    		final String authToken = authHeader.substring(tokenHead.length()); 
    		UserDetails userDetails = jwtTokenUtil.getAuthFromToken(authToken);
    		if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
    			if (jwtTokenUtil.validateToken(authToken, userDetails)) {
    				 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                             userDetails, null, userDetails.getAuthorities());
                     authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                     SecurityContextHolder.getContext().setAuthentication(authentication);
    			}else{
    				//token验证过期 
            		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    writer.write("安全标识过期");
                    writer.flush();
    			}
    		}else{
    			//token无效
        		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write("安全标识无效");
                writer.flush();
                return;
    		}
    	}else{
    		//没有安全头
    		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("缺少安全认证标识");
            writer.flush();
            return;
    	}
    	chain.doFilter(request, response);
    }
}