package com.jvg.peopleapp.home.navigator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.jvg.peopleapp.core.common.Constants.bottomList
import com.jvg.peopleapp.core.presentation.ui.components.BottomBarComponent
import com.jvg.peopleapp.home.routes.HomeTabs

object HomeNavigator : Screen {
    private fun readResolve(): Any = HomeNavigator

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        TabNavigator(
            tab = HomeTabs.Dashboard.tab,
            tabDisposable = { tabNavigator ->
                TabDisposable(navigator = tabNavigator, tabs = bottomList)
            },
            key = key
        ) { tabNavigator ->
            Scaffold(
                bottomBar = {
                    BottomBarComponent(
                        tabNavigator
                            /*onAdd = {
                                peopleViewModel.onEvent(PersonEvent.OnAddNewPerson)
                                navigator.push(DetailScreens.CreatePerson(peopleViewModel.newPerson).screen)
                            },
                            icon = when (tabNavigator.current) {
                                HomeTabs.People.tab -> {
                                    rememberVectorPainter(image = Icons.Default.Edit)
                                }

                                else -> {
                                    rememberVectorPainter(image = Icons.Default.Add)
                                }
                            }*/
                    )
                },
                content = { paddingValues ->
                    Box(
                        modifier = Modifier
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
