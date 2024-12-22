package com.vodafone.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "city")
data class City(
    @PrimaryKey
    @SerializedName("id"            ) var id        : Int     = 1,
    @SerializedName("name"          ) var name      : String?  = null,
)