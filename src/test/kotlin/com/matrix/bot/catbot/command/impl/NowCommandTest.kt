package com.matrix.bot.catbot.command.impl

import com.matrix.bot.catbot.dto.CommandContext
import com.matrix.bot.catbot.model.Client
import com.matrix.bot.catbot.service.ClientService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class NowCommandTest(
    @MockK(relaxUnitFun = true) private val clientService: ClientService
) {
    @InjectMockKs
    private lateinit var nowCommand: NowCommand

    @Test
    fun `should set next invocation now`() {
        val chatId = Random.nextLong()
        val commandContext = CommandContext(
            chatId = chatId
        )
        val client = mockk<Client>(relaxed = true)
        every { clientService.getClient(chatId) } returns client

        assertDoesNotThrow {
            nowCommand.execute(commandContext)
        }

        verify {
            client.nextInvocationNow()
            clientService.saveClient(any())
        }
    }
}