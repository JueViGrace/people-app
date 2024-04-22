package com.jvg.peopleapp.payments.domain.model

import com.jvg.peopleapp.payments.data.local.model.PaymentEntity
import com.jvg.peopleapp.people.domain.model.Person
import java.util.UUID

data class Payment(
    var id: String = UUID.randomUUID().toString(),
    val paymentMethod: String = "",
    val reference: String? = "",
    val bank: String? = null,
    val holderCode: String? = "",
    val amount: Double = 0.0,
    val madeAt: String = "",
    val note: String = "",
    val createdAt: String = "",
    val person: Person? = null
) {
    fun toDatabase(): PaymentEntity = PaymentEntity(
        id = id,
        paymentMethod = paymentMethod,
        reference = reference,
        bank = bank,
        holderCode = holderCode,
        amount = amount,
        madeAt = madeAt,
        note = note,
        person = person?.toDatabase()
    )
}
