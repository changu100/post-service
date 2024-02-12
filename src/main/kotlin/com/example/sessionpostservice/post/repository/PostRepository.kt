package com.example.sessionpostservice.post.repository

import com.example.sessionpostservice.post.repository.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>
