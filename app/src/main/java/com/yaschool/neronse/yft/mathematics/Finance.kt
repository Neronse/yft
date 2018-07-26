package com.yaschool.neronse.yft.mathematics

import com.yaschool.neronse.yft.data.Currency
import com.yaschool.neronse.yft.data.Operation
import com.yaschool.neronse.yft.data.OperationType
import java.math.BigDecimal
import java.math.RoundingMode

fun calculateBalance(list: List<Operation>): String {
    val currentCurrency: Currency = list.first().currency
    var balance: BigDecimal = BigDecimal("0").setScale(2, RoundingMode.HALF_EVEN)
    list.forEach {
        if (currentCurrency != it.currency) throw IllegalArgumentException("Only one currency in list")

        when(it.operationType){
            OperationType.INCOME -> balance += it.count
            OperationType.EXPENSE -> balance -= it.count
        }  }

    return balance.toPlainString()
}