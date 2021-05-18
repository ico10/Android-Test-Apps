package com.sfa.android.currentbitcointracker.network

import com.squareup.moshi.Json

data class PriceIndex(
    val time: CurrentTime,
    val disclaimer: String,
    val chartName: String,
    val bpi: BitcoinIndex
)

data class CurrentTime(
    val updated: String,
    val updatedISO: String
)

data class BitcoinIndex(
    val USD: MyCurrency,
    val GBP: MyCurrency,
    val EUR: MyCurrency
)

data class MyCurrency(
    val code: String,
    val symbol: String,
    val rate: String,
    val description: String,
    @Json(name = "rate_float") val rateFloat: Double
)
