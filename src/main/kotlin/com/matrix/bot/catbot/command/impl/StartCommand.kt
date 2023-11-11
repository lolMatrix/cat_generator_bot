package com.matrix.bot.catbot.command.impl

import com.matrix.bot.catbot.command.Command
import com.matrix.bot.catbot.dto.CommandContext
import com.matrix.bot.catbot.dto.CommandResponse
import com.matrix.bot.catbot.model.Client
import com.matrix.bot.catbot.postprocess.annotation.CommandName
import com.matrix.bot.catbot.service.ClientService
import org.springframework.stereotype.Component

@CommandName("start")
class StartCommand(
    val clientService: ClientService
) : Command {
    override fun execute(commandContext: CommandContext): CommandResponse {
        val text = if (!clientService.existById(commandContext.chatId)) {
            clientService.saveClient(
                Client(
                    id = commandContext.chatId,
                    name = "UNKNOWN"
                )
            )
            "Привет! Установи напиши, через какое время в часа тебе генерировать котиков." +
                    " Напиши /duration <время_через_которое_присылать котиков>"
        } else  {
            "Рад твоему возвращению!"
        }
        return CommandResponse(
            text = text
        )
    }
}