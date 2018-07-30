package com.yaschool.neronse.yft.data

import java.math.BigDecimal
import java.math.RoundingMode

data class Person(val account: List<Account>, var name: String, var surname: String, var updateTime: Int)

data class Account(val accountType: AccountTypes, val currencyType: Currency, val listOperations: MutableList<Operation>)

data class Operation(
        val operationType: OperationType,
        var count: BigDecimal,
        val currency: Currency,
        val category: Category,
        val comment: String) {
    init {
       count = count.setScale(2, RoundingMode.HALF_EVEN)
    }
}

enum class Currency {
    RUB,
    DOLLAR
}

enum class OperationType {
    INCOME,
    EXPENSE
}

enum class AccountTypes {
    CASH,
    CARD
}
enum class Category{
    PRODUCTS,
    CLOTHES,
    AUTO,
    OTHER
}