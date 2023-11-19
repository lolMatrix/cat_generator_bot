package com.matrix.bot.catbot.generator.decorator

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.nd4j.linalg.api.buffer.DataType
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.indexing.PointIndex
import org.springframework.stereotype.Component

@Component
class TensorflowModel(
    private val generatorModel: MultiLayerNetwork,
) {
    fun predict(): Array<IntArray> = randomNormal().use { noise ->
        generatorModel.output(noise).muli(COLOR_MAX_VALUE).castTo(DataType.INT32)
    }.use { prediction ->
        prediction[PointIndex(0)].reshape(IMAGE_SHAPE.first * IMAGE_SHAPE.second, COLOR_COUNT).toIntMatrix()
    }

    private fun randomNormal() =
        Nd4j.random.normal(0.0, 1.0, DataType.fromNumpy("float32"), *NOISE_SHAPE)

    companion object {
        val IMAGE_SHAPE = 64L to 64L
        private val NOISE_SHAPE = longArrayOf(1, 256)
        private const val COLOR_COUNT = 3L
        private const val COLOR_MAX_VALUE = 255
    }
}