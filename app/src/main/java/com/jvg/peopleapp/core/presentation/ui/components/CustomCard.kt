package com.jvg.peopleapp.core.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun CustomClickableCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.outlinedShape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(
        defaultElevation = 2.dp
    ),
    border: BorderStroke? = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline),
    content: @Composable (ColumnScope.() -> Unit)
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        elevation = elevation,
        colors = colors,
        border = border,
        content = content
    )
}