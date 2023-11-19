package com.matrix.bot.catbot.command.impl

import com.matrix.bot.catbot.command.Command
import com.matrix.bot.catbot.dto.CommandContext
import com.matrix.bot.catbot.dto.CommandResponse
import com.matrix.bot.catbot.postprocess.annotation.CommandName
import com.matrix.bot.catbot.service.ClientService

@CommandName("duration")
class DurationCommand(
    private val clientService: ClientService
) : Command {
    override fun execute(commandContext: CommandContext): CommandResponse {
        check(commandContext.paramsList.isNotEmpty()) {
            "Передан пустой список значений [chat_id = ${commandContext.chatId}]"
        }
        clientService.getClient(commandContext.chatId)
            .setOffsetHours(Integer.parseInt(commandContext.paramsList[0]))
            .also {
                clientService.saveClient(it)
            }
        return CommandResponse(
            text = "Установил время! Следующий котик будет через ${commandContext.paramsList[0]} часов."
        )
    }
}