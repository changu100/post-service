package com.example.sessionpostservice.user.repository

import com.example.sessionpostservice.user.repository.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}