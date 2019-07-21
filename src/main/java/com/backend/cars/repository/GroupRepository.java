package com.backend.cars.repository;

import com.backend.cars.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<UserGroup, Integer> {
}
