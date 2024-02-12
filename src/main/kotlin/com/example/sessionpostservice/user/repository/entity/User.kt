package com.example.sessionpostservice.user.repository.entity

import com.example.sessionpostservice.infra.repository.ZonedDateTimeConverter
import com.example.sessionpostservice.user.service.dto.UserDto
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "member")
class User(
    @Column(unique = true)
    val email: String = "",
    var password: String = "",
    var name: String = "",
    var role: UserRole = UserRole.USER,
    @Column(name = "parent_admin_user_id")
    var parentAdminUserId: Long? = null,
    @Column(name = "image_url")
    var imageUrl: String? = null,
    @Convert(converter = ZonedDateTimeConverter::class)
    @Column(name = "created_at")
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    @Convert(converter = ZonedDateTimeConverter::class)
    @Column(name = "updated_at")
    var updatedAt: ZonedDateTime = ZonedDateTime.now(),
    @Convert(converter = ZonedDateTimeConverter::class)
    @Column(name = "deleted_at")
    var deletedAt: ZonedDateTime? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    enum class UserRole {
        USER, ADMIN
    }

    fun toDto(): UserDto {
        return UserDto(
            id = id!!,
            email = email,
            name = name
        )
    }

    fun setInitUserToAdmin() {
        if (this.id == 1L) {
            this.role = UserRole.ADMIN
        }
    }
}