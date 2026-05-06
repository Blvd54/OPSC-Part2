package com.phathutshedzo.moneymanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phathutshedzo.moneymanager.data.AppDatabase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExpenseListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)

        recyclerView = findViewById(R.id.recyclerViewExpenses)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ExpenseAdapter()
        recyclerView.adapter = adapter

        loadExpenses()
    }

    private fun loadExpenses() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@ExpenseListActivity)
            db.expenseDao().getAllExpenses().collectLatest { expenses ->
                adapter.submitList(expenses)
            }
        }
    }
}