package com.example.sessionpostservice.post.service.dto

import com.example.sessionpostservice.user.service.dto.UserDto
import java.time.ZonedDateTime

data class PostDto(
    val id: Long,
    val title: String,
    val user: UserDto,
    val content: String,
    val imageUrls: List<String>,
    val isHidden: Boolean,
    val deletedReason: String,
    val isAnnouncement: Boolean,

    val deletedAt: ZonedDateTime?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
)