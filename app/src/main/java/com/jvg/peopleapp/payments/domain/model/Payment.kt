package com.jvg.peopleapp.payments.domain.model

import com.jvg.peopleapp.people.domain.model.Person

data class Payment(
    val id: String = "",
    val paymentMethod: String = PaymentMethods.Cash.method,
    val reference: String = "",
    val bank: String = "",
    val holderCode: String = "",
    val amount: Double = 0.0,
    val madeAt: String = "",
    val note: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val deletedAt: String? = null,
    val person: Person? = null
)
