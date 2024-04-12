package com.jvg.peopleapp.core.presentation.navigation.home.navigator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.jvg.peopleapp.core.common.Constants.bottomList
import com.jvg.peopleapp.core.presentation.navigation.home.routes.HomeScreens
import com.jvg.peopleapp.core.presentation.ui.components.BottomBarComponent
import com.jvg.peopleapp.core.presentation.ui.components.CustomText

object HomeNavigator : Screen {
    private fun readResolve(): Any = HomeNavigator

    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalMaterial3Api::class)
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
                    CenterAlignedTopAppBar(
                        title = {
                            CustomText(
                                text = tabNavigator.current.options.title,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        navigationIcon = {
                            Icon(
                                modifier = Modifier.padding(start = 10.dp),
                                painter = tabNavigator.current.options.icon!!,
                                contentDescription = tabNavigator.current.options.title,
                            )
                        },
                        actions = {
                            if (tabNavigator.current.key == HomeScreens.People.tab.key){
                                /*IconButton(onClick = {

                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_back_icon),
                                        contentDescription = "Filter"
                                    )
                                }*/

                            }
                        }
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
