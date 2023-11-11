package com.matrix.bot.catbot.bot

import com.matrix.bot.catbot.generator.CatGenerator
import com.matrix.bot.catbot.handler.CommandResolver
import com.matrix.bot.catbot.postprocess.proxy.CommandProxy
import com.matrix.bot.catbot.properties.TelegramProperties
import com.matrix.bot.catbot.service.ClientService
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import org.telegram.abilitybots.api.bot.AbilityBot
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.InputStream
import java.time.LocalDateTime

@Component
class CatBot(
    private val telegramProperties: TelegramProperties,
    private val commands: List<CommandProxy>,
) : AbilityBot(telegramProperties.token, telegramProperties.name) {
    private val commandResolver = CommandResolver(
        sender = sender,
        commands = commands
    )

    fun sendImage(clientId: Long, image: InputStream) {
        sender.sendPhoto(
            SendPhoto(
                clientId.toString(),
                InputFile(
                    image,
                    "generated_cat",
                )
            ).apply {
                caption = "Лови котика :3"
            }
        )
    }

    override fun creatorId(): Long = 1

    override fun onUpdateReceived(update: Update) {
        commandResolver.resolveCommand(update)
    }
}