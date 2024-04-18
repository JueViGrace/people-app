package com.jvg.peopleapp.home.routes

import cafe.adriel.voyager.navigator.tab.Tab
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.common.Constants.HOME
import com.jvg.peopleapp.core.common.Constants.PAYMENTS
import com.jvg.peopleapp.core.common.Constants.PEOPLE
import com.jvg.peopleapp.dashboard.presentation.navigation.tab.DashboardTab
import com.jvg.peopleapp.payments.presentation.navigation.tab.PaymentsTab
import com.jvg.peopleapp.people.presentation.navigation.tab.PeopleTab

sealed class HomeTabs(val tab: Tab, val index: UShort, val title: String, val icon: Int) {
    data object Dashboard : HomeTabs(
        tab = DashboardTab,
        index = 0u,
        title = HOME,
        icon = R.drawable.ic_home_app_logo_24px
    )
    data object People : HomeTabs(
        tab = PeopleTab,
        index = 1u,
        title = PEOPLE,
        icon = R.drawable.ic_person_24px
    )
    data object Payments : HomeTabs(
        tab = PaymentsTab,
        index = 2u,
        title = PAYMENTS,
        icon = R.drawable.ic_payments_24px
    )
}
