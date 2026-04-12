package com.example.tsismisapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class checkBoxItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<checkBoxItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxCatcher)
        val checkBoxLabel: TextView = itemView.findViewById(R.id.checkBoxLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_single_check_box, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.checkBoxLabel.text = item.name
        holder.checkBox.isChecked = item.isChecked

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // Method to get selected items
    fun getSelectedItems(): List<Item> {
        return items.filter { it.isChecked }
    }

    // Method to clear selections
    fun clearSelections() {
        items.forEach { it.isChecked = false } // Uncheck all items
        notifyDataSetChanged() // Notify the adapter to refresh the view
    }
}