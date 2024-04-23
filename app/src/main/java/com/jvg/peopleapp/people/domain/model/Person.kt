package com.jvg.peopleapp.people.domain.model

import com.jvg.peopleapp.payments.domain.model.Payment

data class Person(
    val id: String = "",
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
)
