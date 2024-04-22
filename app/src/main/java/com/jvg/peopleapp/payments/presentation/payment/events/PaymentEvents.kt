package com.jvg.peopleapp.payments.presentation.payment.events

import com.jvg.peopleapp.payments.domain.model.Banks
import com.jvg.peopleapp.people.domain.model.Person

sealed interface PaymentEvents {
    data class OnPaymentMethodChanged(val value: String) : PaymentEvents
    data class OnReferenceChanged(val value: String) : PaymentEvents
    data class OnBankChanged(val value: Banks) : PaymentEvents
    data class OnHolderCodeChanged(val value: String) : PaymentEvents
    data class OnMadeAtChanged(val value: String) : PaymentEvents
    data class OnAmountChanged(val value: Double) : PaymentEvents
    data class OnNoteChanged(val value: String) : PaymentEvents
    data class OnPersonChanged(val value: Person) : PaymentEvents
    data object SavePayment : PaymentEvents
    data object DismissPayment : PaymentEvents
    data object DeletePayment : PaymentEvents
}
