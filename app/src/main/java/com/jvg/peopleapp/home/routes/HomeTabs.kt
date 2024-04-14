package com.jvg.peopleapp.home.routes

import cafe.adriel.voyager.navigator.tab.Tab
import com.jvg.peopleapp.dashboard.presentation.navigation.tab.DashboardTab
import com.jvg.peopleapp.people.presentation.navigation.tab.PeopleTab

sealed class HomeTabs(val tab: Tab) {
    data object Dashboard : HomeTabs(tab = DashboardTab)
    data object People : HomeTabs(tab = PeopleTab)
}
