package com.jvg.peopleapp.core.presentation.navigation.home.navigator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.common.Constants.bottomList
import com.jvg.peopleapp.core.presentation.navigation.home.routes.HomeScreens
import com.jvg.peopleapp.core.presentation.ui.components.AppBar
import com.jvg.peopleapp.core.presentation.ui.components.BottomBarComponent

object HomeNavigator : Screen {
    private fun readResolve(): Any = HomeNavigator

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        TabNavigator(
            tab = HomeScreens.Dashboard.tab,
            tabDisposable = { tabNavigator ->
                TabDisposable(navigator = tabNavigator, tabs = bottomList)
            },
            key = key
        ) { tabNavigator ->
            Scaffold(
                topBar = {
                    AppBar(
                        title = tabNavigator.current.options.title,
                        icon = painterResource(id = R.drawable.ic_home_24px)
                    )
                },
                bottomBar = {
                    BottomBarComponent(tabNavigator, navigator)
                },
                content = { paddingValues ->
                    Column(
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
