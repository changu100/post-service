package com.example.sessionpostservice.user.repository.entity

import com.example.sessionpostservice.infra.repository.ZonedDateTimeConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "admin_invite")
data class AdminInvite(
    @Column(unique = true, name = "invite_code")
    var inviteCode: String,

    @ManyToOne
    var requestUser: User,

    @ManyToOne
    @JoinColumn(name = "invite_user_id")
    var inviteUser: User,

    @Convert(converter = ZonedDateTimeConverter::class)
    var expiredAt: ZonedDateTime = ZonedDateTime.now().plusDays(1),

    @Convert(converter = ZonedDateTimeConverter::class)
    @Column(name = "accept_at")
    var acceptAt: ZonedDateTime? = null,
) {
    constructor() : this(
        inviteCode = "",
        requestUser = User(),
        inviteUser = User(),
    )

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}