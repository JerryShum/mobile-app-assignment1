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
        // get the user input from edittext fields
        val principalStr = etLoanAmount.text.toString()
        val rateStr = etInterestRate.text.toString()
        val tenureStr = etTenure.text.toString()

        // send toast if user presses button without filling up all fields
        if (principalStr.isBlank() || rateStr.isBlank() || tenureStr.isBlank()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // parse the inputs to turn them into the correct values/types
        try {
            // P = Principal Loan Amount
            val principalAmount = principalStr.toDouble()
            // R_annual = Annual Interest Rate
            val annualRatePercent = rateStr.toDouble()
            // Y = Tenure in Years
            val tenureYears = tenureStr.toInt()

            // convert Annual Inputs to Monthly Inputs (easier to work with)

            // monthlyrate calculation
            val monthlyInterestRate = (annualRatePercent / 100.0) / 12.0

            // N = total number of payments (# of months)
            val totalPaymentMonths = tenureYears * 12

            // calculate EMI using all variables
            if (monthlyInterestRate > 0) {
                // formula: EMI = P * [ R * (1+R)^N ] / [ (1+R)^N – 1 ]

                // (1 + R)^N is called the "power term"  / future value factor
                val futureValueFactor = (1.0 + monthlyInterestRate).pow(totalPaymentMonths.toDouble())

                // numerator portion of the EMI formula: P * R * (1+R)^N
                val numerator = principalAmount * monthlyInterestRate * futureValueFactor

                // denominator of the EMI formula: (1+R)^N - 1
                val denominator = futureValueFactor - 1.0

                // final EMI calculation
                val monthlyEmi = numerator / denominator

                // format and display output
                val formatter = DecimalFormat("##,##0.00")
                tvMonthlyEmi.text = "Monthly EMI: $${formatter.format(monthlyEmi)}"

            } else {
                // special case where the interest rate is 0
                // EMI = principal / total months
                val monthlyEmi = principalAmount / totalPaymentMonths

                // format and display for 0% rate
                val formatter = DecimalFormat("##,##0.00")
                tvMonthlyEmi.text = "Monthly EMI: $${formatter.format(monthlyEmi)}"
            }

        } catch (e: NumberFormatException) {
            // catching a numberformatexception (error occurred since user entered an incorrect format)
            Toast.makeText(this, "Invalid number format entered", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // catching all other errors
            Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}