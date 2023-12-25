package com.gm2.pvd.security;

import com.gm2.pvd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.gm2.pvd.Exception.PasswordNotFoundException;
import com.gm2.pvd.dto.LoginDTO;
import com.gm2.pvd.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUserName(username);
        if(user == null) {
        	throw new UsernameNotFoundException("Login inválido");
        }
        
        return new UserPrincipal(user);
    }
    
    public void verifyUserCredentials(LoginDTO login) {
    	UserDetails user = loadUserByUsername(login.getUserName());
    	
    	boolean passwordIsTheSame = SecurityConfig.passwordEncoder()
    			.matches(login.getPassword(), user.getPassword());
    	
    	if(!passwordIsTheSame) {
    		throw new PasswordNotFoundException("Senha incorreta");
    	}
    }
}