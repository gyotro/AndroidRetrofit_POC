package com.sap.testretrofit.repositories

import java.time.LocalDateTime

class FilterBuilder(
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime = LocalDateTime.now(),
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