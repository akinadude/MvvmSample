package ru.akinadude.mvvmsample.model

import org.joda.time.LocalDate

data class Due(
    val recurring: Boolean,
    val string: String,
    val date: LocalDate
)