package com.jvg.peopleapp.core.mappers

import com.jvg.peopleapp.GetPaymentById
import com.jvg.peopleapp.GetPayments
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.people.domain.model.Person
import com.jvg.peopleapp.Payment as PaymentEntity

fun PaymentEntity.toDomain(): Payment = Payment(
    id = id,
    paymentMethod = paymentMethod,
    reference = reference,
    bank = bank,
    holderCode = holderCode,
    amount = amount,
    madeAt = madeAt,
    note = note,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
)

fun Payment.toDatabase(): PaymentEntity = PaymentEntity(
    id = id,
    paymentMethod = paymentMethod,
    reference = reference,
    bank = bank,
    holderCode = holderCode,
    amount = amount,
    madeAt = madeAt,
    note = note,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
    personId = person?.id.orEmpty()
)

fun GetPayments.toPayment(): Payment {
    val person = if (
        id_?.isNotEmpty() == true
    ) {
        Person(
            id = id_,
            name = name ?: "",
            lastname = lastname ?: "",
            code = code ?: "",
            phone = phone ?: "",
            email = email ?: "",
            startsAt = startsAt ?: "",
            finishesAt = finishesAt ?: "",
            active = active ?: true,
            createdAt = createdAt_ ?: "",
            updatedAt = updatedAt_ ?: "",
            deletedAt = deletedAt_,
        )
    } else {
        null
    }

    return Payment(
        id = id,
        paymentMethod = paymentMethod,
        reference = reference,
        bank = bank,
        holderCode = holderCode,
        amount = amount,
        madeAt = madeAt,
        note = note,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
        person = person
    )
}

fun GetPaymentById.toPayment(): Payment {
    val person = if (
        id_?.isNotEmpty() == true
    ) {
        Person(
            id = id_,
            name = name ?: "",
            lastname = lastname ?: "",
            code = code ?: "",
            phone = phone ?: "",
            email = email ?: "",
            startsAt = startsAt ?: "",
            finishesAt = finishesAt ?: "",
            active = active ?: true,
            createdAt = createdAt_ ?: "",
            updatedAt = updatedAt_ ?: "",
            deletedAt = deletedAt_,
        )
    } else {
        null
    }

    return Payment(
        id = id,
        paymentMethod = paymentMethod,
        reference = reference,
        bank = bank,
        holderCode = holderCode,
        amount = amount,
        madeAt = madeAt,
        note = note,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
        person = person
    )
}
