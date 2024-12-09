package com.example.pmdm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppListAdapter(
    private val appList: List<AppModel>,
    private val onCategorize: (AppModel, String) -> Unit
) : RecyclerView.Adapter<AppListAdapter.AppViewHolder>() {

    inner class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appName: TextView = itemView.findViewById(R.id.app_name)
        val packageName: TextView = itemView.findViewById(R.id.package_name)
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

        // Set up category spinner
        holder.categorySpinner.setSelection(
            if (app.category == "Restricted") 1 else 0 // Default selection
        )
        holder.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val category = parent?.getItemAtPosition(position).toString()
                onCategorize(app, category)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun getItemCount(): Int = appList.size
}