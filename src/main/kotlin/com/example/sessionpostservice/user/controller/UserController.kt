package com.example.sessionpostservice.user.controller

import com.example.sessionpostservice.infra.security.UserPrincipal
import com.example.sessionpostservice.user.controller.request.CreateAdminInviteCodeRequest
import com.example.sessionpostservice.user.controller.request.ParticipateAdminRequest
import com.example.sessionpostservice.user.controller.request.SignInRequest
import com.example.sessionpostservice.user.controller.request.SignUpRequest
import com.example.sessionpostservice.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    val userService: UserService
) {
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody request: SignUpRequest
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(
            userService.signUp(request.email, request.password, request.name)
        )
    }

    @PostMapping("/sign-in")
    fun signIn(
        @RequestBody request: SignInRequest
    ): ResponseEntity<String> {
        return ResponseEntity.ok(
            userService.signIn(request.email, request.password)
        )
    }

    @PostMapping("/admin/create-code")
    fun createCode(
        @RequestBody request: CreateAdminInviteCodeRequest
    ): ResponseEntity<String> {
        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        return ResponseEntity.ok(
            userService.createAdminCode(userPrincipal.id, userPrincipal.role, request.inviteUserEmail)
        )
    }

    @PutMapping("/admin/participate")
    fun inviteAdmin(
        @RequestBody request: ParticipateAdminRequest
    ): ResponseEntity<Boolean> {
        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        userService.participateAdmin(userPrincipal.id, request.adminInviteCode)
        return ResponseEntity.ok(
            true
        )
    }
}