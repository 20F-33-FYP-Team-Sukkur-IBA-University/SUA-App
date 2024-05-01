package com.lumins.sua.views.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun ChatMessage(
    modifier: Modifier = Modifier,
    text: String,
    tipSize: Dp = 4.dp,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    chatMessageShape: Shape
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .clip(chatMessageShape)
                .background(color)

        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(start = tipSize + 8.dp, bottom = 8.dp, end = tipSize + 8.dp, top = 8.dp)
            ) {
                Text(
                    text = text, style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


class LeftAlignedChatMessageShape(
    private val tipSize: Dp = 8.dp,
    private val cornerRadius: Dp = 16.dp,
) : Shape {
    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ): Outline {
        val tipSizePx = with(density) { tipSize.toPx() }
        val cornerRadiusPx = with(density) { cornerRadius.toPx() }

        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    left = tipSizePx,
                    top = 0f,
                    right = size.width,
                    bottom = size.height,
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)

                )
            )
            moveTo(tipSizePx, size.height - tipSizePx - cornerRadiusPx / 2)
            lineTo(0f, size.height + tipSizePx)
            lineTo(tipSizePx + cornerRadiusPx, size.height)
        }
        return Outline.Generic(path)
    }
}


class RightAlignedChatMessageShape(
    private val tipSize: Dp = 8.dp,
    private val cornerRadius: Dp = 16.dp,
) : Shape {
    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ): Outline {
        val tipSizePx = with(density) { tipSize.toPx() }
        val cornerRadiusPx = with(density) { cornerRadius.toPx() }

        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f,
                    top = 0f,
                    right = size.width - tipSizePx,
                    bottom = size.height,
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)

                )
            )
            moveTo(size.width - tipSizePx - cornerRadiusPx, size.height)
            lineTo(size.width, size.height)
            lineTo(size.width - tipSizePx, size.height - tipSizePx - cornerRadiusPx / 2)
        }
        return Outline.Generic(path)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRightAlignedChatMessage() {
    MaterialTheme {
        val shape = RightAlignedChatMessageShape(tipSize = 4.dp, cornerRadius = 10.dp)
        ChatMessage(text = "Here is the message content.", chatMessageShape = shape)
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewLeftAlignedChatMessage() {
    MaterialTheme {
        val shape = LeftAlignedChatMessageShape(tipSize = 4.dp, cornerRadius = 10.dp)
        ChatMessage(text = "Here is the message content.", chatMessageShape = shape)
    }
}