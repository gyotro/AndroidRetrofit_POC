package com.sap.testretrofit.repositories

import com.sap.cpi_monitor.domain.resource.BaseModel
import com.sap.testretrofit.data.remote.MessageProcessingLogResponseDto
import com.sap.testretrofit.data.remote.MonitorRepository
import retrofit2.Response

class CpiRepoImpl(
    private val api: MonitorRepository
): CpiRepo {
    override suspend fun getCPIMessages(maxNumber: Int):BaseModel<MessageProcessingLogResponseDto> {
        return request { api.getInterfaces(top = maxNumber) }
    }
    private suspend fun<T> request(request: suspend ()-> Response<T>): BaseModel<T> {
        try {
            request().also {
                return if (it.isSuccessful) {
                    BaseModel.Success(it.body()!!)
                } else {
                    BaseModel.Error(it.errorBody()?.string().toString())
                }
            }
        } catch (e: Exception) {
            return BaseModel.Error(e.message.toString())
        }
    }
}