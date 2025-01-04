package com.App;

import com.App.persistence.entity.PermissionEntity;
import com.App.persistence.entity.RoleEntity;
import com.App.persistence.entity.RoleEnum;
import com.App.persistence.entity.UserEntity;
import com.App.persistence.repository.UserRepository;
import java.util.List;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringSecurityAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityAppApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            /*Create Permisions */
            PermissionEntity createPermission = PermissionEntity.builder()
                    .name("CREATE")
                    .build();
            PermissionEntity readPermission = PermissionEntity.builder()
                    .name("READ")
                    .build();
            PermissionEntity updatePermission = PermissionEntity.builder()
                    .name("UPDATE")
                    .build();
            PermissionEntity deletePermission = PermissionEntity.builder()
                    .name("DELETE")
                    .build();
            PermissionEntity refactorPermission = PermissionEntity.builder()
                    .name("REFACTOR")
                    .build();
            /*Create Roles*/
            RoleEntity roleAdmin =RoleEntity.builder()
                    .roleEnum(RoleEnum.ADMIN)
                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                    .build();
            RoleEntity roleUser =RoleEntity.builder()
                    .roleEnum(RoleEnum.USER)
                    .permissionList(Set.of(createPermission, readPermission))
                    .build();
            RoleEntity roleInvited =RoleEntity.builder()
                    .roleEnum(RoleEnum.INVITED)
                    .permissionList(Set.of(readPermission))
                    .build();
            RoleEntity roleDeveloper =RoleEntity.builder()
                    .roleEnum(RoleEnum.DEVELOPER)
                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
                    .build();
            /*Create Users*/
            
            UserEntity userSantiago = UserEntity.builder()
                    .username("Santiago")
                    .password("1234")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accounNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleAdmin))                    
                    .build();
            UserEntity userDaniel = UserEntity.builder()
                    .username("Daniel")
                    .password("1234")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accounNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleUser))                    
                    .build();
            UserEntity userAndres = UserEntity.builder()
                    .username("Andres")
                    .password("1234")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accounNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleInvited))                    
                    .build();
            UserEntity userAnyi = UserEntity.builder()
                    .username("Anyi")
                    .password("1234")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accounNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleDeveloper))                    
                    .build();
            userRepository.saveAll(List.of(userSantiago, userAndres, userDaniel, userAnyi));
        };

    }

}
