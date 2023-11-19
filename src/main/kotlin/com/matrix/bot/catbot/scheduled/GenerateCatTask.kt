package com.matrix.bot.catbot.scheduled

import com.matrix.bot.catbot.bot.CatBot
import com.matrix.bot.catbot.generator.CatGenerator
import com.matrix.bot.catbot.service.ClientService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class GenerateCatTask(
    private val catBot: CatBot,
    private val clientService: ClientService,
    private val catGenerator: CatGenerator,
) {
    private val log = LoggerFactory.getLogger(GenerateCatTask::class.java)

    @Scheduled(fixedDelay = 1000)
    fun run() {
        clientService.getScheduledClients().forEach { client ->
            log.info("Генерация кота для запланированного задания клиента ${client.id}")
            catBot.sendImage(
                clientId = client.id,
                image = catGenerator.generate().inputStream()
            )
            client.shiftNextInvocation().also {
                clientService.saveClient(it)
                log.info("Следующая генерация кота для клиента ${it.id} запланирована на ${it.nextInvocation}")
            }
        }
    }
}