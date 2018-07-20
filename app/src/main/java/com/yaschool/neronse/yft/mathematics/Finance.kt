package com.yaschool.neronse.yft.mathematics

import com.yaschool.neronse.yft.data.Operation
import com.yaschool.neronse.yft.data.Valute
import java.math.BigDecimal
import java.math.RoundingMode

fun calculateBalance(list: List<Operation>): String {
    val currentValute: Valute = list.first().valute
    var balance: BigDecimal = BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN)
    for (operation in list) {
        if (currentValute != operation.valute) throw IllegalArgumentException("Only one valute in list")
        if (operation.operationType){
            balance += operation.count
        }else
            balance -= operation.count
    }
    return balance.toPlainString()
}