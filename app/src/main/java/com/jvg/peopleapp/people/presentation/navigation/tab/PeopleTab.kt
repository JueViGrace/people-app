package com.jvg.peopleapp.people.presentation.navigation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.jvg.peopleapp.home.routes.HomeScreens
import com.jvg.peopleapp.home.routes.HomeTabs

object PeopleTab : Tab {
    private fun readResolve(): Any = PeopleTab

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(id = HomeTabs.People.icon)
            return remember {
                TabOptions(
                    index = HomeTabs.People.index,
                    title = HomeTabs.People.title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(screen = HomeScreens.People.screen) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}
