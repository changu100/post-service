package com.example.sessionpostservice.infra.security.jwt

import com.example.sessionpostservice.infra.security.UserPrincipal
import com.example.sessionpostservice.user.repository.entity.User
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtPlugin: JwtPlugin,
) : OncePerRequestFilter() {

    companion object {
        private val BEARER_PATTERN = Regex("^Bearer (.+?)$")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION)
        val jwt = try {
            BEARER_PATTERN.matchEntire(authorization)?.groupValues?.get(1)
        } catch (e: Exception) {
            null
        }

        if (jwt != null) {

            val claims = jwtPlugin.validateToken(jwt)
            val userId = claims.payload.subject.toLong()
            val email = claims.payload.get("email", String::class.java)
            val role = claims.payload.get("role", String::class.java)

            val userPrincipal = UserPrincipal(userId, email, User.UserRole.valueOf(role))

            val authentication = JwtAuthenticationToken(
                principal = userPrincipal,
                details = WebAuthenticationDetailsSource().buildDetails(request) //loging  사용자 ip 정보
            )

            // 왜 저장해야하는지 확인이 필요
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}
