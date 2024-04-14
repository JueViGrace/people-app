package com.jvg.peopleapp.people.presentation.navigation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
import com.jvg.peopleapp.people.presentation.components.DashboardContent
import com.jvg.peopleapp.people.presentation.events.PeopleActions
import com.jvg.peopleapp.people.presentation.person.navigation.DetailScreens
import com.jvg.peopleapp.people.presentation.viewmodel.PeopleViewModel

object PeopleScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<PeopleViewModel>()
        val state by viewModel.state.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DashboardContent(
                modifier = Modifier.weight(1f),
                people = state.activePeople,
                showActive = true,
                onSelect = { person ->
                    navigator.push(DetailScreens.PersonDetails(person).screen)
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
                    navigator.push(DetailScreens.PersonDetails(person).screen)
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
}
