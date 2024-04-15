package com.jvg.peopleapp.core.presentation.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String,
    icon: Painter?,
    actions: ((@Composable () -> Unit?))? = null,
    onClick: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            CustomText(
                text = title,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        },
        navigationIcon = {
            icon?.let { ic ->
                IconButton(onClick = { if (onClick != null) onClick() }) {
                    Icon(
                        painter = ic,
                        contentDescription = title,
                    )
                }
            }
        },
        actions = {
            if (actions != null) {
                actions()
            }
        }
    )
}
