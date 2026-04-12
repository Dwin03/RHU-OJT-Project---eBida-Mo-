package com.example.tsismisapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KMutableProperty0

class mainPage : AppCompatActivity() {

    private lateinit var item1RecyclerViewHolder: RecyclerView
    private lateinit var item2RecyclerViewHolder: RecyclerView

    private lateinit var item1DropDownRecyclerView: RadioButton
    private lateinit var item2DropDownRecyclerView: RadioButton
    private lateinit var item4: RadioButton
    private lateinit var item5: RadioButton
    private lateinit var item6: RadioButton
    private lateinit var item7: RadioButton
    private lateinit var item8: RadioButton

    private lateinit var item1DropDown: ImageButton
    private lateinit var item2DropDown: ImageButton
    private lateinit var optionsAdapter1: checkBoxItemAdapter
    private lateinit var optionsAdapter2: RadioButtonItemAdapter

    private var selectedRadioButtonId: Int? = null
    private lateinit var clearSelectionTextView: TextView

    // Toggle state for dropdown
    private var isItem1DropdownOpen = false
    private var isItem2DropdownOpen = false

    private val q1Items = listOf(
        Item("Hinahapo o hirap sa paghinga"),
        Item("Kombulsyon"),
        Item("Lagnat"),
        Item("Masakit ang lalamunan"),
        Item("Masakit ang ulo"),
        Item("Nagsusuka"),
        Item("Nagtatae"),
        Item("Pananakit ng katawan"),
        Item("Panghihina ng katawan"),
        Item("Paninigas ng katawan"),
        Item("Rashes"),
        Item("Sipon"),
        Item("Ubo"),
        Item("Ubo na mahigit dalawang linggo")
    )

    private val q2Items = listOf(
        "Teenager (19 years old pababa)",
        "Adult (20 years old pataas)",
        "Bagong Panganak"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        // Initialize UI components
        item1DropDown = findViewById(R.id.item1DropDown)
        item2DropDown = findViewById(R.id.item2DropDown)
        item1RecyclerViewHolder = findViewById(R.id.item1RecyclerViewHolder)
        item2RecyclerViewHolder = findViewById(R.id.item2RecyclerViewHolder)
        item1DropDownRecyclerView = findViewById(R.id.item1DropDownRecyclerView)
        item2DropDownRecyclerView = findViewById(R.id.item2DropDownRecyclerView)
        item4 = findViewById(R.id.item4)
        item5 = findViewById(R.id.item5)
        item6 = findViewById(R.id.item6)
        item7 = findViewById(R.id.item7)
        item8 = findViewById(R.id.item8)
        clearSelectionTextView = findViewById(R.id.clearSelecton)

        // Set up RecyclerViews
        item1RecyclerViewHolder.layoutManager = LinearLayoutManager(this)
        item2RecyclerViewHolder.layoutManager = LinearLayoutManager(this)

        // Initialize the adapters
        optionsAdapter1 = checkBoxItemAdapter(q1Items)
        item1RecyclerViewHolder.adapter = optionsAdapter1

        optionsAdapter2 = RadioButtonItemAdapter(q2Items) { selectedItem ->
            // Handle the selected item if needed
        }
        item2RecyclerViewHolder.adapter = optionsAdapter2

        // Set click listeners for dropdown buttons
        item1DropDown.setOnClickListener {
            toggleDropdownVisibility(item1RecyclerViewHolder, item1DropDown, ::isItem1DropdownOpen)
        }

        item2DropDown.setOnClickListener {
            toggleDropdownVisibility(item2RecyclerViewHolder, item2DropDown, ::isItem2DropdownOpen)
        }

        // Button to go to personal details page
        val goToPersonalInfo = findViewById<Button>(R.id.nextPageBtn)
        goToPersonalInfo.setOnClickListener { v ->
            val selectedData = mainPageDataHandler()
            val nextPage = Intent(this, personalDetails::class.java)
            nextPage.putExtra("selectedData", selectedData)
            startActivity(nextPage)
        }

        clearSelectionTextView.setOnClickListener {
            clearSelectedRadioButton()
        }

        // Set click listeners for radio buttons
        item1DropDownRecyclerView.setOnClickListener { onRadioButtonClicked(item1DropDownRecyclerView.id) }
        item2DropDownRecyclerView.setOnClickListener { onRadioButtonClicked(item2DropDownRecyclerView.id) }
        item4.setOnClickListener { onRadioButtonClicked(item4.id) }
        item5.setOnClickListener { onRadioButtonClicked(item5.id) }
        item6.setOnClickListener { onRadioButtonClicked(item6.id) }
        item7.setOnClickListener { onRadioButtonClicked(item7.id) }
        item8.setOnClickListener { onRadioButtonClicked(item8.id) }
    }

