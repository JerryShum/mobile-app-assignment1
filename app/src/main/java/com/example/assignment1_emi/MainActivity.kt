package com.example.assignment1_emi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)
        enableEdgeToEdge()

        val emiButton: Button = findViewById(R.id.btn_emi_calculator)
        val budgetButton: Button = findViewById(R.id.btn_budget_balance)

        emiButton.setOnClickListener {
            // Intent to start EMI Calculator Activity
            val intent = Intent(this, EmiCalculatorActivity::class.java)
            startActivity(intent)
        }

        budgetButton.setOnClickListener {
            // Intent to start Budget Balance Activity
            val intent = Intent(this, BudgetBalanceActivity::class.java)
            startActivity(intent)
        }

//        setContent {
//            Assignment1EMITheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
    }
}
