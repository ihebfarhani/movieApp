package com.app.skiilstest.ui.screens.widgets

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TextWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    tint: Color
) {
    val myId = "inlineContent"
    val customText = buildAnnotatedString {
        appendInlineContent(myId, "[icon]")
        append(text)
    }

    val inlineContent = mapOf(
        Pair(
            myId,
            InlineTextContent(
                Placeholder(
                    width = 12.sp,
                    height = 12.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline
                )
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = tint)
            }
        )
    )

    Text(
        text = customText,
        modifier = modifier,
        style = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        ), color = Color.Black,
        inlineContent = inlineContent
    )
}