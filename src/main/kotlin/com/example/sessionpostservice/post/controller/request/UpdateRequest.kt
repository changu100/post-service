package com.example.sessionpostservice.post.controller.request

data class UpdateRequest(
    val title: String?,
    val content: String?,
    val imageUrls: List<String>?,
)
