package com.example.model;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Mateusz on 2016-07-28.
 */

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
