package com.microservice.gateway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.microservice.gateway.persistence.entity.PermissionEntity;
import com.microservice.gateway.persistence.entity.RoleEnum;
import com.microservice.gateway.persistence.entity.UserEntity;
import com.microservice.gateway.persistence.entity.RolesEntity;
import com.microservice.gateway.persistence.repository.UserRepository;

import java.util.List;
import java.util.Set;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceGatewayApplication.class, args);
	}

	@Bean
        CommandLineRunner init(UserRepository userRepository) {
                return args -> {
                /* Create PERMISSIONS */
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

                /* Create ROLES */
                RolesEntity roleAdmin = RolesEntity.builder()
                        .roleEnum(RoleEnum.ADMIN)
                        .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                        .build();

                RolesEntity roleUser = RolesEntity.builder()
                        .roleEnum(RoleEnum.USER)
                        .permissionList(Set.of(readPermission))
                        .build();


                /* CREATE USERS */
                UserEntity userSantiago = UserEntity.builder()
                        .username("santiago")
                        .password("$2a$10$/a1r3F9pwZXbZ1JYj9NbS.0RFrkuA5i/6OIEozXTEVcmylBYylpzO")
                        .isEnabled(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .roles(Set.of(roleAdmin))
                        .build();

                UserEntity userDaniel = UserEntity.builder()
                        .username("daniel")
                        .password("$2a$10$/a1r3F9pwZXbZ1JYj9NbS.0RFrkuA5i/6OIEozXTEVcmylBYylpzO")
                        .isEnabled(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .roles(Set.of(roleUser))
                        .build();

                userRepository.saveAll(List.of(userSantiago, userDaniel));
                };
        }
}
