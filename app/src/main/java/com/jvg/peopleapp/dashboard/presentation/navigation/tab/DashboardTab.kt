package com.jvg.peopleapp.dashboard.presentation.navigation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.jvg.peopleapp.home.routes.BottomNavItem
import com.jvg.peopleapp.home.routes.HomeScreens

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
        Navigator(screen = HomeScreens.Dashboard.screen) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}
