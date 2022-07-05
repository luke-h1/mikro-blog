package com.mikro.blog.controller;

import ch.qos.logback.core.net.SMTPAppenderBase;
import com.mikro.blog.entity.Role;
import com.mikro.blog.entity.User;
import com.mikro.blog.payload.JWTAuthResponse;
import com.mikro.blog.payload.LoginDto;
import com.mikro.blog.payload.SignupDto;
import com.mikro.blog.repository.RoleRepository;
import com.mikro.blog.repository.UserRepository;
import com.mikro.blog.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@Api(value = "Auth Controller that exposes signin & signup endpoints")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;


  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;


  @Autowired
  private JwtTokenProvider tokenProvider;

  @ApiOperation(value = "Signin endpoint", response = JWTAuthResponse.class)
  @PostMapping("/signin")
  public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    // get token from token provider class
    String token = tokenProvider.generateToken(authentication);

    return ResponseEntity.ok(new JWTAuthResponse(token));
  }

  @ApiOperation(value = "Signup endpoint")
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignupDto signupDto) {
    // add check for username exists in a DB
    if(userRepository.existsByUsername(signupDto.getUsername())){
      return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
    }
    // add check for email exists in DB
    if(userRepository.existsByEmail(signupDto.getEmail())){
      return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
    }

    // create user object
    User user = new User();
    user.setFirstName(signupDto.getFirstName());
    user.setLastName(signupDto.getLastName());
    user.setUsername(signupDto.getUsername());
    user.setEmail(signupDto.getEmail());
    user.setPassword(signupDto.getPassword());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    Optional<Role> roles = roleRepository.findByName("ROLE_USER"); // get user role if exists and apply it to user by default

    // check if role exists
    if(roles.isPresent()){
      user.setRoles(Collections.singleton(roles.get()));
    } else {
      System.out.println("role not found:" + roles);

    }
    userRepository.save(user);
    return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
  }
}
