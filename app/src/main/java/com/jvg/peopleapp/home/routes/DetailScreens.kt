package com.jvg.peopleapp.home.routes

import cafe.adriel.voyager.core.screen.Screen
import com.jvg.peopleapp.payments.presentation.payment.screens.CreatePaymentScreen
import com.jvg.peopleapp.payments.presentation.payment.screens.PaymentDetailsScreen
import com.jvg.peopleapp.people.presentation.person.screens.CreatePersonScreen
import com.jvg.peopleapp.people.presentation.person.screens.PersonDetailsScreen
import org.mongodb.kbson.ObjectId

sealed class DetailScreens(val screen: Screen) {
    data class PersonDetails(val id: ObjectId) : DetailScreens(screen = PersonDetailsScreen(id))
    data class CreatePerson(val id: ObjectId? = null) : DetailScreens(screen = CreatePersonScreen(id))
    data class PaymentDetails(val id: ObjectId) : DetailScreens(screen = PaymentDetailsScreen(id))
    data class CreatePayment(val id: ObjectId? = null) : DetailScreens(screen = CreatePaymentScreen(id))
}
