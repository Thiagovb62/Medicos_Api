package com.example.demo.Repository;

import com.example.demo.Domain.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    UserDetails findByEmail(String username);
}
