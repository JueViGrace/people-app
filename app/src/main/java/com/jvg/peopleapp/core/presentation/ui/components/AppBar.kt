package com.jvg.peopleapp.core.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(5.dp, Alignment.Start),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    title: String,
    icon: Painter
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 5.dp),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {
        Icon(painter = icon, contentDescription = title)
        CustomText(
            text = title,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}
