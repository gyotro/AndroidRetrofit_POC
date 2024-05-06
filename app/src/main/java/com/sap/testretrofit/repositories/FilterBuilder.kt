package com.sap.testretrofit.repositories

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class FilterBuilder(
    val startDateTime: LocalDateTime = LocalDateTime.of( LocalDate.now(), LocalTime.of(0,0,1) ),
    val endDateTime: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
    val status: List<Status>,
    val name: String
) {
    fun buildDateFilter(): String {

    }
    fun buildStatusFilter(): String {

    }
    fun buildNameFilter(): String {

    }
}