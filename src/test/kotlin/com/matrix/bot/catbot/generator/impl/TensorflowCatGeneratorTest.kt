package com.matrix.bot.catbot.generator.impl

import com.matrix.bot.catbot.generator.decorator.TensorflowModel
import com.matrix.bot.catbot.generator.decorator.TensorflowModel.Companion.IMAGE_SHAPE
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import javax.imageio.ImageIO
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class TensorflowCatGeneratorTest(
    @MockK private val modelDecorator: TensorflowModel
) {
    @InjectMockKs
    private lateinit var tensorflowCatGenerator: TensorflowCatGenerator

    @Test
    fun `should return image for random generated image`() {
        val randomColors = randomColors()
        every { modelDecorator.predict() } returns randomColors

        assertDoesNotThrow {
            tensorflowCatGenerator.generate().let {
                ImageIO.read(it.inputStream())
            }
        }
    }

    private fun randomColors() = (0..IMAGE_SHAPE.first * IMAGE_SHAPE.second).map {
        intArrayOf(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
    }.toTypedArray()
}