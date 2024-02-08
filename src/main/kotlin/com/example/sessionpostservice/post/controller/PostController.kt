package com.example.sessionpostservice.post.controller

import com.example.sessionpostservice.post.controller.response.PostResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/posts")
class PostController {

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

}