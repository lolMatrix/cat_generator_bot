package com.matrix.bot.catbot

import com.matrix.bot.catbot.properties.TelegramProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(TelegramProperties::class)
class CatbotApplication

fun main(args: Array<String>) {
    runApplication<CatbotApplication>(*args)
}
