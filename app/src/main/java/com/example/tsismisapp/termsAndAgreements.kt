package com.example.tsismisapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class termsAndAgreements : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_agreements)

        val tab = "\t"

        val firstMessage = findViewById<TextView>(R.id.firstMessage)
//        val secondMessage = findViewById<TextView>(R.id.secondMessage)
//        val finalMessafe = findViewById<TextView>(R.id.finalMessage)

        val firstIndent = getString(R.string.firstParagraph, tab)
//        val secondIndent = getString(R.string.secondParagraph, tab)
//        val finalIndent = getString(R.string.finalParagraph, tab)

        firstMessage.text = firstIndent
//        secondMessage.text = secondIndent
//        finalMessafe.text = finalIndent

        val agree = findViewById<Button>(R.id.agreeButton)
        agree.setOnClickListener { v ->
            val nextPage = Intent(v.context, mainPage::class.java)
            startActivity(nextPage)
        }

//        val disagree = findViewById<Button>(R.id.disagreeButton)
//        disagree.setOnClickListener { v ->
//            finish()
//            finishAffinity()
//            System.exit(0)
//        }
    }
}