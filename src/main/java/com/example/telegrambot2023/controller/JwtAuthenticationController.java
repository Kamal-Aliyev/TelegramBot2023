package com.example.telegrambot2023.controller;

import com.example.telegrambot2023.dto.AfterSignInResponseDto;
import com.example.telegrambot2023.dto.SignUpDto;
import com.example.telegrambot2023.entity.UserEntity;
import com.example.telegrambot2023.model.JwtRequest;
import com.example.telegrambot2023.repository.UserRepo;
import com.example.telegrambot2023.securityconfig.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepo userRepo;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity signIn(@RequestBody JwtRequest request)
            throws Exception {

        authenticate(request.getEmail(), request.getPassword());
        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(request.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        UserEntity user = userRepo.findUsersEntityByEmail(request.getEmail());
        if (user == null){
            return ResponseEntity.ok("Please sign up first !");
        }else {
            AfterSignInResponseDto build = AfterSignInResponseDto.builder()
                    .token(token)
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
            return ResponseEntity.ok(build);
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity signUp(@RequestBody SignUpDto dto) {

        UserEntity entity = userRepo.findUsersEntityByEmail(dto.getEmail());
        if (entity == null) {
            UserEntity userEntity = UserEntity.builder()
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .role(dto.getRole())
                    .build();
            userRepo.save(userEntity);
            return ResponseEntity.ok("You signed!");
        } else
            return ResponseEntity.ok("This account already exist in our DB!");

    }


    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
