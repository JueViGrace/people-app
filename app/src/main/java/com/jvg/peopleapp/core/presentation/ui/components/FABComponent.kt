package com.jvg.peopleapp.core.presentation.ui.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun FABComponent(
    onAdd: () -> Unit,
    icon: Painter
) {
    FloatingActionButton(
        onClick = { onAdd() },
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
    ) {
        Icon(painter = icon, contentDescription = "Add Icon")
    }
}
