package com.epolsoft.wtr.security;

import com.epolsoft.wtr.model.User;
import com.epolsoft.wtr.security.jwt.JwtUser;
import com.epolsoft.wtr.security.jwt.JwtUserFactory;
import com.epolsoft.wtr.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LogManager.getLogger(JwtUserDetailsService.class);

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService){
        this.userService=userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user=userService.getUserByName(userName);

        if(user==null){
            throw new UsernameNotFoundException("User with Name="+userName+" not found");
        }

        JwtUser jwtUser= JwtUserFactory.create(user);
        LOGGER.info("In loadUserByUsername; user with userName: "+ userName+" successfully loaded");
        return jwtUser;
    }

}
