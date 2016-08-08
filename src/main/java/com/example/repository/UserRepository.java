package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Mateusz on 2016-07-28.
 */

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);
}
