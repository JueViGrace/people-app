package com.jvg.peopleapp.people.presentation.navigation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.jvg.peopleapp.core.presentation.ui.components.AppBar
import com.jvg.peopleapp.home.routes.DetailScreens
import com.jvg.peopleapp.people.presentation.components.DashboardContent
import com.jvg.peopleapp.people.presentation.person.events.PersonEvent
import com.jvg.peopleapp.people.presentation.viewmodel.PeopleViewModel

object PeopleScreen : Screen {
    private fun readResolve(): Any = PeopleScreen

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val tabNavigator = LocalTabNavigator.current
        val viewModel = navigator.getNavigatorScreenModel<PeopleViewModel>()
        val state by viewModel.state.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AppBar(
                title = tabNavigator.current.options.title,
                icon = tabNavigator.current.options.icon,
            )

            DashboardContent(
                modifier = Modifier.fillMaxSize(),
                people = state.people,
                showActive = true,
                onSelect = { person ->
                    viewModel.onEvent(PersonEvent.SelectedPerson(person))
                    navigator.parent?.parent?.push(DetailScreens.PersonDetails(person).screen)
                },
                onActive = { id, isActive: Boolean ->
                    viewModel.onEvent(PersonEvent.SetActive(id, isActive))
                },
                onDelete = { id ->
                    viewModel.onEvent(PersonEvent.DeletePerson(id))
                }
            )

            /*HorizontalDivider(modifier = Modifier.padding(vertical = 5.dp))

            DashboardContent(
                modifier = Modifier.weight(1f),
                people = state.inactivePeople,
                showActive = false,
                onSelect = { person ->
                    navigator.parent?.parent?.push(DetailScreens.PersonDetails(person).screen)
                },
                onActive = { id, isActive: Boolean ->
                    viewModel.onEvent(PersonEvent.SetActive(id, isActive))
                },
                onDelete = { id ->
                    viewModel.onEvent(PersonEvent.DeletePerson(id))
                }
            )*/
        }
    }
}
