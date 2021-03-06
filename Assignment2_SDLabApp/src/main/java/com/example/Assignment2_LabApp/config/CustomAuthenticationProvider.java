package com.example.Assignment2_LabApp.config;

import com.example.Assignment2_LabApp.controller.LoginController;
import com.example.Assignment2_LabApp.model.login.LoginModel;
import com.example.Assignment2_LabApp.model.login.Role;
import com.example.Assignment2_LabApp.service.IUserService;
import com.example.Assignment2_LabApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    //private LoginController loginController;

    @Autowired
    IUserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            Role role = userService.login(username, password);

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString()));
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(username, password, authorities);
            return token;
        }catch(LoginException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
