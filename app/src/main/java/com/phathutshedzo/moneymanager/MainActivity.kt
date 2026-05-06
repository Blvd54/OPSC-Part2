package com.phathutshedzo.moneymanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.phathutshedzo.moneymanager.utils.AuthManager

class MainActivity : AppCompatActivity() {

    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authManager = AuthManager(this)

        if (!authManager.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val tvMonthlyGoal = findViewById<TextView>(R.id.tvMonthlyGoal)
        val tvTotalSpent = findViewById<TextView>(R.id.tvTotalSpent)
        val etMonthlyGoal = findViewById<EditText>(R.id.etMonthlyGoal)
        val btnSetGoal = findViewById<Button>(R.id.btnSetGoal)
        val btnAddExpense = findViewById<Button>(R.id.btnAddExpense)
        val btnViewExpenses = findViewById<Button>(R.id.btnViewExpenses)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        tvWelcome.text = "Welcome, ${authManager.getUsername()}!"

        // Load saved monthly goal
        val savedGoal = getSharedPreferences("budget_prefs", MODE_PRIVATE)
            .getFloat("monthly_goal", 0f)
        tvMonthlyGoal.text = "R %.2f".format(savedGoal)

        btnSetGoal.setOnClickListener {
            val goalStr = etMonthlyGoal.text.toString().trim()
            if (goalStr.isNotEmpty()) {
                val goal = goalStr.toDoubleOrNull() ?: 0.0
                getSharedPreferences("budget_prefs", MODE_PRIVATE).edit()
                    .putFloat("monthly_goal", goal.toFloat())
                    .apply()
                tvMonthlyGoal.text = "R %.2f".format(goal)
                Toast.makeText(this, "Monthly Goal Updated!", Toast.LENGTH_SHORT).show()
            }
        }

        btnAddExpense.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        btnViewExpenses.setOnClickListener {
            startActivity(Intent(this, ExpenseListActivity::class.java))
        }

        btnLogout.setOnClickListener {
            authManager.logout()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}