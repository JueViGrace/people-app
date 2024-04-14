package com.jvg.peopleapp.core.common

import cafe.adriel.voyager.navigator.tab.Tab
import com.jvg.peopleapp.home.routes.HomeTabs

object Constants {
    const val CODE_LENGTH: Int = 8
    const val PHONE_LENGTH: Int = 8
    const val DELAY: Long = 500
    const val DB_ERROR_MESSAGE: String = "Realm is not available"
    const val IO_DISPATCHER = "ioDispatcher"
    const val MAIN_DISPATCHER = "mainDispatcher"

    const val HOME = "Home"
    const val PEOPLE = "People"

    val bottomList: List<Tab> = listOf(
        HomeTabs.Dashboard.tab,
        HomeTabs.People.tab,
    )
}
