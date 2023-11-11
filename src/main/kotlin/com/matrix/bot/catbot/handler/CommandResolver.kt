package com.matrix.bot.catbot.handler

import com.matrix.bot.catbot.dto.CommandContext
import com.matrix.bot.catbot.dto.CommandResponse
import com.matrix.bot.catbot.postprocess.proxy.CommandProxy
import org.slf4j.LoggerFactory
import org.springframework.util.ResourceUtils
import org.telegram.abilitybots.api.sender.MessageSender
import org.telegram.abilitybots.api.sender.SilentSender
import org.telegram.abilitybots.api.util.AbilityUtils.getChatId
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.ByteArrayInputStream
import java.util.logging.Logger

class CommandResolver(
    private val sender: MessageSender,
    private val commands: List<CommandProxy>
) {
    private val log = LoggerFactory.getLogger(CommandResolver::class.java)

    fun resolveCommand(update: Update) {
        log.debug("Получен запрос на обработку команды")
        val parsedCommand = parseCommand(update.message.text)
        log.info("Получена команда ${parsedCommand}, проверяю правильность введенной команды")
        val commandName = parsedCommand.first()
        if (!commandName.matches(COMMAND_REGEX)) {
            sendUnknownCommand(getChatId(update))
            log.error("Команда $parsedCommand ведена не правильно")
        }

        commands.firstOrNull { "/${it.commandName}" == commandName }?.execute(
            commandContext = CommandContext(
                chatId = getChatId(update),
                paramsList = parsedCommand.drop(1)
            )
        )?.let {
            log.info("Получена команда ${parsedCommand}, идет исполнение...")
            sender.execute(it.toSendMessage(getChatId(update)))
            log.info("Команда $parsedCommand исполнена")
        }
    }

    private fun parseCommand(command: String): List<String> = command.split(" ")

    private fun sendUnknownCommand(chatId: Long) = sender
        .execute(
            CommandResponse("Неизвестная команда")
                .toSendMessage(chatId)
        )

    private companion object {
        private val COMMAND_REGEX = Regex("/[a-z]*")
    }
}