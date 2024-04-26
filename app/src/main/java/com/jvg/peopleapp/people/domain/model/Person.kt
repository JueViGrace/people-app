package com.jvg.peopleapp.people.domain.model

import com.jvg.peopleapp.payments.domain.model.Payment

// TODO: CREATE STUDENT MODEL

data class Person(
    val id: String = "",
    val name: String = "",
    val lastname: String = "",
    val code: String = "",
    val birthDay: String = "", // <---new
    val address: String = "", // <---new
    val phone: String = "",
    val email: String = "",
    val emergencyPhone: String = "", // <---new
    val startsAt: String = "",
    val finishesAt: String = "",
    val active: Boolean = true,
    val createdAt: String = "",
    val updatedAt: String = "",
    val deletedAt: String? = null,
    val payments: List<Payment> = emptyList()
)
