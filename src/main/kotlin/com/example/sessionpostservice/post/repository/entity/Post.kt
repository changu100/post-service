package com.example.sessionpostservice.post.repository.entity

import com.example.sessionpostservice.infra.repository.ZonedDateTimeConverter
import com.example.sessionpostservice.post.controller.request.UpdateRequest
import com.example.sessionpostservice.post.service.dto.PostDto
import com.example.sessionpostservice.user.repository.entity.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "post")
data class Post(
    var title: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,

    var content: String,

    /*
    각 연관관계의 Default Fetching 전략
        @OneToMany: LAZY
        @ManyToOne: EAGER
        @ManyToMany: LAZY
        @OneToOne: EAGER
     */
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    var imageUrls: List<PostImage> = listOf(),

    @Column(name = "is_hidden")
    var isHidden: Boolean = false, // 비공개 용도로 사용됨.

    @Column(name = "deleted_reason")
    var isHiddenReason: String = "",

    @Column(name = "is_announcement")
    var isAnnouncement: Boolean = false,

    @Column(name = "deleted_at")
    @Convert(converter = ZonedDateTimeConverter::class)
    var deletedAt: ZonedDateTime? = null,

    @Column(name = "created_at")
    @Convert(converter = ZonedDateTimeConverter::class)
    var createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column(name = "updated_at")
    @Convert(converter = ZonedDateTimeConverter::class)
    var updatedAt: ZonedDateTime = ZonedDateTime.now(),

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    constructor() : this(
        "",
        User(),
        "",
    ) {
    }

    fun toDto(): PostDto {
        return PostDto(
            id = id!!,
            title = title,
            user = user.toDto(),
            content = content,
            imageUrls = imageUrls.map { it.imageUrl },
            isHidden = isHidden,
            deletedReason = isHiddenReason,
            isAnnouncement = isAnnouncement,
            deletedAt = deletedAt,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun delete() {
        deletedAt = ZonedDateTime.now()
    }

    fun update(request: UpdateRequest) {
        updatedAt = ZonedDateTime.now()
        if (request.title != null)
            title = request.title
        if (request.content != null)
            content = request.content
        if (request.imageUrls != null)
            imageUrls = request.imageUrls.map { imageUrl ->
                PostImage(
                    this,
                    imageUrl,
                )
            }
    }
}