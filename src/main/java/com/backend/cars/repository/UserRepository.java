package com.backend.cars.repository;

import com.backend.cars.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.username = :name")
    List<User> findByUsername(@Param("name") String name);

    @Query("select u from User u where u.email = :email")
    List<User> findByEmail(@Param("email") String email);
}
