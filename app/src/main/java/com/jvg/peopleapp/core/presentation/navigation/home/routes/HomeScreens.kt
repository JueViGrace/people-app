package com.jvg.peopleapp.core.presentation.navigation.home.routes

import cafe.adriel.voyager.navigator.tab.Tab
import com.jvg.peopleapp.dashboard.presentation.screen.DashboardTab
import com.jvg.peopleapp.person.presentation.screen.PeopleTab

sealed class HomeScreens(val tab: Tab) {
    data object Dashboard : HomeScreens(tab = DashboardTab)
    data object People : HomeScreens(tab = PeopleTab)
}
