package com.sap.testretrofit.roomDB

import com.sap.testretrofit.sessionManager.TenantData

class TenantDBtoSharedPref_mapper() {
    companion object{
        fun mapToTenantData(tenantEntity: TenantEntity): TenantData {
            return TenantData(
                clientId = tenantEntity.clientId,
                clientSecret = tenantEntity.clientSecret,
                url = tenantEntity.urlMoni,
                tokenUrl = tenantEntity.urlAuth
            )
        }
    }
}