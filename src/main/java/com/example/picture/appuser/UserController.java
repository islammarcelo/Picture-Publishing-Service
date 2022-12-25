package com.example.picture.appuser;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api")
public class UserController {
    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerNewUser(@RequestParam("email") String email,
                                                        @RequestParam("password") String password){
        userService.register(email,password);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserResponse("User created successfully"));
    }

    @PostMapping("/save/role")
    public void saveRole(@RequestBody Role role){
        userService.saveRole(role);
    }

    @PostMapping("/add/roleToUser")
    public ResponseEntity<UserResponse> addRoleToUser(@RequestParam("role") String role,
                              @RequestParam("email") String email){

        userService.addRoleToUser(role,email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserResponse("Created the role successfully"));
    }
}


