package com.jvg.peopleapp.core.presentation.navigation.home.routes

import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.common.Constants.HOME
import com.jvg.peopleapp.core.common.Constants.PEOPLE

sealed class BottomNavItem(val index: UShort, val title: String, val icon: Int) {
    data object DashboardTab : BottomNavItem(
        index = 0u,
        title = HOME,
        icon = R.drawable.ic_home_app_logo_24px
    )

    data object PeopleTab : BottomNavItem(
        index = 1u,
        title = PEOPLE,
        icon = R.drawable.ic_person_24px
    )
}
