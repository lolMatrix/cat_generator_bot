package com.matrix.bot.catbot.command.impl

import com.matrix.bot.catbot.dto.CommandContext
import com.matrix.bot.catbot.service.ClientService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class StartCommandTest(
    @MockK(relaxUnitFun = true) private val clientService: ClientService
) {
    @InjectMockKs
    private lateinit var startCommand: StartCommand

    @Test
    fun `should save new client when client not exists`() {
        val chatId = Random.nextLong()
        val commandContext = CommandContext(
            chatId = chatId
        )
        every { clientService.existById(chatId) } returns false

        assertDoesNotThrow {
            startCommand.execute(commandContext)
        }

        verify {
            clientService.saveClient(any())
        }
    }

    @Test
    fun `should do nothing when client exist`() {
        val chatId = Random.nextLong()
        val commandContext = CommandContext(
            chatId = chatId
        )
        every { clientService.existById(chatId) } returns true

        assertDoesNotThrow {
            startCommand.execute(commandContext)
        }

        verify(exactly = 0) {
            clientService.saveClient(any())
        }
    }
}