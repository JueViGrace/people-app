package com.jvg.peopleapp.core.presentation.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.jvg.peopleapp.core.common.Constants.bottomList

@Composable
fun BottomBarComponent(navigator: Navigator) {
    val tabNavigator = LocalTabNavigator.current
    val currentRoute = tabNavigator.current
    BottomAppBar(
        actions = {
            bottomList.forEach { tab ->
                val selected = currentRoute == tab

                BottomNavigationItem(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    selected = selected,
                    alwaysShowLabel = selected,
                    onClick = {
                        tabNavigator.current = tab
                    },
                    icon = {
                        tab.options.icon?.let { icon ->
                            Icon(
                                modifier = Modifier.size(22.dp),
                                painter = icon,
                                contentDescription = tab.options.title
                            )
                        }
                    },
                    label = {
                        CustomText(
                            text = tab.options.title,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        },
        floatingActionButton = {
            FABComponent(
                onAdd = {

                }
            )
        },
        contentPadding = PaddingValues(
            top = BottomAppBarDefaults.ContentPadding.calculateTopPadding(),
            bottom = BottomAppBarDefaults.ContentPadding.calculateBottomPadding(),
            start = BottomAppBarDefaults.ContentPadding.calculateStartPadding(LayoutDirection.Rtl),
            end = BottomAppBarDefaults.ContentPadding.calculateRightPadding(LayoutDirection.Rtl)
        )
    )
}