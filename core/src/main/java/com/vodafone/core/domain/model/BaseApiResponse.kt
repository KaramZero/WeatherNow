package com.vodafone.core.domain.model

import com.google.gson.annotations.SerializedName


open class BaseApiResponse {
    @SerializedName("cod"        ) var cod        : Int?              = null
    @SerializedName("message"    ) var message    : String?           = null
    @SerializedName("parameters" ) var parameters : ArrayList<String> = arrayListOf()
}