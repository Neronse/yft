package com.yaschool.neronse.yft

import com.yaschool.neronse.yft.data.Currency
import com.yaschool.neronse.yft.data.Operation
import com.yaschool.neronse.yft.data.OperationType
import com.yaschool.neronse.yft.mathematics.calculateBalance
import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal

class FinanceUnitTest {
    val list: List<Operation> = listOf(
            Operation(OperationType.INCOME, BigDecimal("1.51"), Currency.RUB),
            Operation(OperationType.EXPENSE, BigDecimal("1.51"), Currency.RUB),
            Operation(OperationType.INCOME, BigDecimal("1.51"), Currency.RUB),
            Operation(OperationType.EXPENSE, BigDecimal("1.51"), Currency.RUB))

    val list2: List<Operation> = listOf(
            Operation(OperationType.EXPENSE, BigDecimal("1.51"), Currency.RUB),
            Operation(OperationType.INCOME, BigDecimal("1.51"), Currency.DOLLAR))


    @Test
    fun testCalculateBalance() {
        val reslt = calculateBalance(list)
        assertEquals(reslt, "0.00")

        try{
            calculateBalance(list2)
        }catch (e: IllegalArgumentException ){
            assertEquals(e.message, "Only one currency in list")
        }
    }
}
