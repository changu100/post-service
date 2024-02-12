package com.example.sessionpostservice.post.controller

import com.example.sessionpostservice.infra.security.UserPrincipal
import com.example.sessionpostservice.post.controller.request.CreateRequest
import com.example.sessionpostservice.post.controller.request.UpdateRequest
import com.example.sessionpostservice.post.controller.response.PostResponse
import com.example.sessionpostservice.post.service.PostService
import com.example.sessionpostservice.post.service.dto.PostDto
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postService: PostService,
) {

    @GetMapping()
    fun getAllPosts(): ResponseEntity<List<PostResponse>> {
        return ResponseEntity.ok(
            listOf(
                PostResponse.getMock(),
                PostResponse.getMock(),
                PostResponse.getMock(),
                PostResponse.getMock(),
                PostResponse.getMock(),
            )
        )
    }

    @PostMapping()
    fun createPost(
        @RequestBody request: CreateRequest
    ): ResponseEntity<PostDto> {
        return ResponseEntity.ok(postService.createPost(1, request))
    }

    @PostMapping("/{id}")
    fun updatePost(
        @PathVariable id: Long,
        @RequestBody request: UpdateRequest
    ): ResponseEntity<PostDto> {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        val userId = principal.id
        return ResponseEntity.ok(postService.updatePost(id, userId, request))
    }

    @GetMapping("/{id}")
    fun getPostById(
        @PathVariable id: Long
    ): ResponseEntity<PostDto> {
        return ResponseEntity.ok(postService.getPostById(id))
    }

    @DeleteMapping("/{id}")
    fun deletePost(
        @PathVariable id: Long,
    ): ResponseEntity<Boolean> {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        val userId = principal.id
        postService.deletePost(id, userId)
        return ResponseEntity.ok(true)
    }
}