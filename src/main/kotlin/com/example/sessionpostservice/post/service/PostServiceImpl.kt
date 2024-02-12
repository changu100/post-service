package com.example.sessionpostservice.post.service

import com.example.sessionpostservice.post.controller.request.CreateRequest
import com.example.sessionpostservice.post.controller.request.UpdateRequest
import com.example.sessionpostservice.post.repository.PostRepository
import com.example.sessionpostservice.post.repository.entity.Post
import com.example.sessionpostservice.post.repository.entity.PostImage
import com.example.sessionpostservice.post.service.dto.PostDto
import com.example.sessionpostservice.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) : PostService {
    override fun getAllPosts(): List<PostDto> {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun createPost(userId: Long, request: CreateRequest): PostDto {
        val user = userRepository.findById(userId).orElseThrow()

        val post = postRepository.save(
            Post(
                request.title,
                user,
                request.content,
            )
        )

        post.imageUrls = request.imageUrls.map { imageUrl ->
            PostImage(
                post,
                imageUrl,
            )
        }

        postRepository.save(post)
        return post.toDto()
    }

    override fun getPostById(id: Long): PostDto {
        val post = postRepository.findById(id).orElseThrow()
        return post.toDto()
    }

    @Transactional
    override fun updatePost(id: Long, userId: Long, request: UpdateRequest): PostDto {
        val post = postRepository.findById(id).orElseThrow()

        if (post.user.id != userId) {
            throw RuntimeException("본인의 게시글만 수정할 수 있습니다.")
        }

        post.updatedAt = ZonedDateTime.now()
        if (request.title != null)
            post.title = request.title
        if (request.content != null)
            post.content = request.content
        if (request.imageUrls != null)
            post.imageUrls = request.imageUrls.map { imageUrl ->
                PostImage(
                    post,
                    imageUrl,
                )
            }
        return post.toDto()
    }

    @Transactional
    override fun deletePost(id: Long, userId: Long) {
        val post = postRepository.findById(id).orElseThrow()

        if (post.user.id != userId) {
            throw RuntimeException("본인의 게시글만 삭제할 수 있습니다.")
        }

        try {
            post.deletedAt = ZonedDateTime.now()
        } catch (e: Exception) {
            throw RuntimeException("post 삭제에 실패했습니다.")
        }
    }
}