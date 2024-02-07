package com.example.sessionpostservice.user.controller.request

data class SignInRequest(
    val email: String,
    val password: String
)