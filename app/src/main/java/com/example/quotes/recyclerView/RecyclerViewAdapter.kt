package com.example.quotes.recyclerView


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.R
import com.example.quotes.SqliteDatabase
import com.example.quotes.activities.MainActivity
import com.example.quotes.activities.UpdateQuoteActivity

class RecyclerViewAdapter(private val quotesList: MutableList<QuoteModelClass>) :
    RecyclerView.Adapter
    <RecyclerViewAdapter.QuotesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_row_design, parent,
            false
        )
        return QuotesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quotesList.size
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        val quote = quotesList[position]
        holder.quoteTv?.text = quote.quote
        holder.bind(quote.quote)

        holder.itemView.setOnLongClickListener {
            quotesList.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)

            val context = holder.itemView.context
            val sqliteDatabase = SqliteDatabase(context)
            sqliteDatabase.deleteQuote(quote.quote)

            if (quotesList.size == 0){
                Intent(context, MainActivity::class.java).also {
                    context.startActivity(it)
                }
            }
            true
        }
    }

    class QuotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quoteTv: TextView? = itemView.findViewById(R.id.quoteTv)
        private val context = itemView.context
        fun bind(quoteBind: String) {
            itemView.setOnClickListener {
                Intent(context, UpdateQuoteActivity::class.java).also {
                    it.putExtra("quoteSent", quoteBind)
                    context.startActivity(it)
                }
            }
        }
    }
}
