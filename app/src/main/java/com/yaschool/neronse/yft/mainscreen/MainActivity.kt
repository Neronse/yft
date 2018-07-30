package com.yaschool.neronse.yft.mainscreen

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.yaschool.neronse.yft.MainViewModel
import com.yaschool.neronse.yft.R
import com.yaschool.neronse.yft.SettingsActivity
import com.yaschool.neronse.yft.data.AccountTypes
import com.yaschool.neronse.yft.data.Currency
import com.yaschool.neronse.yft.mainscreen.adaptes.HistoryAdapter
import com.yaschool.neronse.yft.mainscreen.dialogs.AddOperationDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_content_view.*

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    companion object {
        const val ADD_DIALOG_TAG = "AddOperationDialog"
    }


    private val model by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDefaultDataValues() //инициализируем данные в репозитории из настроек
        setContentView(R.layout.activity_main)
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)
        fabAdd.setOnClickListener {
            AddOperationDialog().show(supportFragmentManager, ADD_DIALOG_TAG)
        }

        bottomHistory.adapter = HistoryAdapter(this, R.layout.history_list_item)
        bottomHistory.emptyView = emptyHistory
        model.history.observe(this, Observer {
            (bottomHistory.adapter as HistoryAdapter).updateHistory(it!!)
        })
        val bottomBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomBehavior.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                fabAdd.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start()
            }

        })
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            getString(R.string.pref_accountName), getString(R.string.pref_accountSurname) -> updateUserName(sharedPreferences, key)
            getString(R.string.pref_accountType) -> {
                val accountType = when (sharedPreferences?.getString(getString(R.string.pref_accountType), "CASH")) {
                    "CASH" -> AccountTypes.CASH
                    "CARD" -> AccountTypes.CARD
                    else -> throw IllegalArgumentException("settings callback accountType")
                }
                model.updateAccountType(accountType)
            }
            getString(R.string.pref_currencyType) -> {
                val currencyType = when (sharedPreferences?.getString(getString(R.string.pref_currencyType), "RUB")) {
                    "RUB" -> Currency.RUB
                    "USD" -> Currency.DOLLAR
                    else -> throw IllegalArgumentException("settings callback currencyType")
                }
                model.updateCurrencyType(currencyType)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun updateUserName(sharedPreferences: SharedPreferences?, key: String?) {
        val name: String
        val surname: String
        if (sharedPreferences != null && key != null) {
            if (key == getString(R.string.pref_accountName)) {
                name = sharedPreferences.getString(key, "")
                surname = sharedPreferences.getString(getString(R.string.pref_accountSurname), "")
            } else {
                name = sharedPreferences.getString(getString(R.string.pref_accountName), "")
                surname = sharedPreferences.getString(key, "")
            }
            model.updateUserName(name, surname)
        }
    }

    private fun setDefaultDataValues() {
        //Временный метод в рамках текущего здания
        //С появлением ДБ будет инициализироваться ДБ и вся работа будет с ней
        PreferenceManager.setDefaultValues(this, R.xml.pref_main, false)
        val sPref = PreferenceManager.getDefaultSharedPreferences(this)
        val name = sPref.getString(getString(R.string.pref_accountName), "John")
        val surname = sPref.getString(getString(R.string.pref_accountSurname), "Doe")
        val currencyType = when (sPref.getString(getString(R.string.pref_currencyType), "RUB")) {
            "RUB" -> Currency.RUB
            "USD" -> Currency.DOLLAR
            else -> throw IllegalArgumentException("setDefaultValues currencyType")
        }
        val updateTime = sPref.getString(getString(R.string.pref_UpdateTime), "10").toInt()
        val accountType = when (sPref.getString(getString(R.string.pref_accountType), "CASH")) {
            "CASH" -> AccountTypes.CASH
            "CARD" -> AccountTypes.CARD
            else ->throw IllegalArgumentException("setDefaultValues accountType")
        }

        model.initData(name, surname, currencyType, updateTime, accountType)
        model.updateUserName(name, surname)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_main_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
