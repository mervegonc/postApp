package com.tobias.des.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tobias.des.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail2);

	@Override
	User save(User user);

	// Kullanıcı adını kontrol etmek için metot
	boolean existsByUsername(String username);

	User findByUsername(String username);

}
