package com.yaschool.neronse.yft

import com.yaschool.neronse.yft.data.Category
import com.yaschool.neronse.yft.data.Currency
import com.yaschool.neronse.yft.data.Operation
import com.yaschool.neronse.yft.data.OperationType
import com.yaschool.neronse.yft.mathematics.calculateBalance
import com.yaschool.neronse.yft.mathematics.calculateExpense
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class FinanceUnitTest {
    private val list: List<Operation> = listOf(
            Operation(OperationType.INCOME, BigDecimal("1.51"), Currency.RUB, Category.OTHER, "comment"),
            Operation(OperationType.EXPENSE, BigDecimal("1.51"), Currency.RUB, Category.OTHER, "comment"),
            Operation(OperationType.INCOME, BigDecimal("1.51"), Currency.RUB, Category.OTHER, "comment"),
            Operation(OperationType.EXPENSE, BigDecimal("1.51"), Currency.RUB, Category.OTHER, "comment"))

    private val list2: List<Operation> = listOf(
            Operation(OperationType.EXPENSE, BigDecimal("1.51"), Currency.RUB, Category.OTHER, "comment"),
            Operation(OperationType.INCOME, BigDecimal("1.51"), Currency.DOLLAR, Category.OTHER, "comment"))

    private val list3: List<Operation> = listOf(
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

    @Test
    fun testCalculateExpense(){
        val result = calculateExpense(list.filter { it.operationType == OperationType.EXPENSE })
        assertEquals(result, 3.02f)
    }
}
