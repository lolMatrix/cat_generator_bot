package com.matrix.bot.catbot.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Client(
    @Id
    val id: Long,
    val name: String,
    val offsetHours: Int = 2,
    val nextInvocation: LocalDateTime = LocalDateTime.now(),
) {
    fun shiftNextInvocation() = copy(nextInvocation = LocalDateTime.now().plusHours(offsetHours.toLong()))
    fun nextInvocationNow() = copy(nextInvocation = LocalDateTime.now())
    fun setOffsetHours(offsetHours: Int) = copy(offsetHours = offsetHours)
}
