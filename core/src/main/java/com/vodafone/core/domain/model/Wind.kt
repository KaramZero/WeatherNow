package com.vodafone.core.domain.model

import com.google.gson.annotations.SerializedName


data class Wind (

  @SerializedName("speed" ) var speed : Double? = null,
  @SerializedName("deg"   ) var deg   : Int?    = null

)