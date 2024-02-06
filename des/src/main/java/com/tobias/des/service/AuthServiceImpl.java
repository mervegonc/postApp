package com.tobias.des.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tobias.des.dto.LoginDto;
import com.tobias.des.dto.SignupDto;
import com.tobias.des.entity.Role;
import com.tobias.des.entity.User;
import com.tobias.des.jwt.JwtTokenProvider;
import com.tobias.des.repository.RoleRepository;
import com.tobias.des.repository.UserRepository;

@Service

public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;

	private JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
			JwtTokenProvider jwtTokenProvider) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String login(LoginDto loginDto) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
// add debug
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// try to delete 48

		String token = jwtTokenProvider.generateToken(authentication);

		return token;
	}

	@Override
	public void signup(SignupDto signupDto) {
		User user = new User();
		user.setName(signupDto.getName());
		user.setUsername(signupDto.getUsername());
		user.setEmail(signupDto.getEmail());
		user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

		Role userRole = roleRepository.findByName("USER");
		Set<Role> roles = new HashSet<>();
		roles.add(userRole);
		user.setRoles(roles);

		userRepository.save(user);
	}

	@Override
	public void signupAndAssignRole(SignupDto signupDto, String roleName) {
		User user = new User();
		user.setName(signupDto.getName());
		user.setUsername(signupDto.getUsername());
		user.setEmail(signupDto.getEmail());
		user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

		Role userRole = roleRepository.findByName(roleName);
		if (userRole == null) {
			throw new RuntimeException("Role not found: " + roleName);
		}

		Set<Role> roles = new HashSet<>();
		roles.add(userRole);
		user.setRoles(roles);

		userRepository.save(user);
	}

}
