package com.example.pmdm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.graphics.Color
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppListAdapter(
    private val appList: List<AppModel>,
    private val onCategorySelected: (AppModel, String) -> Unit
) : RecyclerView.Adapter<AppListAdapter.AppViewHolder>() {

    class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appName: TextView = itemView.findViewById(R.id.app_name)
        val packageName: TextView = itemView.findViewById(R.id.package_name)
        val riskLevel: TextView = itemView.findViewById(R.id.risk_level)
        val categorySpinner: Spinner = itemView.findViewById(R.id.category_spinner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app_row, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = appList[position]
        holder.appName.text = app.appName
        holder.packageName.text = app.packageName
        holder.riskLevel.text = holder.itemView.context.getString(R.string.risk_level, app.riskLevel)


        // Update text color based on risk level
        holder.riskLevel.setTextColor(
            when (app.riskLevel) {
                "High" -> Color.RED
                "Medium" -> Color.MAGENTA
                else -> Color.GREEN
            }
        )

        // Set up spinner
        val adapter = ArrayAdapter.createFromResource(
            holder.itemView.context,
            R.array.category_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.categorySpinner.adapter = adapter

        holder.categorySpinner.setSelection(
            if (app.category == "Restricted") 1 else 0
        )

        holder.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCategory = parent.getItemAtPosition(position).toString()
                onCategorySelected(app, selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun getItemCount(): Int = appList.size
}