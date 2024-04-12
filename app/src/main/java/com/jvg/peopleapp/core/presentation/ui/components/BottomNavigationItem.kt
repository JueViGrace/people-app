package com.jvg.peopleapp.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@Composable
fun RowScope.BottomNavigationItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    alwaysShowLabel: Boolean = true,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = modifier.background(
                color = if (selected) {
                    BottomAppBarDefaults.containerColor
                } else {
                    BottomAppBarDefaults.containerColor.copy(0.38f)
                },
                shape = RoundedCornerShape(25)
            ).clip(RoundedCornerShape(25)),
            onClick = onClick,
        ) {
            icon()
        }
        if (selected || alwaysShowLabel) {
            label()
        }
    }
}
