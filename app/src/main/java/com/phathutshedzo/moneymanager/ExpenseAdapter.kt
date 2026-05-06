package com.phathutshedzo.moneymanager

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phathutshedzo.moneymanager.data.Expense
import java.text.SimpleDateFormat
import java.util.Locale

class ExpenseAdapter : ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder>(ExpenseDiffCallback()) {

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)   // ← Added this
        val ivPhoto: ImageView = itemView.findViewById(R.id.ivPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = getItem(position)
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        holder.tvAmount.text = "R %.2f".format(expense.amount)
        holder.tvDescription.text = expense.description.ifEmpty { "No description" }
        holder.tvDate.text = dateFormat.format(expense.date)
        holder.tvCategory.text = expense.category   // Now it works

        if (expense.photoPath != null) {
            holder.ivPhoto.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(Uri.parse(expense.photoPath))
                .centerCrop()
                .into(holder.ivPhoto)
        } else {
            holder.ivPhoto.visibility = View.GONE
        }
    }

    class ExpenseDiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean = oldItem == newItem
    }
}