package com.example.assignment1_emi
// BudgetBalanceActivity.kt

import android.app.Activity
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

        // Initialize views
        etIncome = findViewById(R.id.et_income)
        etExpenses = findViewById(R.id.et_expenses)
        etEmiPaid = findViewById(R.id.et_emi_paid)
        tvBudgetResult = findViewById(R.id.tv_budget_result)
        btnCalculateBudget = findViewById(R.id.btn_calculate_budget)

        btnCalculateBudget.setOnClickListener {
            calculateBudget()
        }
    }

    private fun calculateBudget() {
        // 1. Validate Input (Simple check)
        val incomeStr = etIncome.text.toString()
        val expensesStr = etExpenses.text.toString()
        val emiStr = etEmiPaid.text.toString()

        if (incomeStr.isBlank() || expensesStr.isBlank() || emiStr.isBlank()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // 2. Parse Input
        try {
            val income = incomeStr.toDouble()
            val expenses = expensesStr.toDouble()
            val emi = emiStr.toDouble()

            // 3. Calculation
            val totalExpense = expenses + emi
            val balance = income - totalExpense

            // 4. Format and Display Output
            val formatter = DecimalFormat("##,##0.00")
            val resultText: String

            if (balance >= 0) {
                resultText = "Monthly Savings: $${formatter.format(balance)}"
                // Optional: Change text color to green for savings
                tvBudgetResult.setTextColor(resources.getColor(android.R.color.holo_green_dark))
            } else {
                // balance is negative, indicating deficit
                resultText = "Monthly Deficit: $${formatter.format(Math.abs(balance))}"
                // Optional: Change text color to red for deficit
                tvBudgetResult.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            }

            tvBudgetResult.text = resultText

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid number format entered", Toast.LENGTH_SHORT).show()
        }
    }
}