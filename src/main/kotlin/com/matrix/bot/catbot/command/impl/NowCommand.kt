package com.matrix.bot.catbot.command.impl

import com.matrix.bot.catbot.command.Command
import com.matrix.bot.catbot.dto.CommandContext
import com.matrix.bot.catbot.dto.CommandResponse
import com.matrix.bot.catbot.postprocess.annotation.CommandName
import com.matrix.bot.catbot.service.ClientService

@CommandName(name = "now")
class NowCommand(
    private val clientService: ClientService
) : Command {
    override fun execute(commandContext: CommandContext): CommandResponse =
        clientService.getClient(commandContext.chatId)
            .nextInvocationNow()
            .also(clientService::saveClient)
            .let {
                CommandResponse(
                    text = "Генерируем котика..."
                )
            }
}