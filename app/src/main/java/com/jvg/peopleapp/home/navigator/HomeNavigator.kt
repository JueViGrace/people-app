package com.jvg.peopleapp.home.navigator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.jvg.peopleapp.core.common.Constants.bottomList
import com.jvg.peopleapp.core.presentation.ui.components.AppBar
import com.jvg.peopleapp.core.presentation.ui.components.BottomBarComponent
import com.jvg.peopleapp.home.routes.HomeTabs
import com.jvg.peopleapp.people.presentation.person.navigation.DetailScreens

object HomeNavigator : Screen {
    private fun readResolve(): Any = HomeNavigator

    override val key: ScreenKey = uniqueScreenKey

    @OptIn(InternalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        TabNavigator(
            tab = HomeTabs.Dashboard.tab,
            tabDisposable = { tabNavigator ->
                TabDisposable(navigator = tabNavigator, tabs = bottomList)
            },
            key = key
        ) { tabNavigator ->
            Scaffold(
                topBar = {
                    AppBar(
                        title = tabNavigator.current.options.title,
                        icon = tabNavigator.current.options.icon,
                    )
                },
                bottomBar = {
                    if (navigator.key != DetailScreens.PersonDetails().screen.key) {
                        BottomBarComponent(navigator)
                    }
                },
                content = { paddingValues ->
                    Box(
                        modifier = Modifier
                            .padding(
                                5.dp
                            )
                            .padding(
                                top = paddingValues.calculateTopPadding(),
                                bottom = paddingValues.calculateBottomPadding()
                            )
                    ) {
                        CurrentTab()
                    }
                }
            )
        }
    }
}
