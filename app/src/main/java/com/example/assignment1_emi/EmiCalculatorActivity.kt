package com.example.assignment1_emi

// EmiCalculatorActivity.kt

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.math.pow
import java.text.DecimalFormat

class EmiCalculatorActivity : Activity() {

    // Define views globally or inside a binding object (more advanced)
    private lateinit var etLoanAmount: EditText
    private lateinit var etInterestRate: EditText
    private lateinit var etTenure: EditText
    private lateinit var tvMonthlyEmi: TextView
    private lateinit var btnCalculateEmi: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emi_calculator)

        val backButton: Button = findViewById(R.id.btn_back);

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        // Initialize views
        etLoanAmount = findViewById(R.id.et_loan_amount)
        etInterestRate = findViewById(R.id.et_interest_rate)
        etTenure = findViewById(R.id.et_tenure)
        tvMonthlyEmi = findViewById(R.id.tv_monthly_emi)
        btnCalculateEmi = findViewById(R.id.btn_calculate_emi)

        btnCalculateEmi.setOnClickListener {
            calculateEmi()
        }
    }

    private fun calculateEmi() {
        // 1. Validate Input
        val principalStr = etLoanAmount.text.toString()
        val rateStr = etInterestRate.text.toString()
        val tenureStr = etTenure.text.toString()

        if (principalStr.isBlank() || rateStr.isBlank() || tenureStr.isBlank()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // 2. Parse Input (Handle potential number format exceptions)
        try {
            val principal = principalStr.toDouble()
            val annualRatePercent = rateStr.toDouble()
            val years = tenureStr.toInt()

            // 3. Convert to Monthly Rate and Total Months
            // R = Monthly Interest Rate = (Annual Rate / 12 / 100)
            val monthlyRate = (annualRatePercent / 12) / 100.0
            // N = Total Number of Payments = Years * 12
            val months = years * 12

            // 4. EMI Formula Calculation
            if (monthlyRate > 0) {
                // Formula: P * [ R * (1+R)^N ] / [ (1+R)^N – 1 ]
                val powerTerm = (1.0 + monthlyRate).pow(months.toDouble())
                val emi = principal * monthlyRate * powerTerm / (powerTerm - 1.0)

                // 5. Format and Display Output
                val formatter = DecimalFormat("##,##0.00")
                tvMonthlyEmi.text = "Monthly EMI: $${formatter.format(emi)}"
            } else {
                // Simple principal / months calculation if rate is 0
                val emi = principal / months
                val formatter = DecimalFormat("##,##0.00")
                tvMonthlyEmi.text = "Monthly EMI: $${formatter.format(emi)}"
            }

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid number format entered", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}