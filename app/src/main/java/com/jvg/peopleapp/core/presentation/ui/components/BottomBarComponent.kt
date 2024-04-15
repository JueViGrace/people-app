package com.jvg.peopleapp.core.presentation.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.jvg.peopleapp.home.routes.HomeTabs

@Composable
fun BottomBarComponent(
    onAdd: () -> Unit,
    icon: Painter
) {
    val tabNavigator = LocalTabNavigator.current
    BottomAppBar(
        actions = {
            BottomNavigationItem(
                modifier = Modifier.padding(horizontal = 5.dp),
                tabNavigator,
                HomeTabs.Dashboard.tab,
            )
            BottomNavigationItem(
                modifier = Modifier.padding(horizontal = 5.dp),
                tabNavigator,
                HomeTabs.People.tab,
            )
        },
        floatingActionButton = {
            FABComponent(
                onAdd = {
                    onAdd()
                },
                icon = icon
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
