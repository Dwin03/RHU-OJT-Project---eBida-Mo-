package com.example.tsismisapp

import android.annotation.SuppressLint
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.Call
import kotlin.reflect.KMutableProperty0

class personalDetails : AppCompatActivity() {

    private lateinit var infoAddressDropDownRecyclerViewHolder: RecyclerView
    private lateinit var patientSexDropDownRecyclerViewHolder: RecyclerView
    private lateinit var patientAddressDropDownRecyclerViewHolder: RecyclerView
    private lateinit var infoAddressDropDown: ImageButton
    private lateinit var patientSexDropDown: ImageButton
    private lateinit var patientAddressDropDown: ImageButton

    private lateinit var editTextPatientSex: EditText
    private lateinit var editTextPatientAddress: EditText
    private lateinit var editTextPatientName: EditText
    private lateinit var editTextPatientAge: EditText
    private lateinit var editTextPatientNum: EditText

    private lateinit var editTextInfoName: EditText
    private lateinit var editTextInfoAddress: EditText
    private lateinit var editTextInfoNum: EditText

    private var isInfoAddressDropDownOpen = false
    private var isPatientSexDropDownOpen = false
    private var isPatientAddressDropDownOpen = false

    private val infoAd = listOf(
        "Antipolo",
        "Entablado",
        "Laguan",
        "Paule 1",
        "Pauli 2",
        "Poblacion East",
        "Poblacion West",
        "Pook",
        "Tala",
        "Talaga",
        "Tuy"
    )

    private val patSex = listOf(
        "Lalaki",
        "Babae"
    )

