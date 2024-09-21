package com.example.iotservice.repositories;

import com.example.iotservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByName(String username);
    User findByEmail(String email);
}
