package com.jvg.peopleapp.payments.presentation.payment.state

import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.people.domain.model.Person
import org.mongodb.kbson.ObjectId

data class PaymentDetailsState(
    val payment: RequestState<Payment> = RequestState.Loading,
    val people: RequestState<List<Person>> = RequestState.Idle,
    val selectedPayment: ObjectId? = null,
    val referenceError: String? = null,
    val bankError: String? = null,
    val holderCodeError: String? = null,
    val madeAtError: String? = null,
    val amountError: String? = null,
    val personError: String? = null,
    val errors: Boolean? = null
)
