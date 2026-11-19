package com.example.expensenote_25

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.expensenote_25.databinding.ItemExpenseBinding
import java.text.NumberFormat
import java.util.Locale

class ExpenseAdapter(
    private var items: List<ExpenseItem>,
    private val onDeleteClick: (ExpenseItem) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(val binding: ItemExpenseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemExpenseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context

        holder.binding.tvTitle.text = item.title
        holder.binding.tvSubtitle.text = "${item.category} • ${item.date}"

        val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
        val formattedAmount = formatter.format(item.amount)

        if (item.type == ExpenseDatabaseHelper.TYPE_INCOME) {
            holder.binding.tvAmount.text = "+ ฿$formattedAmount"
            holder.binding.tvAmount.setTextColor(
                ContextCompat.getColor(context, R.color.income_green)
            )
            holder.binding.viewTypeIndicator.setBackgroundColor(
                ContextCompat.getColor(context, R.color.income_green)
            )
        } else {
            holder.binding.tvAmount.text = "- ฿$formattedAmount"
            holder.binding.tvAmount.setTextColor(
                ContextCompat.getColor(context, R.color.expense_red)
            )
            holder.binding.viewTypeIndicator.setBackgroundColor(
                ContextCompat.getColor(context, R.color.expense_red)
            )
        }

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<ExpenseItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
