package com.jvg.peopleapp.payments.presentation.navigation.tab

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.FadeTransition
import com.jvg.peopleapp.home.routes.HomeScreens
import com.jvg.peopleapp.home.routes.HomeTabs

object PaymentsTab : Tab {
    private fun readResolve(): Any = PaymentsTab

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(id = HomeTabs.Payments.icon)
            return TabOptions(
                index = HomeTabs.Payments.index,
                title = HomeTabs.Payments.title,
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        Navigator(screen = HomeScreens.Payments.screen) { navigator ->
            FadeTransition(navigator = navigator)
        }
    }
}
