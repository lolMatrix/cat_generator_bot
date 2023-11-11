package com.matrix.bot.catbot.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Client(
    @Id
    val id: Long,
    val name: String,
    var offsetHours: Int = 2,
    var nextInvocation: LocalDateTime = LocalDateTime.now(),
)
