package com.matrix.bot.catbot.generator.impl

import com.matrix.bot.catbot.generator.CatGenerator
import com.matrix.bot.catbot.generator.ModelDecorator
import com.matrix.bot.catbot.generator.ModelDecorator.Companion.IMAGE_SHAPE
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@Component
class TensorflowCatGenerator(
    private val modelDecorator: ModelDecorator
) : CatGenerator {
    override fun generate(): ByteArray = ByteArrayOutputStream().use { outputStream ->
        val (width, height) = IMAGE_SHAPE
        val generatedColorArray = modelDecorator.predict().toRgbValuesArray()
        BufferedImage(width.toInt(), height.toInt(), BufferedImage.TYPE_INT_RGB).apply {
            setRGB(0, 0, width.toInt(), height.toInt(), generatedColorArray, 0, width.toInt())
        }.also { image ->
            ImageIO.write(image, "jpeg", outputStream)
        }
        outputStream.toByteArray()
    }

    private fun Array<IntArray>.toRgbValuesArray() = asSequence()
        .map { Color(it[0], it[1], it[2]).rgb }
        .toList()
        .toIntArray()
}