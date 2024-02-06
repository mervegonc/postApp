package com.tobias.des.service;

import com.tobias.des.dto.LoginDto;
import com.tobias.des.dto.SignupDto;

public interface AuthService {
	String login(LoginDto loginDto);

	void signup(SignupDto signupDto);

	void signupAndAssignRole(SignupDto signupDto, String roleName);
}
