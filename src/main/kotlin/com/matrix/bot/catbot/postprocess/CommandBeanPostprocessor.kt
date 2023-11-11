package com.matrix.bot.catbot.postprocess

import com.matrix.bot.catbot.command.Command
import com.matrix.bot.catbot.postprocess.annotation.CommandName
import com.matrix.bot.catbot.postprocess.proxy.CommandProxy
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class CommandBeanPostprocessor : BeanPostProcessor {
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any = when (bean) {
        is Command -> {
            val commandNameAnnotation = bean::class.annotations
                .filterIsInstance(CommandName::class.java)
                .first()
            CommandProxy(
                command = bean,
                commandName = commandNameAnnotation.name
            )
        }

        else -> bean
    }

    @Bean
    fun registerList(commands: List<Command>): List<CommandProxy> = commands.filterIsInstance(CommandProxy::class.java)
}
