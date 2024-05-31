package com.sap.testretrofit.repositories

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class FilterBuilder(
    val startDateTime: LocalDateTime = LocalDateTime.of( LocalDate.now(ZoneId.systemDefault()), LocalTime.of(0,0,1) ),
    val endDateTime: LocalDateTime = LocalDateTime.now(ZoneId.systemDefault()).truncatedTo(ChronoUnit.SECONDS),
    val status: List<Status> = emptyList<Status>(),
    val nameFlow: String? = null
) {
    fun buildDateFilter() = "LogStart le datetime'${endDateTime}' and LogStart ge datetime'${startDateTime}'"

    fun buildStatusFilter(): String {
        var filter = ""
        if (status.isNotEmpty()) {
            filter = "(Status eq "
            this.status.forEachIndexed { index, statusItem ->
                filter += "'$statusItem'"
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
    companion object {
        fun getDateTimeFromString(dateString: String): LocalDateTime {
            val pattern = "yyyy-MM-dd HH:mm:ss"
            val formatter = DateTimeFormatter.ofPattern(pattern)

            // Parse a string into a LocalDateTime object using the formatter
            return LocalDateTime.parse(dateString, formatter)
        }
        fun localDateToEpoch(localDate: LocalDate): Long {
            val localDateTime = localDate.atStartOfDay()
            val instant = localDateTime.toInstant(ZoneOffset.UTC)
            return instant.epochSecond
        }
        fun epochMillisToLocalDate(epochMillis: Long): LocalDate {
            val instant = Instant.ofEpochMilli(epochMillis)
            return instant.atZone(ZoneId.systemDefault()).toLocalDate()
        }
        fun timeFormatter(num: Int): String {
            return if(num < 10)
                "0"+num
            else
                ""+num
        }
    }
}