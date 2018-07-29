package com.yaschool.neronse.yft

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieDataSet
import com.yaschool.neronse.yft.data.*
import com.yaschool.neronse.yft.mathematics.calculateBalance
import com.yaschool.neronse.yft.network.CBRDailyService

class MainViewModel : ViewModel() {

    val shortHist: MutableLiveData<List<Operation>> = MutableLiveData()
    val userName: MutableLiveData<String> = MutableLiveData()
    val currentAccountType: MutableLiveData<AccountTypes> = MutableLiveData()
    val currentCurrencyType: MutableLiveData<Currency> = MutableLiveData()
    val currentBalance: MutableLiveData<String> = MutableLiveData()
    val history: MutableLiveData<List<Operation>> = MutableLiveData()
    val pieChart: MutableLiveData<PieDataSet> = MutableLiveData()
    val exchangeRate: MutableLiveData<String> = MutableLiveData()

    fun initData(name: String, surname: String, currencyType: Currency, updateTime: Int, accountTypes: AccountTypes) {
        Repository.initPerson(name, surname, updateTime, currencyType)
        currentCurrencyType.value = currencyType
        currentAccountType.value = accountTypes
        updateBalance()
        updatePieChart(Repository.getPieDataSet(accountTypes, currencyType))
    }

    fun updateShortHistory(accountType: AccountTypes) {
        shortHist.postValue(Repository.getOperationList(accountType)
                .filter { it.currency == currentCurrencyType.value }
                .reversed()
                .take(5))
    }

    fun updateHistory(accountType: AccountTypes) {
        history.postValue(Repository.getOperationList(accountType)
                .filter { it.currency == currentCurrencyType.value }
                .reversed())
    }

    fun updateUserName(name: String, surname: String) {
        Repository.person.name = name
        Repository.person.surname = surname
        userName.postValue("${Repository.person.name} ${Repository.person.surname}")
    }

    fun updateAccountType(accountType: AccountTypes) {
        currentAccountType.value = accountType
        updateShortHistory(accountType)
        updateHistory(accountType)
        val currencyType = currentCurrencyType.value
        if(currencyType != null)
        updatePieChart(Repository.getPieDataSet(accountType, currencyType))
    }

    fun updateCurrencyType(currencyType: Currency) {
        currentCurrencyType.value = currencyType
        val accountType = currentAccountType.value
        if (accountType != null) {
            updateShortHistory(accountType)
            updateHistory(accountType)
            updatePieChart(Repository.getPieDataSet(accountType, currencyType))
        }
    }

    fun updateBalance() {
        val accountType = currentAccountType.value
        val currencuType = currentCurrencyType.value
        var balance = "0.00"
        if (accountType != null && currencuType != null) {
            balance = calculateBalance(Repository.getOperationList(accountType).filter { it.currency == currencuType })
        }
        currentBalance.postValue(balance)
    }

    fun updatePieChart(pieDataSet: PieDataSet) {
        pieChart.postValue(pieDataSet)
    }

    fun addOperation(count: String, operationType: OperationType, category: Category, comment: String) {
        val accountType = currentAccountType.value
        val currencyType = currentCurrencyType.value
        if (accountType != null && currencyType != null) {
            Repository.addOperation(accountType, currencyType, operationType, count, category, comment)
            updateBalance()
            updateShortHistory(accountType)
            updateHistory(accountType)
            updatePieChart(Repository.getPieDataSet(accountType, currencyType))
        }
    }

    fun getRate() {
        Repository.exchangeRate.observeForever { exchangeRate.postValue(it) }
        CBRDailyService.create().request().enqueue(Repository)
    }


}