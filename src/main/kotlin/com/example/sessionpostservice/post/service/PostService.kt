package com.example.sessionpostservice.post.service

import com.example.sessionpostservice.post.controller.request.CreateRequest
import com.example.sessionpostservice.post.controller.request.UpdateRequest
import com.example.sessionpostservice.post.service.dto.PostDto

interface PostService {
    fun getAllPosts(): List<PostDto>
    fun createPost(userId: Long, request: CreateRequest): PostDto
    fun getPostById(id: Long): PostDto
    fun updatePost(id: Long, userId: Long, request: UpdateRequest): PostDto
    fun deletePost(id: Long, userId: Long)
}