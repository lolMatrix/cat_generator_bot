package com.matrix.bot.catbot.configuration

import org.deeplearning4j.nn.modelimport.keras.KerasModelImport
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.nd4j.common.io.ClassPathResource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TensorflowModelConfiguration {
    @Bean
    fun generatorModel(): MultiLayerNetwork = ClassPathResource("tf/model/generator.h5").file.path.let {
        KerasModelImport.importKerasSequentialModelAndWeights(it, false)
    }
}