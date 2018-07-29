package com.yaschool.neronse.yft.mainscreen.adaptes

import android.content.Context
import android.graphics.Color
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.yaschool.neronse.yft.R
import com.yaschool.neronse.yft.data.Operation
import com.yaschool.neronse.yft.data.OperationType
import kotlinx.android.synthetic.main.small_list_item.view.*

import java.util.ArrayList


class ShortHistoryAdapter(context: Context, @LayoutRes resource: Int) : ArrayAdapter<Any>(context, resource) {

    private var operations: List<Operation> = ArrayList()
    private val inflater: LayoutInflater = LayoutInflater.from(context)


    fun updateHistory(operations: List<Operation>) {
        this.operations = operations
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any? {
        return operations[position]
    }

    override fun getCount(): Int {
        return operations.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val operation = operations[position]
        val holder: ShortOperationHolder
        val retView: View
        if(convertView == null){
           retView = inflater.inflate(R.layout.small_list_item, parent, false)
            holder = ShortOperationHolder()
            holder.operationValue = retView.opearValue
            holder.comment = retView.listItemText
            retView.tag = holder
        }
        else{
            holder = convertView.tag as ShortOperationHolder
            retView = convertView
        }
        holder.comment?.text = operation.comment
        when (operation.operationType){
            OperationType.INCOME -> {
                val opText = "+${operation.count.toPlainString()}"
                holder.operationValue?.text = opText
                holder.operationValue?.setTextColor(context.resources.getColor(R.color.textIncome))
                holder.comment?.setTextColor(context.resources.getColor(R.color.textIncome))
            }
            OperationType.EXPENSE ->{
                val opText = "-${operation.count.toPlainString()}"
                holder.operationValue?.text = opText
                holder.operationValue?.setTextColor(context.resources.getColor(R.color.textExpense))
                holder.comment?.setTextColor(context.resources.getColor(R.color.textExpense))
            }
        }
        return retView

    }
}

class ShortOperationHolder{
    var operationValue: TextView? = null
    var comment: TextView? = null
}


