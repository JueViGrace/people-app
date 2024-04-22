package com.jvg.peopleapp.payments.data.local.model

import com.jvg.peopleapp.core.common.toStringFormat
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.people.data.local.model.PersonEntity
import java.util.Date
import java.util.UUID

data class PaymentEntity(
    val id: String = UUID.randomUUID().toString(),
    val paymentMethod: String = "",
    val reference: String? = "",
    val bank: String? = null,
    val holderCode: String? = "",
    val amount: Double = 0.0,
    val madeAt: String = "",
    val note: String = "",
    val createdAt: Date = Date(),
    val person: PersonEntity? = null
) {
    fun toDomain(): Payment = Payment(
        id = id,
        paymentMethod = paymentMethod,
        reference = reference,
        bank = bank,
        holderCode = holderCode,
        amount = amount,
        madeAt = madeAt,
        note = note,
        createdAt = createdAt.toStringFormat(1),
        person = person?.toDomain()
    )
}
