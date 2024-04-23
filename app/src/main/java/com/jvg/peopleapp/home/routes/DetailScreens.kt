package com.jvg.peopleapp.home.routes

import cafe.adriel.voyager.core.screen.Screen
import com.jvg.peopleapp.payments.presentation.payment.screens.CreatePaymentScreen
import com.jvg.peopleapp.payments.presentation.payment.screens.PaymentDetailsScreen
import com.jvg.peopleapp.people.presentation.person.screens.CreatePersonScreen
import com.jvg.peopleapp.people.presentation.person.screens.PersonDetailsScreen

sealed class DetailScreens(val screen: Screen) {
    data class PersonDetails(val id: String) : DetailScreens(screen = PersonDetailsScreen(id))
    data class CreatePerson(val id: String? = null) : DetailScreens(screen = CreatePersonScreen(id))
    data class PaymentDetails(val id: String) : DetailScreens(screen = PaymentDetailsScreen(id))
    data class CreatePayment(val id: String? = null) : DetailScreens(screen = CreatePaymentScreen(id))
}
