package com.matrix.bot.catbot.configuration

import com.matrix.bot.catbot.bot.CatBot
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class TelegramConfiguration {

    @Bean
    fun telegramBotsApi(catBot: CatBot) = TelegramBotsApi(DefaultBotSession::class.java).apply {
        registerBot(catBot)
    }

}