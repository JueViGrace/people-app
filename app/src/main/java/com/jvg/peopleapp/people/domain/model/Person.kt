package com.jvg.peopleapp.people.domain.model

import com.jvg.peopleapp.core.common.toCustomDateFormat
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.people.data.local.model.PersonEntity
import java.util.UUID

data class Person(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val lastname: String = "",
    val code: String = "",
    val phone: String = "",
    val email: String = "",
    val startsAt: String = "",
    val finishesAt: String = "",
    val active: Boolean = true,
    val createdAt: String = "",
    val updatedAt: String = "",
    val deletedAt: String? = null,
    val payments: List<Payment> = emptyList()
) {
    fun toDatabase(): PersonEntity = PersonEntity(
        id = id,
        name = name,
        lastname = lastname,
        code = code,
        phone = phone,
        email = email,
        startsAt = startsAt.toCustomDateFormat(),
        finishesAt = finishesAt.toCustomDateFormat(),
        active = active,
        payments = payments.map { it.toDatabase() }
    )
}
