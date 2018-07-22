package com.yaschool.neronse.yft

import com.yaschool.neronse.yft.data.Operation
import com.yaschool.neronse.yft.data.Valute
import com.yaschool.neronse.yft.mathematics.calculateBalance
import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal

class FinanceUnitTest {
    val list: List<Operation> = listOf(
            Operation(false, BigDecimal("1.51"), Valute.RUB),
            Operation(true, BigDecimal("1.51"), Valute.RUB),
            Operation(false, BigDecimal("1.51"), Valute.RUB),
            Operation(true, BigDecimal("1.51"), Valute.RUB))

    val list2: List<Operation> = listOf(
            Operation(false, BigDecimal("1.51"), Valute.RUB),
            Operation(true, BigDecimal("1.51"), Valute.DOLLAR))


    @Test
    fun testCalculateBalance() {
        val reslt = calculateBalance(list)
        assertEquals(reslt, "0.00")

        try{
            calculateBalance(list2)
        }catch (e: IllegalArgumentException ){
            assertEquals(e.message, "Only one valute in list")
        }
    }
}
