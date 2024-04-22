package com.jvg.peopleapp.people.data.local.model

import com.jvg.peopleapp.core.common.toStringFormat
import com.jvg.peopleapp.payments.data.local.model.PaymentEntity
import com.jvg.peopleapp.people.domain.model.Person
import java.util.Date
import java.util.UUID

data class PersonEntity(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val lastname: String = "",
    val code: String = "",
    val phone: String = "",
    val email: String = "",
    val startsAt: Date = Date(),
    val finishesAt: Date = Date(),
    val active: Boolean = true,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val deletedAt: Date? = null,
    val payments: List<PaymentEntity> = emptyList()
) {
    fun toDomain(): Person = Person(
        id = id,
        name = name,
        lastname = lastname,
        code = code,
        phone = phone,
        email = email,
        startsAt = startsAt.toStringFormat(1),
        finishesAt = finishesAt.toStringFormat(1),
        active = active,
        createdAt = createdAt.toStringFormat(1),
        updatedAt = updatedAt.toStringFormat(1),
        deletedAt = deletedAt?.toStringFormat(1),
        payments = payments.map { it.toDomain() }
    )
}
