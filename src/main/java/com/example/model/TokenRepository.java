package com.example.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mateusz on 2016-08-02.
 */

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>{

    Token findByToken(String token);

    Token findByUserId(Long id);
}
