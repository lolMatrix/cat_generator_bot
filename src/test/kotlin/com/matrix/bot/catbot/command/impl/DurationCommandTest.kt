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
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class DurationCommandTest(
    @MockK(relaxUnitFun = true) private val clientService: ClientService
) {
    @InjectMockKs
    private lateinit var durationCommand: DurationCommand

    @Test
    fun `should throw when arguments not present`() {
        val chatId = Random.nextLong()
        val commandContext = CommandContext(chatId = chatId, paramsList = listOf())

        assertThrows<IllegalStateException> {
            durationCommand.execute(commandContext)
        }.apply {
            assertThat(message, `is`("Передан пустой список значений [chat_id = ${chatId}]"))
        }
    }

    @Test
    fun `should return command response when duration is changed`() {
        val chatId = Random.nextLong()
        val duration = Random.nextInt()
        val commandContext = CommandContext(chatId = chatId, paramsList = listOf(duration.toString()))
        val client = mockk<Client>(relaxed = true)
        every { clientService.getClient(chatId) } returns client

        assertDoesNotThrow {
            durationCommand.execute(commandContext)
        }

        verify {
            client.setOffsetHours(duration)
            clientService.saveClient(any())
        }
    }
}