package com.phathutshedzo.moneymanager

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.phathutshedzo.moneymanager.data.AppDatabase
import com.phathutshedzo.moneymanager.data.Expense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var etAmount: EditText
    private lateinit var etDescription: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var btnAttachPhoto: Button
    private lateinit var ivExpensePhoto: ImageView
    private lateinit var btnSave: Button

    private var selectedPhotoUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedPhotoUri = it
            ivExpensePhoto.setImageURI(it)
            ivExpensePhoto.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        initViews()
        loadCategories()
        setupListeners()
    }

    private fun initViews() {
        etAmount = findViewById(R.id.etAmount)
        etDescription = findViewById(R.id.etDescription)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        btnAttachPhoto = findViewById(R.id.btnAttachPhoto)
        ivExpensePhoto = findViewById(R.id.ivExpensePhoto)
        btnSave = findViewById(R.id.btnSave)
    }

    private fun loadCategories() {
        val defaultCategories = listOf("Food", "Transport", "Groceries", "Entertainment", "Bills", "Shopping", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, defaultCategories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter
    }

    private fun setupListeners() {
        btnAttachPhoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        btnSave.setOnClickListener {
            saveExpense()
        }
    }

    private fun saveExpense() {
        val amountStr = etAmount.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val categoryName = spinnerCategory.selectedItem.toString()

        if (amountStr.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill amount and description", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val expense = Expense(
                amount = amountStr.toDouble(),
                date = Date(),
                description = description,
                category = categoryName,           // Use name instead of ID
                photoPath = selectedPhotoUri?.toString()
            )

            AppDatabase.getDatabase(this@AddExpenseActivity).expenseDao().insert(expense)

            runOnUiThread {
                Toast.makeText(this@AddExpenseActivity, "Expense Saved Successfully! ✅", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}