package com.sap.testretrofit.repositories

import com.sap.cpi_monitor.domain.resource.BaseModel
import com.sap.testretrofit.data.remote.MessageProcessingLogResponseDto

interface CpiRepo {
    suspend fun getCPIMessages(maxNumber: Int = 5, filter: String = "Status eq 'COMPLETED' or Status eq 'FAILED' or Status eq 'RETRY'"): BaseModel<MessageProcessingLogResponseDto>
}