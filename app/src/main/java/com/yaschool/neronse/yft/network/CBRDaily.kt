package com.yaschool.neronse.yft.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CBRDaily(
        @SerializedName("Date")
        @Expose
        var date: String? = null,

        @SerializedName("PreviousDate")
        @Expose
        var previousDate: String? = null,

        @SerializedName("PreviousURL")
        @Expose
        var previousURL: String? = null,

        @SerializedName("Timestamp")
        @Expose
        var timestamp: String? = null,
        @SerializedName("Valute")
        @Expose
        var valute: Valute? = null
)


data class Valute(@SerializedName("USD")
                  @Expose
                  var usd: USD? = null)


data class USD(
        @SerializedName("ID")
        @Expose
        var id: String? = null,
        @SerializedName("NumCode")
        @Expose
        var numCode: String? = null,
        @SerializedName("CharCode")
        @Expose
        var charCode: String? = null,
        @SerializedName("Nominal")
        @Expose
        var nominal: Int = 0,
        @SerializedName("Name")
        @Expose
        var name: String? = null,
        @SerializedName("Value")
        @Expose
        var value: Double = 0.toDouble(),
        @SerializedName("Previous")
        @Expose
        var previous: Double = 0.toDouble()
)

