package com.example.assignment1_emi
// BudgetBalanceActivity.kt

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.DecimalFormat

class BudgetBalanceActivity : Activity() {

    private lateinit var etIncome: EditText
    private lateinit var etExpenses: EditText
    private lateinit var etEmiPaid: EditText
    private lateinit var tvBudgetResult: TextView
    private lateinit var btnCalculateBudget: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_balance)

        // initialize views (EditTexts and textviews and buttons)
        etIncome = findViewById(R.id.et_income)
        etExpenses = findViewById(R.id.et_expenses)
        etEmiPaid = findViewById(R.id.et_emi_paid)
        tvBudgetResult = findViewById(R.id.tv_budget_result)
        btnCalculateBudget = findViewById(R.id.btn_calculate_budget)

        btnCalculateBudget.setOnClickListener {
            calculateBudget()
        }

        val backButton: Button = findViewById(R.id.btn_back);

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun calculateBudget() {
        // get user the input values
        val incomeStr = etIncome.text.toString()
        val expensesStr = etExpenses.text.toString()
        val emiStr = etEmiPaid.text.toString()

        //send toast
        if (incomeStr.isBlank() || expensesStr.isBlank() || emiStr.isBlank()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // parse and convert input values
        try {
            val income = incomeStr.toDouble()
            val expenses = expensesStr.toDouble()
            val emi = emiStr.toDouble()

            // calculations
            val totalExpense = expenses + emi
            val balance = income - totalExpense

            // format and display value
            val formatter = DecimalFormat("##,##0.00")
            val resultText: String

            if (balance >= 0) {
                resultText = "Monthly Savings: $${formatter.format(balance)}"
            } else {
                // balance is negative, indicating deficit
                resultText = "Monthly Deficit: $${formatter.format(Math.abs(balance))}"
            }

            tvBudgetResult.text = resultText

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid number format entered", Toast.LENGTH_SHORT).show()
        }
    }
}