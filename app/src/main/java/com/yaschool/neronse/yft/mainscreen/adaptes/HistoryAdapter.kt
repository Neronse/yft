package com.yaschool.neronse.yft.mainscreen.adaptes

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.yaschool.neronse.yft.R
import com.yaschool.neronse.yft.data.Operation
import com.yaschool.neronse.yft.data.OperationType
import kotlinx.android.synthetic.main.history_list_item.view.*

class HistoryAdapter(context: Context, @LayoutRes resource: Int) : ArrayAdapter<Any>(context, resource) {

    private var history: List<Operation> = ArrayList()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    fun updateHistory(history: List<Operation>){
        this.history = history
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val operation = history[position]
        val holder: ViewHolder
        val retView: View
        if (convertView == null) {
            retView = inflater.inflate(R.layout.history_list_item, parent, false)
            holder = ViewHolder()
            holder.opCount = retView.textCount
            holder.comment = retView.textOperationComment
            holder.opCategory = retView.textCategoryOperation
            retView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            retView = convertView
        }
        holder.opCategory?.text = operation.category.toString()
        holder.comment?.text = operation.comment
        when (operation.operationType) {
            OperationType.INCOME -> {
                val opText = "+${operation.count.toPlainString()}"
                holder.opCount?.text = opText
                holder.opCount?.setTextColor(context.resources.getColor(R.color.textIncome))
                holder.comment?.setTextColor(context.resources.getColor(R.color.textIncome))
                holder.opCategory?.setTextColor(context.resources.getColor(R.color.textIncome))
            }
            OperationType.EXPENSE -> {
                val opText = "-${operation.count.toPlainString()}"
                holder.opCount?.text = opText
                holder.opCount?.setTextColor(context.resources.getColor(R.color.textExpense))
                holder.comment?.setTextColor(context.resources.getColor(R.color.textExpense))
                holder.opCategory?.setTextColor(context.resources.getColor(R.color.textExpense))
            }
        }
        return retView
    }

    override fun getItem(position: Int): Any {
        return history[position]
    }

    override fun getCount(): Int {
        return history.size
    }
}

class ViewHolder {
    var comment: TextView? = null
    var opCategory: TextView? = null
    var opCount: TextView? = null

}