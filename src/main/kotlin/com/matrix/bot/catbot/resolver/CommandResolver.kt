package com.matrix.bot.catbot.resolver

import com.matrix.bot.catbot.dto.CommandContext
import com.matrix.bot.catbot.dto.CommandResponse
import com.matrix.bot.catbot.postprocess.proxy.CommandProxy
import org.slf4j.LoggerFactory
import org.telegram.abilitybots.api.sender.MessageSender
import org.telegram.abilitybots.api.util.AbilityUtils.getChatId
import org.telegram.telegrambots.meta.api.objects.Update

class CommandResolver(
    private val sender: MessageSender,
    private val commands: List<CommandProxy>
) {
    private val log = LoggerFactory.getLogger(CommandResolver::class.java)

    fun resolveCommand(update: Update) {
        log.debug("Получен запрос на обработку команды")
        val parsedCommand = parseCommand(update.message.text)
        val commandName = parsedCommand.first()
        log.info("Получена команда ${commandName}, проверяю правильность введенной команды")
        if (!commandName.matches(COMMAND_REGEX)) {
            sendUnknownCommand(getChatId(update))
            log.error("Команда $commandName ведена не правильно")
        }

        commands.firstOrNull { "/${it.commandName}" == commandName }?.execute(
            commandContext = CommandContext(
                chatId = getChatId(update),
                paramsList = parsedCommand.drop(1)
            )
        )?.let {
            log.info("Получен ответ от команды ${commandName}, идет отправка результата...")
            sender.execute(it.toSendMessage(getChatId(update)))
            log.info("Команда $commandName исполнена")
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