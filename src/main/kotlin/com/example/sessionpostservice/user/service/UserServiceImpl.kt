package com.example.sessionpostservice.user.service

import com.example.sessionpostservice.user.repository.UserRepository
import com.example.sessionpostservice.user.repository.entity.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
): UserService {
    override fun signUp(email: String, password: String, name: String): Boolean {
        try {
            userRepository.save(
                User(
                    email = email,
                    password = passwordEncoder.encode(password),
                    name = name
                )
            )
            return true
        }
        catch (e: Exception){
            throw RuntimeException("회원 가입 실패 ${e.message}")
        }
    }

    override fun signIn(email: String, password: String): String {
        val user = userRepository.findByEmail(email)
            ?: throw RuntimeException("존재하지 않는 사용자입니다.")
        if (!passwordEncoder.matches(password, user.password)){
            throw RuntimeException("비밀번호가 일치하지 않습니다.")
        }

        // token 생성
        return "로그인 성공"
    }
}