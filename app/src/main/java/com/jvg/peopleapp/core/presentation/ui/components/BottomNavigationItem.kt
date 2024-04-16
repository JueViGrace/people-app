package com.jvg.peopleapp.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator

@Composable
fun RowScope.BottomNavigationItem(
    modifier: Modifier = Modifier,
    tabNavigator: TabNavigator,
    tab: Tab,
) {
    val currentRoute = tabNavigator.current
    val selected = currentRoute == tab

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier
                .background(
                    color = if (selected) {
                        BottomAppBarDefaults.containerColor
                    } else {
                        BottomAppBarDefaults.containerColor.copy(0.38f)
                    },
                    shape = RoundedCornerShape(25)
                )
                .clip(RoundedCornerShape(25)),
            onClick = {
                tabNavigator.current = tab
            },
        ) {
            tab.options.icon?.let { icon ->
                Icon(
                    modifier = Modifier.size(22.dp),
                    painter = icon,
                    contentDescription = tab.options.title
                )
            }
        }
        if (selected) {
            CustomText(
                text = tab.options.title,
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
