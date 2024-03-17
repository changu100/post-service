package com.example.sessionpostservice.infra.security

import com.example.sessionpostservice.user.repository.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    val id: Long,
    val email: String,
    val role: User.UserRole,
){
    fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_${role.name}"))
    }
}
