package com.example.sessionpostservice.user.repository.entity

import com.example.sessionpostservice.infra.repository.ZonedDateTimeConverter
import com.example.sessionpostservice.user.service.dto.UserDto
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "member")
class User(
    @Column(unique = true)
    val email: String = "",
    var password: String = "",
    var name: String = "",

    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER,

    @ManyToOne
    var parentAdminUser: User? = null,

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
    var deletedAt: ZonedDateTime? = null,

    @OneToMany(mappedBy = "requestUser", cascade = [CascadeType.ALL], orphanRemoval = false)
    var adminInvites: List<AdminInvite> = mutableListOf(),

    @OneToMany(mappedBy = "inviteUser", cascade = [CascadeType.ALL], orphanRemoval = false)
    var inviteCodes: List<AdminInvite> = mutableListOf(),
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

    fun createAdminInviteCode(inviteUser: User): String {
        if (this.email == inviteUser.email) {
            throw RuntimeException("자기 자신을 초대할 수 없습니다.")
        }

        val uuid = java.util.UUID.randomUUID().toString()

        val adminInvite = AdminInvite(
            inviteCode = uuid,
            requestUser = this,
            inviteUser = inviteUser
        )

        adminInvites = mutableListOf(adminInvite)

        return uuid
    }

    fun participateAdmin(inviteCode: String) {
        if (parentAdminUser != null) {
            throw RuntimeException("이미 관리자에게 참여하였습니다.")
        }
        
        inviteCodes.find { it.inviteCode == inviteCode }?.let {
            if (it.expiredAt.isBefore(ZonedDateTime.now())) {
                throw RuntimeException("만료된 코드입니다.")
            }
            if (it.acceptAt != null) {
                throw RuntimeException("이미 참여한 코드입니다.")
            }

            it.acceptAt = ZonedDateTime.now()
            parentAdminUser = it.requestUser
            this.role = UserRole.ADMIN
        } ?: throw RuntimeException("존재하지 않는 코드입니다.")
    }
}