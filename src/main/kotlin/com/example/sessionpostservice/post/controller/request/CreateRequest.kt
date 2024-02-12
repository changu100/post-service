package com.example.sessionpostservice.post.controller.request

data class CreateRequest(
    val title: String,
    val content: String,
    val imageUrls: List<String>,
)
