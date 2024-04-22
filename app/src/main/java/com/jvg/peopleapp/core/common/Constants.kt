package com.jvg.peopleapp.core.common

import cafe.adriel.voyager.navigator.tab.Tab
import com.jvg.peopleapp.home.routes.HomeTabs
import com.jvg.peopleapp.payments.domain.model.Banks
import com.jvg.peopleapp.payments.domain.model.PaymentMethods

object Constants {
    const val PHONE_LENGTH: Int = 8
    const val DELAY: Long = 500
    const val DB_ERROR_MESSAGE: String = "Realm is not available"

    const val HOME = "Home"
    const val PEOPLE = "Personas"
    const val PAYMENTS = "Pagos"

    val bottomList: List<Tab> = listOf(
        HomeTabs.Dashboard.tab,
        HomeTabs.People.tab,
        HomeTabs.Payments.tab
    )

    val banksList: List<Banks> = listOf(
        Banks.BNC,
        Banks.Banesco
    )

    val paymentMethodsList: List<PaymentMethods> = listOf(
        PaymentMethods.Cash,
        PaymentMethods.Transfer
    )
}
