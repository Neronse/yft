package com.yaschool.neronse.yft.mainscreen

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.yaschool.neronse.yft.MainViewModel
import com.yaschool.neronse.yft.R
import com.yaschool.neronse.yft.mainscreen.adaptes.ShortHistoryAdapter
import kotlinx.android.synthetic.main.main_screen_layout.view.*

class MainScreenFragment : Fragment() {

    private lateinit var model: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        model = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        val view = inflater.inflate(R.layout.main_screen_layout, container, false)

        initUI(view)

        return view
    }


    private fun initUI(view: View) {

        model.userName.observe(this, Observer { view.currentUser.text = it })
        model.currentCurrencyType.observe(this, Observer { view.currencyType.text = it.toString() })
        model.currentAccountType.observe(this, Observer { view.accountType.text = it.toString() })
        model.currentBalance.observe(this, Observer { view.valueRub.text = it })
        model.exchangeRate.observe(this, Observer { view.buyRate.text = it })
        model.getRate()
        view.valueUsd.text = "0.00" //конвертация будет добавленна позже
        view.refreshDate.text = "Сегодня" //как и обновление даты

        view.shortHistory.adapter = ShortHistoryAdapter(context!!, R.layout.small_list_item)
        view.shortHistory.emptyView = view.emptyShortList

        model.shortHist.observe(this,
                Observer { list ->
                    (view.shortHistory.adapter as ShortHistoryAdapter).updateHistory(list!!)
                })

        view.pieChart.apply {
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.textSize = 15f
            legend.formSize = 15f
            holeRadius = 15f
            transparentCircleRadius = 20f
            description.isEnabled = false
            dragDecelerationFrictionCoef = 0.95f
            isDrawHoleEnabled = false
            animateX(1400)
        }

        model.pieChart.observe(this, Observer {
            view.pieChart.data = PieData(it)
            view.pieChart.invalidate()
        })
    }
}