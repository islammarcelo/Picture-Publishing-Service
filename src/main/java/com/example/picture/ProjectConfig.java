package com.example.picture;

import com.example.picture.appuser.Role;
import com.example.picture.appuser.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
    @Bean
    CommandLineRunner commandLineRunnerUser(UserService userService){
        return args -> {
            userService.saveRole(new Role(null,"user"));
            userService.saveRole(new Role(null,"admin"));

            userService.register("admin","admin123");

            userService.addRoleToUser("admin","admin");

        };
    }
}
