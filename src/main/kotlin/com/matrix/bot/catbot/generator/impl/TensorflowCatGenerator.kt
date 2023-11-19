package com.matrix.bot.catbot.generator.impl

import com.matrix.bot.catbot.generator.CatGenerator
import com.matrix.bot.catbot.generator.decorator.TensorflowModel
import com.matrix.bot.catbot.generator.decorator.TensorflowModel.Companion.IMAGE_SHAPE
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@Component
class TensorflowCatGenerator(
    private val modelDecorator: TensorflowModel
) : CatGenerator {
    override fun generate(): ByteArray = modelDecorator.predict().toRgbValuesArray().let { rgbValues ->
        val (width, height) = IMAGE_SHAPE
        BufferedImage(width.toInt(), height.toInt(), BufferedImage.TYPE_INT_RGB).apply {
            setRGB(0, 0, width.toInt(), height.toInt(), rgbValues, 0, width.toInt())
        }
    }.toJPEGBytes()

    private fun Array<IntArray>.toRgbValuesArray() = asSequence()
        .map { Color(it[0], it[1], it[2]).rgb }
        .toList()
        .toIntArray()

    private fun BufferedImage.toJPEGBytes() = ByteArrayOutputStream().use { outputStream ->
        ImageIO.write(this, "png", outputStream)
        outputStream.toByteArray()
    }
}