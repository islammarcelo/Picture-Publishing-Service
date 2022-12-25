package com.example.picture.appuser;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Transactional
@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public void register(String email, String password) {
        AppUser appUser = AppUser.builder().email(email).password(password).build();
        Optional<AppUser> userByEmail = appUserRepo.findRoleByEmail(appUser.getEmail());

        if (userByEmail.isPresent()) {
            throw new IllegalStateException("Email taken :) ");
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUserRepo.save(appUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepo.findRoleByEmail(username).orElseThrow(() -> new IllegalStateException(
                "User with this " + username + " dose not exists"));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(appUser.getEmail(), appUser.getPassword(), authorities);
    }


    public void saveRole(Role role) {
        roleRepo.save(role);
    }

    public void addRoleToUser(String roleName, String email) {
        AppUser appUser = appUserRepo.findRoleByEmail(email).orElseThrow(() -> new IllegalStateException(
                "User with this email " + email + " dose not exists"));
        Role role = roleRepo.findRoleByName(roleName).orElseThrow(() -> new IllegalStateException(
                "Role with this " + roleName + " dose not exists"));
        appUser.getRoles().add(role);


    }



}