    private fun toggleDropdownVisibility(selectedRecyclerView: RecyclerView, dropDownButton: ImageButton, isDropdownOpen: KMutableProperty0<Boolean>) {
        // List of all RecyclerViews
        val allRecyclerViews = listOf(item1RecyclerViewHolder, item2RecyclerViewHolder)

        // Toggle visibility for the selected RecyclerView
        allRecyclerViews.forEach { recyclerView ->
            if (recyclerView == selectedRecyclerView) {
                if (isDropdownOpen.get()) {
                    recyclerView.visibility = View.GONE
                    dropDownButton.setBackgroundResource(R.drawable.down) // Set to down image
                } else {
                    recyclerView.visibility = View.VISIBLE
                    dropDownButton.setBackgroundResource(R.drawable.up) // Set to up image
                }
            } else {
                recyclerView.visibility = View.GONE // Hide other RecyclerViews
                // Ensure the other dropdown button is set to down image
                if (recyclerView == item1RecyclerViewHolder) {
                    item1DropDown.setBackgroundResource(R.drawable.down)
                    isItem1DropdownOpen = false
                } else if (recyclerView == item2RecyclerViewHolder) {
                    item2DropDown.setBackgroundResource(R.drawable.down)
                    isItem2DropdownOpen = false
                }
            }
        }
        isDropdownOpen.set(!isDropdownOpen.get()) // Toggle the dropdown state
    }

    private fun clearSelectedRadioButton() {
        selectedRadioButtonId?.let { id ->
            val radioButton = findViewById<RadioButton>(id)
            radioButton.isChecked = false // Uncheck the RadioButton
            selectedRadioButtonId = null // Reset the selected ID
        }

        optionsAdapter1.clearSelections()
        optionsAdapter2.clearSelection()
    }

    private fun onRadioButtonClicked(selectedId: Int) {
        selectedRadioButtonId?.let { id ->
            val previousRadioButton = findViewById<RadioButton>(id)
            previousRadioButton.isChecked = false
        }

        val selectedRadioButton = findViewById<RadioButton>(selectedId)
        selectedRadioButton.isChecked = true
        selectedRadioButtonId = selectedId
    }

    private fun mainPageDataHandler(): String {
        val selectedCheckboxItems = optionsAdapter1.getSelectedItems()
        val selectedRadioButtonItem = optionsAdapter2.getSelectedItem()

        val combinedData = StringBuilder()

        if (selectedCheckboxItems.isNotEmpty()) {
            combinedData.append(selectedCheckboxItems.joinToString(", \n") { it.name })
        }

        if (selectedRadioButtonItem != null) {
            if (combinedData.isNotEmpty()) {
                combinedData.append(", \n")
            }
            combinedData.append("Bagong buntis / Buntis na walang check-up / Bagong Panganak: $selectedRadioButtonItem")
        }

        val additionalRadioButtons = listOf(item4, item5, item6, item7, item8)
        additionalRadioButtons.forEach { radioButton ->
            if (radioButton.isChecked) {
                if (combinedData.isNotEmpty()) {
                    combinedData.append(", \n")
                }
                combinedData.append(radioButton.text)
            }
        }
        return combinedData.toString()
    }
}