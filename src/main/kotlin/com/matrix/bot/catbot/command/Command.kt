package com.matrix.bot.catbot.command

import com.matrix.bot.catbot.dto.CommandContext
import com.matrix.bot.catbot.dto.CommandResponse

interface Command {
    fun execute(commandContext: CommandContext): CommandResponse
}