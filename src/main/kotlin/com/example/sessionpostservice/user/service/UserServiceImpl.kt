package com.example.sessionpostservice.user.service

import com.example.sessionpostservice.infra.security.jwt.JwtPlugin
import com.example.sessionpostservice.user.repository.UserRepository
import com.example.sessionpostservice.user.repository.entity.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : UserService {
    @Transactional
    override fun signUp(email: String, password: String, name: String): Boolean {
        val user = try {
            userRepository.save(
                User(
                    email = email,
                    password = passwordEncoder.encode(password),
                    name = name
                )
            )
        } catch (e: Exception) {
            throw RuntimeException("회원 가입 실패 ${e.message}")
        }

        user.setInitUserToAdmin()

        return true
    }

    override fun signIn(email: String, password: String): String {
        val user = userRepository.findByEmail(email)
            ?: throw RuntimeException("존재하지 않는 사용자입니다.")
        if (!passwordEncoder.matches(password, user.password)) {
            throw RuntimeException("비밀번호가 일치하지 않습니다.")
        }

        // token 생성
        return jwtPlugin.generateToken(userId = user.id!!, email = user.email, role = user.role)
    }

    @Transactional
    override fun createAdminCode(userId: Long, userRole: User.UserRole, inviteUserEmail: String): String {
        if (userRole != User.UserRole.ADMIN) {
            throw RuntimeException("관리자만 코드를 생성할 수 있습니다.")
        }

        val inviteUser = userRepository.findByEmail(inviteUserEmail) ?: throw RuntimeException("존재하지 않는 사용자입니다.")

        val user = userRepository.findById(userId)
            .orElseThrow { RuntimeException("존재하지 않는 사용자입니다.") }

        return user.createAdminInviteCode(inviteUser)
    }

    @Transactional
    override fun participateAdmin(userId: Long, inviteCode: String) {
        val user = userRepository.findById(userId)
            .orElseThrow { RuntimeException("존재하지 않는 사용자입니다.") }

        user.participateAdmin(inviteCode)
    }
}