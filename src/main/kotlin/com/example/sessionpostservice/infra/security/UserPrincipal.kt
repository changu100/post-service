package com.example.sessionpostservice.infra.security

import com.example.sessionpostservice.user.repository.entity.User

data class UserPrincipal(
    val id: Long,
    val email: String,
    val role: User.UserRole,
)