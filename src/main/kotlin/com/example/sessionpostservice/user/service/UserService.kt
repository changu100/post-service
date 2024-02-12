package com.example.sessionpostservice.user.service

import com.example.sessionpostservice.user.repository.entity.User

interface UserService {
    fun signUp(email: String, password: String, name: String): Boolean

    fun signIn(email: String, password: String): String

    fun createAdminCode(userId: Long, userRole: User.UserRole, inviteUserEmail: String): String

    fun participateAdmin(userId: Long, inviteCode: String)
}