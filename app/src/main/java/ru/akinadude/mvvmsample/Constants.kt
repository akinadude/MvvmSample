package ru.akinadude.mvvmsample

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

object Constants {
    val DATE_TIME_ZONE_FORMAT: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val DATE_FORMAT: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd")
}