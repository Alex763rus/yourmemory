package com.example.yourmemory.model.jpa;

import com.example.yourmemory.enums.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();

    List<User> findByUserRoleIn(List<UserRole> userRoles);
}
