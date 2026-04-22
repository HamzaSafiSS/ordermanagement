package com.hamza.ordermanagementsystem.repository;

import com.hamza.ordermanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}