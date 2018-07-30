package com.yaschool.neronse.yft.data

import android.arch.lifecycle.MutableLiveData
import android.graphics.Color
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.yaschool.neronse.yft.mathematics.calculateBalance
import com.yaschool.neronse.yft.mathematics.calculateExpense
import com.yaschool.neronse.yft.network.CBRDaily
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Arrays.asList


object Repository : Callback<CBRDaily> {

    lateinit var person: Person
    var initialize = true
    val exchangeRate: MutableLiveData<String> = MutableLiveData()

    fun initPerson(name: String, surname: String, updateTime: Int, currencyType: Currency) {
        if (initialize) {
            val account = listOf(
                    Account(AccountTypes.CASH, currencyType, mutableListOf()),
                    Account((AccountTypes.CARD), currencyType, mutableListOf()))
            person = Person(account, name, surname, updateTime)
            initialize = false
        }
    }

    fun getOperationList(accountTypes: AccountTypes): MutableList<Operation> {
        return when (accountTypes) {
            AccountTypes.CASH -> person.account[0].listOperations
            AccountTypes.CARD -> person.account[1].listOperations
        }
    }

    fun addOperation(accountType: AccountTypes, currencyType: Currency, operationType: OperationType, count: String, category: Category, comment: String) {
        val operation = Operation(operationType, BigDecimal(count), currencyType, category, comment)
        when (accountType) {
            AccountTypes.CASH -> person.account[0].listOperations.add(operation)
            AccountTypes.CARD -> person.account[1].listOperations.add(operation)
        }
    }

    fun getPieDataSet(accountType: AccountTypes, currencyType: Currency): PieDataSet {
       val entries = mutableListOf<PieEntry>()
        val filterList = getOperationList(accountType).filter { it.currency == currencyType && it.operationType == OperationType.EXPENSE}
        filterList.groupBy { it.category }.forEach{entry ->
            val count = calculateExpense(entry.value)
            if (!count.equals("0.00")) {
                entries.add(PieEntry(count, entry.value[0].category.toString()))
            }
        }
         return PieDataSet(entries, "").apply {
            valueTextSize = 14f
            colors =  ColorTemplate.MATERIAL_COLORS.asList()
            sliceSpace = 3f
        }
    }


    override fun onFailure(call: Call<CBRDaily>?, t: Throwable?) {

    }

    override fun onResponse(call: Call<CBRDaily>?, response: Response<CBRDaily>?) {
        if (response != null) {
            val cbrDaily = response.body()
            val rate = BigDecimal(cbrDaily?.valute?.usd?.value.toString()).setScale(2, RoundingMode.HALF_EVEN)
            exchangeRate.postValue(rate.toPlainString())
        }
    }
}