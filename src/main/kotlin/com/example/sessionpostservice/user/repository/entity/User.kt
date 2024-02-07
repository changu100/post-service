package com.example.sessionpostservice.user.repository.entity

import com.example.sessionpostservice.infra.repository.ZonedDateTimeConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name ="member")
class User(
    @Column(unique = true)
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val role: UserRole = UserRole.USER,
    @Column(name = "parent_admin_user_id")
    val parentAdminUserId: Long? = null,
    @Column(name = "image_url")
    val imageUrl:String? = null,
    @Convert(converter = ZonedDateTimeConverter::class)
    @Column(name = "created_at")
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
    @Convert(converter = ZonedDateTimeConverter::class)
    @Column(name = "updated_at")
    val updatedAt: ZonedDateTime = ZonedDateTime.now(),
    @Convert(converter = ZonedDateTimeConverter::class)
    @Column(name = "deleted_at")
    val deletedAt: ZonedDateTime? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    enum class UserRole {
        USER, ADMIN
    }

}