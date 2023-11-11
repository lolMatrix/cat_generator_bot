package com.matrix.bot.catbot.postprocess.annotation

import org.springframework.stereotype.Component

@Component
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class CommandName(
    val name: String = "/default"
)
