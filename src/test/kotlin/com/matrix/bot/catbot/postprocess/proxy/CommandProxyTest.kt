package com.matrix.bot.catbot.postprocess.proxy

import com.matrix.bot.catbot.command.Command
import com.matrix.bot.catbot.dto.CommandContext
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.random.Random

class CommandProxyTest {
    private val command = mockk<Command>(relaxed = true)

    private val commandProxy = CommandProxy(
        commandName = UUID.randomUUID().toString(),
        command = command
    )

    @Test
    fun `should execute command whenever`() {
        val commandContext = CommandContext(
            chatId = Random.nextLong()
        )

        commandProxy.execute(commandContext)

        verify {
            command.execute(commandContext)
        }
    }
}