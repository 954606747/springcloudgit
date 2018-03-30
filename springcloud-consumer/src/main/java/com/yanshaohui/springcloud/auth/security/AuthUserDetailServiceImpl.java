package com.yanshaohui.springcloud.auth.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yanshaohui.springcloud.auth.jwt.JwtUserDetails;
import com.yanshaohui.springcloud.entity.UserEntity;
import com.yanshaohui.springcloud.entity.UserRoleEntity;
import com.yanshaohui.springcloud.repository.UserRepository;

@Service
public class AuthUserDetailServiceImpl implements UserDetailsService{

	@Autowired
    private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String loginAccount) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByLoginAccount(loginAccount);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("{0},用户不存在", loginAccount));
        }
        final UserDetails userDetails = change(user);
        return userDetails;
	}
	
    private UserDetails change(UserEntity user){
    	UserDetails detail = new JwtUserDetails(
    			user.getLoginAccount(), 
    			user.getUserName(), 
    			user.getPassword(), 
    			user.getEmail(), 
    			user.getUserRoles().stream().map(UserRoleEntity::getRoleId).collect(Collectors.toList()), 
    			null, 
    			user.getLastPasswordResetDate());
    	return detail;
    }

}
