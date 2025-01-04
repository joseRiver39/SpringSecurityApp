package com.App.service;

import com.App.persistence.entity.UserEntity;
import com.App.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author ANTONIO
 */
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("El Usuario " + username + "no existe"));
                
        
        return null ;
    }

}
