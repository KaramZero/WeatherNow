package com.vodafone.core.domain.model

import com.google.gson.annotations.SerializedName


data class ForecastResponse (

  @SerializedName("cnt"     ) var cnt     : Int?            = null,
  @SerializedName("list"    ) var forecasts    : ArrayList<WeatherForecast>  = arrayListOf(),
  @SerializedName("city"    ) var city    : City?           = City()

) : BaseApiResponse()