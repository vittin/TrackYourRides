package com.example.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mateusz on 2016-08-02.
 */

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>{

    Token findByTokenId(String token);

    List<Token> findByUserId(Long id);
}
