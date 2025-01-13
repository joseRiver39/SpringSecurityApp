package com.App.service;

import com.App.controller.dto.AuthCreateUserRequest;
import com.App.controller.dto.AuthLoginRequest;
import com.App.controller.dto.AuthResponse;
import com.App.persistence.entity.RoleEntity;
import com.App.persistence.entity.UserEntity;
import com.App.persistence.repository.RoleRepository;
import com.App.persistence.repository.UserRepository;
import com.App.util.JwtUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author ANTONIO
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder PasswordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El Usuario " + username + "no existe"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccounNoLocked(),
                authorityList);

    }
    
    public  AuthResponse loginUser(AuthLoginRequest authLoginRequest){
    
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();
        
        
        Authentication authentication = this.authenticate(username,password);
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String accessToken = jwtUtils.createToken(authentication);
        
        AuthResponse authResponse = new  AuthResponse(username, "User loged succesfuly", accessToken, true);
        return  authResponse;
    }

    public Authentication authenticate(String username, String password){
    
    UserDetails userDetails = this.loadUserByUsername(username);
    if(userDetails == null){
       return  (Authentication) new BadCredentialsException("Invalid User or Password.");
    }
    
    if(!PasswordEncoder.matches(password,userDetails.getPassword())){
     throw new BadCredentialsException("Invalid password");
    }
    
    
    return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(),userDetails.getAuthorities());
  
    
        }
    
    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest){
    
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();
        
        List<String> roleRequest = authCreateUserRequest.roleRequest().roleListName();
        
        Set<RoleEntity> roleEntitySet = roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());
        
        if (roleEntitySet.isEmpty()) {
            throw new  IllegalArgumentException("El Rol especificado no existe");     
        }
        
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(PasswordEncoder.encode(password))
                .roles(roleEntitySet)
                .isEnabled(true)
                .accounNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();
        UserEntity userCreated = userRepository.save(userEntity);
        ArrayList<SimpleGrantedAuthority> authorityList = new  ArrayList<>();
        
        userCreated.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        
        userCreated.getRoles()
                .stream()
                .flatMap(role-> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));
        
        Authentication authentication =  new UsernamePasswordAuthenticationToken(userCreated.getUsername(),userCreated.getPassword(),authorityList);
       
        String accessToken = jwtUtils.createToken(authentication);
        
        AuthResponse authResponse =  new AuthResponse(userCreated.getUsername(), "Usuario creado satisfactoriamente", accessToken, true);
        
        return authResponse;
    }
}
