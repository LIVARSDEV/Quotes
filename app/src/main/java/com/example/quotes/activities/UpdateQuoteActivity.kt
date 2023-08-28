package com.example.quotes.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import com.example.quotes.SqliteDatabase
import com.example.quotes.databinding.ActivityUpdateQuoteBinding

class UpdateQuoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateQuoteBinding
    private var sqliteDatabase = SqliteDatabase(this)
    private var sentQuote: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUpdateQuoteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            toUpdateQuoteET.requestFocus()

            sentQuote = intent.getStringExtra("quoteSent").toString()
            toUpdateQuoteET.text = Editable.Factory.getInstance().newEditable(sentQuote)

            val newString = toUpdateQuoteET.text
            updateQuoteFAB.setOnClickListener {
                if (newString.isNotEmpty()) {
                    sqliteDatabase.updateQuote(sentQuote, newString.toString())
                    Intent(this@UpdateQuoteActivity, MainActivity::class.java).also {
                        startActivity(it)
                    }
                } else {
                    Toast.makeText(this@UpdateQuoteActivity, "Empty field", Toast.LENGTH_SHORT)
                        .show()

                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val a = binding.toUpdateQuoteET.text.toString()
        if (a != sentQuote) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Quit without saving?")
                .setPositiveButton(HtmlCompat.fromHtml("<font color='#4285F4'>CANCEL</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)
                ) { dialog, _ ->
                    dialog.dismiss()
                }
                .setNegativeButton(HtmlCompat.fromHtml("<font color='#4285F4'>QUIT</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)
                ) { _, _ ->
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()

        } else {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }

        }
    }
}
