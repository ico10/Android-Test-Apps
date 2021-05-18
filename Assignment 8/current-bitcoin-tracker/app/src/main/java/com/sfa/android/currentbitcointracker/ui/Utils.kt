package com.sfa.android.currentbitcointracker.ui

import com.sfa.android.currentbitcointracker.network.DatabaseCurrency
import com.sfa.android.currentbitcointracker.network.MyCurrency
import java.lang.StringBuilder
import java.util.*

private const val GBP_CODE = "GBP"

fun formatCurrencyText(myCurrency: MyCurrency): String {
    val sb = StringBuilder()
    sb.append(myCurrency.description).append("\n")
    if (myCurrency.code == GBP_CODE){
        sb.append(Currency.getInstance(myCurrency.code).symbol).append((myCurrency.rateFloat))
    } else {
        sb.append((myCurrency.rateFloat)).append(Currency.getInstance(myCurrency.code).symbol)
    }
    sb.append(" (${myCurrency.code})")

    return sb.toString()
}

fun formatCurrencyTextFromDatabase(databaseCurrency: DatabaseCurrency): String {
    val sb = StringBuilder()
    sb.append(databaseCurrency.getCurrentTime()).append("\n")
    if (databaseCurrency.getCode() == GBP_CODE){
        sb.append(Currency.getInstance(databaseCurrency.getCode()).symbol).append((databaseCurrency.getRate()))
    } else {
        sb.append((databaseCurrency.getRate())).append(Currency.getInstance(databaseCurrency.getCode()).symbol)
    }
    sb.append(" (${databaseCurrency.getCode()})")

    return sb.toString()
}
