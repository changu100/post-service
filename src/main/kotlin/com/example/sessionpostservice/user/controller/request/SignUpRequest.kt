package com.example.sessionpostservice.user.controller.request

data class SignUpRequest (
    val email: String,
    val password: String,
    val name: String,
)