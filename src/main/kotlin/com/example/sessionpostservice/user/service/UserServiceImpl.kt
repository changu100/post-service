package com.example.sessionpostservice.user.service

import com.example.sessionpostservice.user.repository.UserRepository
import com.example.sessionpostservice.user.repository.entity.User
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
): UserService {
    override fun signUp(email: String, password: String, name: String): Boolean {
        try {
            userRepository.save(
                User(
                    email = email,
                    password = password,
                    name = name
                )
            )
            return true
        }
        catch (e: Exception){
            throw RuntimeException("회원 가입 실패 ${e.message}")
        }
    }
}