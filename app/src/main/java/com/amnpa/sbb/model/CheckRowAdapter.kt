package com.amnpa.sbb.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.amnpa.sbb.R

class CheckRowAdapter(mContext: Context, private val dataSet: List<Competition>) :
    ArrayAdapter<Any?>(mContext, R.layout.row_checkbox, dataSet) {
    private val items = HashMap<Int, ViewHolder>()

    private class ViewHolder {
        lateinit var txtName: TextView
        lateinit var checkBox: CheckBox
    }

    override fun getCount(): Int {
        return dataSet.size
    }

    override fun getItem(position: Int): CheckRow {
        return CheckRow(dataSet[position], if (items[position] != null) items[position]!!.checkBox.isChecked else false)
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var _convertView = convertView
        val viewHolder: ViewHolder
        val result: View
        if (_convertView == null) {
            viewHolder = ViewHolder()
            _convertView =
                LayoutInflater.from(parent.context).inflate(R.layout.row_checkbox, parent, false)
            viewHolder.txtName =
                _convertView.findViewById(R.id.txtName)
            viewHolder.checkBox =
                _convertView.findViewById(R.id.checkBox)
            result = _convertView
            _convertView.tag = viewHolder
        } else {
            viewHolder = _convertView.tag as ViewHolder
            result = _convertView
        }
        val item: CheckRow = getItem(position)
        viewHolder.txtName.text = item.toString()
        viewHolder.checkBox.isChecked = item.checked
        items[position] = viewHolder
        return result
    }
}