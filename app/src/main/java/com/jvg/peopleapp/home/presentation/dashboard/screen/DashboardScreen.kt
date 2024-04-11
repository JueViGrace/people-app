package com.jvg.peopleapp.home.presentation.dashboard.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jvg.peopleapp.core.presentation.ui.components.AppBar
import com.jvg.peopleapp.core.presentation.ui.components.FABComponent
import com.jvg.peopleapp.home.presentation.dashboard.components.DashboardContent
import com.jvg.peopleapp.home.presentation.dashboard.viewmodel.DashborardViewModel
import com.jvg.peopleapp.home.presentation.events.PeopleActions
import com.jvg.peopleapp.home.presentation.navigation.routes.HomeScreens

object DashboardScreen : Screen {
    private fun readResolve(): Any = DashboardScreen

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<DashborardViewModel>()
        val state by viewModel.state.collectAsState()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AppBar()
            },
            floatingActionButton = {
                FABComponent(
                    onAdd = {
                        navigator.push(HomeScreens.PersonDetails().screen)
                    }
                )
            },
            content = { paddingValues ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding()
                        )
                ) {
                    DashboardContent(
                        modifier = Modifier.weight(1f),
                        people = state.activePeople,
                        showActive = true,
                        onSelect = { person ->
                            navigator.push(HomeScreens.PersonDetails(person).screen)
                        },
                        onActive = { id, isActive: Boolean ->
                            viewModel.setActions(action = PeopleActions.SetActive(id, isActive))
                        },
                        onDelete = { id ->
                            viewModel.setActions(action = PeopleActions.Delete(id))
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 5.dp))

                    DashboardContent(
                        modifier = Modifier.weight(1f),
                        people = state.inactivePeople,
                        showActive = false,
                        onSelect = { person ->
                            navigator.push(HomeScreens.PersonDetails(person).screen)
                        },
                        onActive = { id, isActive: Boolean ->
                            viewModel.setActions(action = PeopleActions.SetActive(id, isActive))
                        },
                        onDelete = { id ->
                            viewModel.setActions(action = PeopleActions.Delete(id))
                        }
                    )
                }
            }
        )
    }
}
