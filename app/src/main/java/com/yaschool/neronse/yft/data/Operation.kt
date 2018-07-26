package com.yaschool.neronse.yft.data

import java.math.BigDecimal
import java.math.RoundingMode

data class Operation(val operationType: OperationType, val count: BigDecimal, val currency: Currency ) {
    init {
        count.setScale(2, RoundingMode.HALF_EVEN)
    }
}
enum class Currency{
    RUB,
    DOLLAR
}

enum class OperationType{
    INCOME,
    EXPENSE
}