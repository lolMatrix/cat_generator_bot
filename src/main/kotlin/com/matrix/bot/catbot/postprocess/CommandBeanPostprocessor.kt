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
        is Command -> bean::class.annotations
            .filterIsInstance(CommandName::class.java)
            .first().let {
                CommandProxy(
                    command = bean,
                    commandName = it.name
                )
            }
        else -> bean
    }

    @Bean
    fun commandProxies(commands: List<Command>): List<CommandProxy> =
        commands.filterIsInstance(CommandProxy::class.java)
}
