package com.yaschool.neronse.yft.mainscreen.dialogs

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import com.yaschool.neronse.yft.MainViewModel
import com.yaschool.neronse.yft.R
import com.yaschool.neronse.yft.data.Category
import com.yaschool.neronse.yft.data.OperationType
import kotlinx.android.synthetic.main.dialog_add_operation.view.*

class AddOperationDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val model = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        val adb = AlertDialog.Builder(activity!!)
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_add_operation, null)
        adb.setTitle(getString(R.string.add_operation))
                .setView(view)
                .setPositiveButton("Ok") { dialog, which ->
                    val count = view.editCount.text.toString()
                    val category = when(view.spinner.selectedItem.toString()){
                        getString(R.string.category_products) -> Category.PRODUCTS
                        getString(R.string.category_auto) -> Category.AUTO
                        getString(R.string.category_clothes) -> Category.CLOTHES
                        getString(R.string.category_other) -> Category.OTHER
                        else -> throw IllegalArgumentException("dialog fragment category")
                    }
                    var comment = view.editComment.text.toString()
                    val operation = when (view.operationSpinner.selectedItem.toString()) {
                        getString(R.string.income) -> OperationType.INCOME
                        getString(R.string.expense) -> OperationType.EXPENSE
                        else -> OperationType.INCOME
                    }
                    if (count.isNotBlank()) {
                        if (comment.isBlank()) {
                            when (operation) {
                                OperationType.INCOME -> comment = getString(R.string.incomeMoney)
                                OperationType.EXPENSE -> comment = getString(R.string.expenseMoney)
                            }
                        }
                        model.addOperation(count, operation, category, comment)
                    }
                    dismiss()
                }
                .setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

        return adb.create()
    }
}