package com.sap.testretrofit.repositories

import com.sap.cpi_monitor.domain.resource.BaseModel
import com.sap.testretrofit.data.remote.MessageProcessingLogResponseDto

interface CpiRepo {
    suspend fun getCPIMessages(maxNumber: Int = 5): BaseModel<MessageProcessingLogResponseDto>
}