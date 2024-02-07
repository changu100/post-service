package com.example.sessionpostservice.user.service


interface UserService {
    fun signUp(email: String, password: String, name: String): Boolean
}