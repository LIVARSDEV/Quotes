package com.example.quotes.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotes.SqliteDatabase
import com.example.quotes.databinding.ActivityMainBinding
import com.example.quotes.recyclerView.QuoteModelClass
import com.example.quotes.recyclerView.RecyclerViewAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sqliteDatabase: SqliteDatabase
    private lateinit var quoteFromDb: MutableList<QuoteModelClass>


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {

            addQuoteFAB.setOnClickListener {
                Intent(this@MainActivity, EnterQuoteActivity::class.java).also {
                    startActivity(it)
                }
            }

            sqliteDatabase = SqliteDatabase(this@MainActivity)
            quoteFromDb = sqliteDatabase.retrieveAllQuotes()
            val adapter = RecyclerViewAdapter(quoteFromDb)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)


            if (quoteFromDb.isEmpty()){
                noItemsLL.visibility = View.VISIBLE
            }else {
                noItemsLL.visibility = View.GONE

            }
        }
    }
}
