package com.matrix.bot.catbot.dto.util

import com.matrix.bot.catbot.dto.CommandContext
import org.telegram.abilitybots.api.objects.MessageContext

fun MessageContext.toCommandContext() = CommandContext(
    chatId = chatId()
)
