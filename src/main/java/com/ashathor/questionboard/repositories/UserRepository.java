package com.ashathor.questionboard.repositories;

import com.ashathor.questionboard.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This is the data access layer in the application. Spring gives us access to predefined SQL based on implemented methods in this interface,
 * */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
