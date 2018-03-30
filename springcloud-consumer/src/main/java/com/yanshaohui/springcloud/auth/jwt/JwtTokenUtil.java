package com.yanshaohui.springcloud.auth.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    //用户ID
    private static final String CLAIM_KEY_USERID = "sub";
    //token生成时间
    private static final String CLAIM_KEY_CREATED = "crt";
    //用户名
    private static final String CLAIM_KEY_USERNAME = "nm";
    //用户邮件
    private static final String CLAIM_KEY_MAIL = "mail";
    //用户权限
    private static final String CLAIM_KEY_AUTH = "auth";
    //末次修改密码时间
    private static final String CLAIM_KEY_PSWRESETDATE = "rst";

    @Value("${jwt.secret:SpringCloudWithJwt}")
    private String secret;

    @Value("${jwt.expiration:604800}")
    private Long expiration;
    
	public <T> T getAuthFromToken(String token,String claimsKey,Class<T> cls){
        try {
            final Claims claims = getClaimsFromToken(token);
            return claims.get(claimsKey,cls);
        } catch (Exception e) {
            return null;
        }
    }

	/**
	 * 根据token解析出可信赖的UserDetails对象
	 * @param token
	 * @return
	 */
    public JwtUserDetails getAuthFromToken(String token){
        try {
            final Claims claims = getClaimsFromToken(token);
			@SuppressWarnings("unchecked")
			JwtUserDetails user = new JwtUserDetails(
					claims.get(CLAIM_KEY_USERID, String.class),
            		claims.get(CLAIM_KEY_USERNAME, String.class),
            		null,
            		claims.get(CLAIM_KEY_MAIL, String.class),
            		claims.get(CLAIM_KEY_AUTH, List.class),
            		claims.get(CLAIM_KEY_CREATED, Date.class),
            		claims.get(CLAIM_KEY_PSWRESETDATE, Date.class)
            		);
            return user;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 登陆成功时，根据userDetails生成token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        JwtUserDetails jwtuser = (JwtUserDetails) userDetails;
        claims.put(CLAIM_KEY_USERID, jwtuser.getLoginAccount());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_USERNAME, jwtuser.getUsername());
        claims.put(CLAIM_KEY_MAIL, jwtuser.getEmail());
        claims.put(CLAIM_KEY_AUTH, 
        		jwtuser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        claims.put(CLAIM_KEY_PSWRESETDATE, jwtuser.getLastPasswordResetDate());
        return generateToken(claims);
    }
    
    /**
     * 验证token没有过期，且token创建日期晚于末次密码修改日期
     * @param userDetails
     * @param token
     * @return
     */
    public Boolean validateToken(String token,UserDetails userDetails) {
    	JwtUserDetails user = (JwtUserDetails) userDetails;
        return (!isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(user.getTokenCreateDate(), user.getLastPasswordResetDate()));
    }
    
    /**
     * 验证token是否需要更新
     * @param token
     * @param lastPasswordReset
     * @return
     */
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && !isTokenExpired(token);
    }

    /**
     * 更新token，主要是token创建时间及过期时间
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
    
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }
    
    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

}