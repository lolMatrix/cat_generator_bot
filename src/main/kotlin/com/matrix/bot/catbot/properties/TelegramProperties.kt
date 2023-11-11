package com.matrix.bot.catbot.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "telegram")
data class TelegramProperties(
    val name: String,
    val token: String,
)