package com.example.sessionpostservice.infra.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class PasswordEncoderConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        // BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체
        return BCryptPasswordEncoder()
    }
}