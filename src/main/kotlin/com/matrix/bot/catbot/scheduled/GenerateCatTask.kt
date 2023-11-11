package com.matrix.bot.catbot.scheduled

import com.matrix.bot.catbot.bot.CatBot
import com.matrix.bot.catbot.generator.CatGenerator
import com.matrix.bot.catbot.service.ClientService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class GenerateCatTask(
    private val catBot: CatBot,
    private val clientService: ClientService,
    private val catGenerator: CatGenerator,
) {
    private val log = LoggerFactory.getLogger(GenerateCatTask::class.java)

    @Scheduled(fixedDelay = 1000)
    fun run() {
        clientService.getClosestClients().forEach { client ->
            log.info("Генерация кота для запланированного задания клиента ${client.id}")
            catBot.sendImage(
                clientId = client.id,
                image = catGenerator.generate().inputStream()
            )
            client.apply {
                nextInvocation = LocalDateTime.now().plusMinutes(client.offsetHours.toLong())
                log.info("Следующая генерация кота для клиента $id запланирована на $nextInvocation")
            }.also(clientService::saveClient)
        }
    }
}