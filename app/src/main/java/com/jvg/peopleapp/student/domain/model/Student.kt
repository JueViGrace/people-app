package com.jvg.peopleapp.student.domain.model

import com.jvg.peopleapp.people.domain.model.Person

data class Student(
    val startDate: String = "",
    val belt: String = "",
    val exams: List<String> = emptyList(),
    val representativeName: String = "",
    val person: Person? = null
)
