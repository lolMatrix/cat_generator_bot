package com.matrix.bot.catbot.dto

data class CommandContext(
    val chatId: Long,
    var paramsList: List<String> = listOf()
)
