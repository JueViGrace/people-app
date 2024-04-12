package com.jvg.peopleapp.person.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.jvg.peopleapp.core.presentation.navigation.details.DetailScreens
import com.jvg.peopleapp.core.presentation.navigation.home.routes.BottomNavItem
import com.jvg.peopleapp.dashboard.presentation.components.DashboardContent
import com.jvg.peopleapp.dashboard.presentation.viewmodel.DashborardViewModel
import com.jvg.peopleapp.person.presentation.events.PeopleActions

object PeopleTab : Tab {
    private fun readResolve(): Any = PeopleTab

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(id = BottomNavItem.PeopleTab.icon)
            return remember {
                TabOptions(
                    index = BottomNavItem.PeopleTab.index,
                    title = BottomNavItem.PeopleTab.title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<DashborardViewModel>()
        val state by viewModel.state.collectAsState()
        // TODO: CREAR NAVIGATOR
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
