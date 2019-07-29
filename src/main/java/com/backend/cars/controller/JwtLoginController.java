package com.backend.cars.controller;

import com.backend.cars.jwtsecurity.JwtTokenUtil;
import com.backend.cars.model.User;
import com.backend.cars.service.CustomUserDetailService;
import com.backend.cars.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@RequestMapping("/user")
public class JwtLoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailService userDetailService;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody User user){
        String token = new String();
        HashMap<String, String> response = new HashMap<>();
        try {
            token = login(user.getEmail(), user.getPassword());
        }catch (Exception e) {
            return ResponseEntity.status(401).body("Authentication failed!");
        }
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/loginAsAdmin")
    public ResponseEntity<?> userLoginAsAdmin(@RequestBody User user){
        String token = new String();
        HashMap<String, String> response = new HashMap<>();
        try {
            token = login(user.getEmail(), user.getPassword());
        }catch (Exception e){
            ResponseEntity.status(401).body("Authentication failed!");
        }

        final CustomUserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());
        if(!userDetails.getAuthorities().contains("ROLE_ADMIN")){
            ResponseEntity.status(403).body("User does not have ADMIN role in system!");
        }

        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    private String login(String email, String password) throws Exception{
        authenticate(email, password);
        final CustomUserDetails userDetails = userDetailService.loadUserByUsername(email);
        return jwtTokenUtil.generateToken(userDetails);
    }

    private void authenticate(String email, String password) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
