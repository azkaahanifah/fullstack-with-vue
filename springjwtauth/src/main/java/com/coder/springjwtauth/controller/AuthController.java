package com.coder.springjwtauth.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coder.springjwtauth.model.ERole;
import com.coder.springjwtauth.model.Role;
import com.coder.springjwtauth.model.User;
import com.coder.springjwtauth.payload.JwtResponse;
import com.coder.springjwtauth.payload.MessageResponse;
import com.coder.springjwtauth.payload.request.LoginRequest;
import com.coder.springjwtauth.payload.request.RegisterRequest;
import com.coder.springjwtauth.repository.RoleRepository;
import com.coder.springjwtauth.repository.UserRepository;
import com.coder.springjwtauth.security.impl.UserDetailsImpl;
import com.coder.springjwtauth.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate
				(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetailsImpl.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt, 
												userDetailsImpl.getId(), 
												userDetailsImpl.getUsername(), 
												userDetailsImpl.getEmail(), 
												roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
		if (userRepository.existsByUsername(registerRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		
		if (userRepository.existsByEmail(registerRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in user!"));
		}
		
		/* Create New User Account */
		User user = new User(registerRequest.getUsername(),
								registerRequest.getEmail(),
								passwordEncoder.encode(registerRequest.getPassword()));
		
		Set<String> strRoles = registerRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
					roles.add(adminRole);
					break;
				
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
					roles.add(userRole);
					break;
				}
			});
		}
		
		user.setRoles(roles);
		userRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}
