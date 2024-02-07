package com.example.sessionpostservice.user.controller

import com.example.sessionpostservice.user.controller.request.SignUpRequest
import com.example.sessionpostservice.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class UserController(
    val userService: UserService
) {
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody request: SignUpRequest
    ): ResponseEntity<Boolean>{
        return ResponseEntity.ok(
            userService.signUp(request.email, request.password, request.name)
        )
    }

}