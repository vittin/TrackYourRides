package com.example.model;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Mateusz on 2016-07-28.
 */

public interface UserRepository extends CrudRepository<User, Long> {

}
