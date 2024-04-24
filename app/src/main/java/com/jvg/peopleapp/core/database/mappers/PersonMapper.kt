package com.jvg.peopleapp.core.database.mappers

import com.jvg.peopleapp.GetPersonById
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.people.domain.model.Person
import com.jvg.peopleapp.Person as PersonEntity

fun PersonEntity.toDomain(): Person = Person(
    id = id,
    name = name,
    lastname = lastname,
    code = code,
    phone = phone,
    email = email,
    startsAt = startsAt,
    finishesAt = finishesAt,
    active = active,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun Person.toDatabase(): PersonEntity = PersonEntity(
    id = id,
    name = name,
    lastname = lastname,
    code = code,
    phone = phone,
    email = email,
    startsAt = startsAt,
    finishesAt = finishesAt,
    active = active,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun List<GetPersonById>.toPerson(): Person {
    var person = Person()
    if (this.size == 1) {
        this.map { getPersonById ->
            val payments = if (getPersonById.id_?.isNotEmpty() == true) {
                listOf(
                    Payment(
                        id = getPersonById.id_,
                        paymentMethod = getPersonById.paymentMethod ?: "",
                        reference = getPersonById.reference ?: "",
                        bank = getPersonById.bank ?: "",
                        holderCode = getPersonById.holderCode ?: "",
                        amount = getPersonById.amount ?: 0.0,
                        madeAt = getPersonById.madeAt ?: "",
                        note = getPersonById.note ?: "",
                        createdAt = getPersonById.createdAt_ ?: "",
                        updatedAt = getPersonById.updatedAt_ ?: "",
                        deletedAt = getPersonById.deletedAt_,
                        person = Person(
                            id = getPersonById.id,
                            name = getPersonById.name,
                            lastname = getPersonById.lastname,
                            code = getPersonById.code,
                            phone = getPersonById.phone,
                            email = getPersonById.email,
                            startsAt = getPersonById.startsAt,
                            finishesAt = getPersonById.finishesAt,
                            active = getPersonById.active,
                            createdAt = getPersonById.createdAt,
                            updatedAt = getPersonById.updatedAt,
                            deletedAt = getPersonById.deletedAt
                        )
                    )
                )
            } else {
                emptyList()
            }

            person = Person(
                id = getPersonById.id,
                name = getPersonById.name,
                lastname = getPersonById.lastname,
                code = getPersonById.code,
                phone = getPersonById.phone,
                email = getPersonById.email,
                startsAt = getPersonById.startsAt,
                finishesAt = getPersonById.finishesAt,
                active = getPersonById.active,
                createdAt = getPersonById.createdAt,
                updatedAt = getPersonById.updatedAt,
                deletedAt = getPersonById.deletedAt,
                payments = payments
            )
        }
    } else {
        this.groupBy { getPerson ->
            Person(
                id = getPerson.id,
                name = getPerson.name,
                lastname = getPerson.lastname,
                code = getPerson.code,
                phone = getPerson.phone,
                email = getPerson.email,
                startsAt = getPerson.startsAt,
                finishesAt = getPerson.finishesAt,
                active = getPerson.active,
                createdAt = getPerson.createdAt,
                updatedAt = getPerson.updatedAt,
                deletedAt = getPerson.deletedAt,
            )
        }.map { map ->
            val payments = map.value.map { getPersonById ->
                Payment(
                    id = getPersonById.id_ ?: "",
                    paymentMethod = getPersonById.paymentMethod ?: "",
                    reference = getPersonById.reference ?: "",
                    bank = getPersonById.bank ?: "",
                    holderCode = getPersonById.holderCode ?: "",
                    amount = getPersonById.amount ?: 0.0,
                    madeAt = getPersonById.madeAt ?: "",
                    note = getPersonById.note ?: "",
                    createdAt = getPersonById.createdAt_ ?: "",
                    updatedAt = getPersonById.updatedAt_ ?: "",
                    deletedAt = getPersonById.deletedAt_,
                    person = Person(
                        id = map.key.id,
                        name = map.key.name,
                        lastname = map.key.lastname,
                        code = map.key.code,
                        phone = map.key.phone,
                        email = map.key.email,
                        startsAt = map.key.startsAt,
                        finishesAt = map.key.finishesAt,
                        active = map.key.active,
                        createdAt = map.key.createdAt,
                        updatedAt = map.key.updatedAt,
                        deletedAt = map.key.deletedAt
                    )
                )
            }

            person = Person(
                id = map.key.id,
                name = map.key.name,
                lastname = map.key.lastname,
                code = map.key.code,
                phone = map.key.phone,
                email = map.key.email,
                startsAt = map.key.startsAt,
                finishesAt = map.key.finishesAt,
                active = map.key.active,
                createdAt = map.key.createdAt,
                updatedAt = map.key.updatedAt,
                deletedAt = map.key.deletedAt,
                payments = payments.ifEmpty { emptyList() }
            )
        }
    }

    return person
}
