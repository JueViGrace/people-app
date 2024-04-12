package com.jvg.peopleapp.dashboard.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.jvg.peopleapp.core.presentation.navigation.home.routes.BottomNavItem
import com.jvg.peopleapp.core.presentation.ui.components.CustomText

object DashboardTab : Tab {
    private fun readResolve(): Any = DashboardTab

    override val key: ScreenKey = uniqueScreenKey
    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(id = BottomNavItem.DashboardTab.icon)
            return remember {
                TabOptions(
                    index = BottomNavItem.DashboardTab.index,
                    title = BottomNavItem.DashboardTab.title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CustomText(
                text = "Building...",
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        }
    }
}
