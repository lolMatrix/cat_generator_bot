package com.matrix.bot.catbot.postprocess.proxy

import com.matrix.bot.catbot.command.Command
import com.matrix.bot.catbot.dto.CommandContext
import com.matrix.bot.catbot.dto.CommandResponse

class CommandProxy(
    val commandName: String,
    private val command: Command,
) : Command {
    override fun execute(commandContext: CommandContext) = command.execute(commandContext)
}