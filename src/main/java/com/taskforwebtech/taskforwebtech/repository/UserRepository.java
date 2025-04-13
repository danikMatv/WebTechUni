package com.taskforwebtech.taskforwebtech.repository;

import com.taskforwebtech.taskforwebtech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findUserById(Long id);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u")
    List<User> getAll();
}
