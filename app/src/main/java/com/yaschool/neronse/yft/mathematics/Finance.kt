package com.yaschool.neronse.yft.mathematics

import com.yaschool.neronse.yft.data.Currency
import com.yaschool.neronse.yft.data.Operation
import com.yaschool.neronse.yft.data.OperationType
import java.math.BigDecimal
import java.math.RoundingMode

fun calculateBalance(list: List<Operation>): String {
    if (list.isEmpty()) return "0.00"
    val currentCurrency: Currency = list.first().currency
    var balance: BigDecimal = BigDecimal("0").setScale(2, RoundingMode.HALF_EVEN)
    list.forEach {
        if (currentCurrency != it.currency) throw IllegalArgumentException("Only one currency in list")

        when(it.operationType){
            OperationType.INCOME -> balance += it.count.setScale(2,RoundingMode.HALF_EVEN)
            OperationType.EXPENSE -> balance -= it.count.setScale(2,RoundingMode.HALF_EVEN)
        }  }
    return balance.toPlainString()
}

fun calculateExpense(list: List<Operation>):Float {
    if (list.isEmpty()) return 0.00f
    return list.fold(BigDecimal(0)) { totalExpense, operation ->
        totalExpense.plus(operation.count)
    }.toFloat()

}