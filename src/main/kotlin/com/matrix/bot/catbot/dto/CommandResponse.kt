package com.matrix.bot.catbot.dto

import org.telegram.telegrambots.meta.api.methods.send.SendMessage

data class CommandResponse(
    val text: String
) {
    fun toSendMessage(chatId: Long) = SendMessage().apply {
        text = this@CommandResponse.text
        setChatId(chatId)
    }
}