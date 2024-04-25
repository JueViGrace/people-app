package com.jvg.peopleapp.people.domain.rules

import com.google.common.truth.Truth.assertThat
import com.jvg.peopleapp.people.domain.model.Person
import org.junit.Test

class PersonValidatorTest {

    @Test
    fun `name provided is blank returns `() {
        val result = PersonValidator.validatePerson(
            person = Person(
                name = "",
                email = ""
            )
        )

        assertThat(result.nameError).isNotEmpty()
    }
}