    private val patAd = listOf(
        "Antipolo",
        "Entablado",
        "Laguan",
        "Paule 1",
        "Pauli 2",
        "Poblacion East",
        "Poblacion West",
        "Pook",
        "Tala",
        "Talaga",
        "Tuy"
    )

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_details)

        val goBack = findViewById<ImageButton>(R.id.backBtn)
        goBack.setOnClickListener { v ->
            val nextPage = Intent(v.context, mainPage::class.java)
            startActivity(nextPage)
        }

        // Retrieve selected data
        val selectedData = intent.getStringExtra("selectedData")
        val selectedItems = selectedData?.split(", ") ?: emptyList()

        val requiredFields = listOf(
            findViewById<EditText>(R.id.informantName),
            findViewById<EditText>(R.id.informantAddress),
            findViewById<EditText>(R.id.informantNum),
            findViewById<EditText>(R.id.patientName),
            findViewById<EditText>(R.id.patientAge),
            findViewById<EditText>(R.id.patientSex),
            findViewById<EditText>(R.id.patientAddress)
        )
        markRequiredFields(*requiredFields.toTypedArray())

        // Initialize views
        infoAddressDropDownRecyclerViewHolder = findViewById(R.id.infoAddressDropDownRecyclerViewHolder)
        infoAddressDropDownRecyclerViewHolder.layoutManager = LinearLayoutManager(this)

        patientSexDropDownRecyclerViewHolder = findViewById(R.id.patientSexDropDownRecyclerViewHolder)
        patientSexDropDownRecyclerViewHolder.layoutManager = LinearLayoutManager(this)

        patientAddressDropDownRecyclerViewHolder = findViewById(R.id.patientAddressDropDownRecyclerViewHolder)
        patientAddressDropDownRecyclerViewHolder.layoutManager = LinearLayoutManager(this)

        infoAddressDropDown = findViewById(R.id.infoAddressDropDown)
        patientSexDropDown = findViewById(R.id.patientSexDropDown)
        patientAddressDropDown = findViewById(R.id.patientAddressDropDown)

        editTextPatientSex = findViewById(R.id.patientSex)
        editTextPatientAddress = findViewById(R.id.patientAddress)
        editTextPatientName = findViewById(R.id.patientName)
        editTextPatientAge = findViewById(R.id.patientAge)
        editTextPatientNum = findViewById(R.id.patientNum)

        editTextInfoName = findViewById(R.id.informantName)
        editTextInfoAddress = findViewById(R.id.informantAddress)
        editTextInfoNum = findViewById(R.id.informantNum)

        // Set up dropdowns
        infoAddressDropDown.setOnClickListener {
            toggleDropdownVisibility(infoAddressDropDownRecyclerViewHolder, infoAddressDropDown, ::isInfoAddressDropDownOpen)
            val radioButtonAdapter = RadioButtonItemAdapter(infoAd) { selectedItem ->
                editTextInfoAddress.setText(selectedItem)
                toggleDropdownVisibility(infoAddressDropDownRecyclerViewHolder, infoAddressDropDown, ::isInfoAddressDropDownOpen)
            }
            infoAddressDropDownRecyclerViewHolder.adapter = radioButtonAdapter
        }

        patientSexDropDown.setOnClickListener {
            toggleDropdownVisibility(patientSexDropDownRecyclerViewHolder, patientSexDropDown, ::isPatientSexDropDownOpen)
            val radioButtonAdapter = RadioButtonItemAdapter(patSex) { selectedItem ->
                editTextPatientSex.setText(selectedItem)
                toggleDropdownVisibility(patientSexDropDownRecyclerViewHolder, patientSexDropDown, ::isPatientSexDropDownOpen)
            }
            patientSexDropDownRecyclerViewHolder.adapter = radioButtonAdapter
        }

        patientAddressDropDown.setOnClickListener {
            toggleDropdownVisibility(patientAddressDropDownRecyclerViewHolder, patientAddressDropDown, ::isPatientAddressDropDownOpen)
            val radioButtonAdapter = RadioButtonItemAdapter(patAd) { selectedItem ->
                editTextPatientAddress.setText(selectedItem)
                toggleDropdownVisibility(patientAddressDropDownRecyclerViewHolder, patientAddressDropDown, ::isPatientAddressDropDownOpen)
            }
            patientAddressDropDownRecyclerViewHolder.adapter = radioButtonAdapter
        }

        // Submit button click listener
        val goToMainPage = findViewById<Button>(R.id.submitBtn)
        goToMainPage.setOnClickListener { v ->
            if (editTextInfoName.isEmpty() || editTextInfoAddress.isEmpty() || editTextInfoNum.isEmpty() || editTextPatientName.isEmpty() || editTextPatientAge.isEmpty() || editTextPatientSex.isEmpty() || editTextPatientAddress.isEmpty()) {
                Toast.makeText(this, "Pakiusap, kumpletuhin ang mga detalye!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (editTextInfoNum.text.toString().length != 11 || editTextPatientNum.text.toString().length != 11) {
                Toast.makeText(this, "Kulang ang numerong iniligay!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            if (editTextPatientNum.text.toString().length != 11) {
//                Toast.makeText(this, "Kulang ang numerong iniligay ng ibinibida!", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }

            val choice = AlertDialog.Builder(this, R.style.CustomAlertDialog)
                .setTitle("Kumpirmasyon")
                .setMessage("Mayroon ka pa bang gustong i-bida?")
                .setPositiveButton("Mayroon pa") { dialog, _ ->
                    val nextPage = Intent(v.context, mainPage::class.java)
                    sendDataToGoogleForm()
                    startActivity(nextPage)
                    dialog.dismiss()
//                    finish()
                }
                .setNegativeButton("Wala na") { dialog, _ ->
                    val nextPage = Intent(v.context, exitPage::class.java)
                    sendDataToGoogleForm()
                    startActivity(nextPage)
                    dialog.dismiss()
//                    finish()
                }

            val alertDialog = choice.create()

            alertDialog.setOnShowListener {
                val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)


                positiveButton.setTextColor(ContextCompat.getColor(this, R.color.darkBlue))
                negativeButton.setTextColor(ContextCompat.getColor(this, R.color.red))
//                positiveButton.setTextColor(Color.GREEN)
//                negativeButton.setTextColor(Color.RED)
            }

            alertDialog.show()
        }
    }

    private fun markRequiredFields(vararg EditText: EditText) {
        for (editText in EditText) {
            val hint = editText.hint?.toString() ?: ""
            val asterisk = " *"

            val spannableString = SpannableString("$hint$asterisk").apply {
                setSpan(ForegroundColorSpan(Color.RED), hint.length, hint.length + asterisk.length, 0)
            }
            editText.hint = spannableString
        }
    }

    private fun toggleDropdownVisibility(selectedRecyclerView: RecyclerView, dropDownButton: ImageButton, isDropdownOpen: KMutableProperty0<Boolean>) {
        val allRecyclerViews = listOf(
            infoAddressDropDownRecyclerViewHolder,
            patientSexDropDownRecyclerViewHolder,
            patientAddressDropDownRecyclerViewHolder
        )

        allRecyclerViews.forEach { recyclerView ->
            if (recyclerView == selectedRecyclerView) {
                if (isDropdownOpen.get()) {
                    recyclerView.visibility = View.GONE
                    dropDownButton.setBackgroundResource(R.drawable.down) // Set to down image
                } else {
                    recyclerView.visibility = View .VISIBLE
                    dropDownButton.setBackgroundResource(R.drawable.up) // Set to up image
                }
            } else {
                recyclerView.visibility = View.GONE
                if (recyclerView == infoAddressDropDownRecyclerViewHolder) {
                    infoAddressDropDown.setBackgroundResource(R.drawable.down)
                    isInfoAddressDropDownOpen = false
                } else if (recyclerView == patientSexDropDownRecyclerViewHolder) {
                    patientSexDropDown.setBackgroundResource(R.drawable.down)
                    isPatientSexDropDownOpen = false
                } else if (recyclerView == patientAddressDropDownRecyclerViewHolder) {
                    patientAddressDropDown.setBackgroundResource(R.drawable.down)
                    isPatientAddressDropDownOpen = false
                }
            }
        }
        isDropdownOpen.set(!isDropdownOpen.get())
    }

    private fun EditText.isEmpty(): Boolean {
        return this.text.toString().isEmpty()
    }

    private fun sendDataToGoogleForm() {
        // Google Form response submission URL
        val formUrl = "https://docs.google.com/forms/d/e/1FAIpQLSdWr7NKhwIx5G_xwCN_wvk9B8rHVOno_YmcSILyie26WnAE-Q/formResponse"

        val selectedData = intent.getStringExtra("selectedData") ?: ""

        val formData = mapOf(
            "entry.1630963027" to editTextPatientName.text.toString(),
            "entry.730703705" to editTextPatientAge.text.toString(),
            "entry.1630219846" to editTextPatientSex.text.toString(),
            "entry.1484214197" to editTextPatientAddress.text.toString(),
            "entry.740304159" to editTextPatientNum.text.toString(),
            "entry.1990074277" to selectedData,
            "entry.1148467621" to editTextInfoName.text.toString(),
            "entry.565508550" to editTextInfoAddress.text.toString(),
            "entry.1960890905" to editTextInfoNum.text.toString()
        )

        // Build the request body
        val formBody = FormBody.Builder().apply {
            formData.forEach { (key, value) -> add(key, value) }
        }.build()

        // Create OkHttpClient and Request
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(formUrl)
            .post(formBody)
            .build()

        // Execute Request Asynchronously
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    // Show network error message
                    AlertDialog.Builder(this@personalDetails)
                        .setTitle("Error")
                        .setMessage("Network error occurred. Please try again.")
                        .setPositiveButton("OK", null)
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        // Show success message
                        AlertDialog.Builder(this@personalDetails)
                            .setTitle("Success")
                            .setMessage("Form submitted successfully!")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                    else {
                        // Show error message
                        AlertDialog.Builder(this@personalDetails)
                            .setTitle("Error")
                            .setMessage("Error submitting form: ${response.code} - ${response.message}")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                }
            }
        })
    }
}