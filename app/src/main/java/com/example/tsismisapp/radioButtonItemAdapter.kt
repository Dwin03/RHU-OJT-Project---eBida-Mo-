package com.example.tsismisapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView

class RadioButtonItemAdapter(
    private val items: List<String>, // List of items to display as radio buttons
    private val onItemSelected: (String) -> Unit // Callback for item selection
) : RecyclerView.Adapter<RadioButtonItemAdapter.ViewHolder>() {

    private var selectedItem: String? = null // Variable to store the selected item

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val radioGroup: RadioGroup = itemView.findViewById(R.id.radioGroup) // Reference to the RadioGroup
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_information_radio_button, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.radioGroup.removeAllViews() // Clear previous RadioButtons

        // Create RadioButtons for each item
        items.forEach { item ->
            // Inflate the custom RadioButton layout
            val radioButton = LayoutInflater.from(holder.itemView.context).inflate(R.layout.custom_radio_button, holder.radioGroup, false) as RadioButton
            radioButton.text = item // Set the text of the RadioButton to the item
            radioButton.isChecked = (item == selectedItem)
            radioButton.setOnClickListener {
                selectedItem = item
                onItemSelected(item)
            }
            holder.radioGroup.addView(radioButton)
        }
    }

    override fun getItemCount(): Int = 1 // Only one item for the group

    // Method to get the selected item
    fun getSelectedItem(): String? {
        return selectedItem
    }

    // Method to clear selection
    fun clearSelection() {
        selectedItem = null // Reset the selected item
        notifyDataSetChanged() // Notify the adapter to refresh the view
    }
}