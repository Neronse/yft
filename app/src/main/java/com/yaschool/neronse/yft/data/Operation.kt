package com.yaschool.neronse.yft.data

import java.math.BigDecimal
import java.math.RoundingMode

data class Operation(val operationType: Boolean, val count: BigDecimal, val valute: Valute ) {
    init {
        count.setScale(2, RoundingMode.HALF_EVEN)
    }
}
enum class Valute{
    RUB,
    DOLLAR
}