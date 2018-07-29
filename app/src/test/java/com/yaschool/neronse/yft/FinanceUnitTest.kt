package com.yaschool.neronse.yft

import com.yaschool.neronse.yft.data.Category
import com.yaschool.neronse.yft.data.Currency
import com.yaschool.neronse.yft.data.Operation
import com.yaschool.neronse.yft.data.OperationType
import com.yaschool.neronse.yft.mathematics.calculateBalance
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class FinanceUnitTest {
    val list: List<Operation> = listOf(
            Operation(OperationType.INCOME, BigDecimal("1.51"), Currency.RUB, Category.OTHER, "comment"),
            Operation(OperationType.EXPENSE, BigDecimal("1.51"), Currency.RUB, Category.OTHER, "comment"),
            Operation(OperationType.INCOME, BigDecimal("1.51"), Currency.RUB, Category.OTHER, "comment"),
            Operation(OperationType.EXPENSE, BigDecimal("1.51"), Currency.RUB, Category.OTHER, "comment"))

    val list2: List<Operation> = listOf(
            Operation(OperationType.EXPENSE, BigDecimal("1.51"), Currency.RUB, Category.OTHER, "comment"),
            Operation(OperationType.INCOME, BigDecimal("1.51"), Currency.DOLLAR, Category.OTHER, "comment"))

    val list3: List<Operation> = listOf(
            Operation(OperationType.INCOME, BigDecimal("1.512681"), Currency.RUB, Category.OTHER, "comment"),
            Operation(OperationType.INCOME, BigDecimal("1.512681"), Currency.RUB, Category.OTHER, "comment"))


    @Test
    fun testCalculateBalance1() {
        val reslt = calculateBalance(list)
        assertEquals(reslt, "0.00")
    }

    @Test
    fun testCalculateBalance2() {
        try {
            calculateBalance(list2)
        } catch (e: IllegalArgumentException) {
            assertEquals(e.message, "Only one currency in list")
        }
    }
    @Test
    fun testCalculateBalance3() {
        val result = calculateBalance(list3)
        assertEquals(result,"3.02")
    }
}
