package com.example.sessionpostservice.infra.security.jwt

import com.example.sessionpostservice.user.repository.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.Date

@Component
class JwtPlugin(
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
    @Value("\${auth.jwt.accessTokenExpireTime}") private val accessTokenExpireTime: Long,
) {
    fun validateToken(jwt: String): Jws<Claims> {

        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
    }

    fun generateToken(userId: Long, email: String, role: User.UserRole): String {
        val duration = Duration.ofHours(accessTokenExpireTime)

        val claims: Claims = Jwts.claims().add(
            "email", email,
        ).add(
            "role", role
        ).build()

        val now = Instant.now()
        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
            .subject(userId.toString())
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(duration)))
            .claims(claims)
            .signWith(key)
            .compact()
    }
}