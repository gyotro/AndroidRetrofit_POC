package com.sap.testretrofit.repositories

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class FilterBuilder(
    val startDateTime: LocalDateTime = LocalDateTime.of( LocalDate.now(), LocalTime.of(0,0,1) ),
    val endDateTime: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
    val status: List<Status> = emptyList<Status>(),
    val nameFlow: String? = null
) {
    fun buildDateFilter() = "LogStart le datetime'${endDateTime}' and LogStart ge datetime'${startDateTime}'"

    fun buildStatusFilter(): String {
        var filter = ""
        if (status.isNotEmpty()) {
            filter = "(Status eq "
            this.status.forEachIndexed { index, statusItem ->
                filter += "'${statusItem.toString()}'"
                if (index < this.status.size - 1)
                    filter += " or Status eq"
            }
        }
        return "$filter)"
    }
    fun buildNameFilter(): String? = nameFlow?.also { return "substringof('${it}',IntegrationFlowName)" }


    fun buildTotalFilter(): String {
        var totalFilter = buildDateFilter()
        if (status.isNotEmpty())
            totalFilter += " and ${buildStatusFilter()}"
        if (!buildNameFilter().isNullOrBlank())
            totalFilter += " and ${buildNameFilter()}"
        return totalFilter
    }
}