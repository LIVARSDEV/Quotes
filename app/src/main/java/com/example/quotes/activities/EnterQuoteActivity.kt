package com.example.quotes.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import com.example.quotes.SqliteDatabase
import com.example.quotes.databinding.ActivityEnterQuoteBinding
import com.google.android.material.snackbar.Snackbar

class EnterQuoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEnterQuoteBinding
    private lateinit var sqliteDatabase: SqliteDatabase
    private lateinit var editTextInput: Editable

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEnterQuoteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            quoteET.requestFocus()
            editTextInput = quoteET.text


            sqliteDatabase = SqliteDatabase(this@EnterQuoteActivity)
            insertQuoteFAB.setOnClickListener {
                if (editTextInput.isEmpty()) {
                    Snackbar.make(enterQuoteContainer, "Empty filed", Snackbar.LENGTH_SHORT).also {
                        it.setBackgroundTint(Color.parseColor("#808080"))
                    }
                        .show()

                }
                if (editTextInput.startsWith("\n") || editTextInput.endsWith("\n")) {
                    val pureText = editTextInput.toString().replace("\n", "")
                    sqliteDatabase.addQuoteToDb(pureText)
                    Intent(this@EnterQuoteActivity, MainActivity::class.java).also {
                        startActivity(it)

                    }
                } else {
                    sqliteDatabase.addQuoteToDb(editTextInput.toString())
                    Intent(this@EnterQuoteActivity, MainActivity::class.java).also {
                        startActivity(it)

                    }
                }
            }
        }
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onBackPressed() {
        if (editTextInput.isNotEmpty()) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Quit without saving?")
                .setPositiveButton(
                    HtmlCompat.fromHtml(
                        "<font color='#4285F4'>CANCEL</font>",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                ) { dialog, _ ->
                    dialog.dismiss()
                }
                .setNegativeButton(
                    HtmlCompat.fromHtml(
                        "<font color='#4285F4'>QUIT</font>",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
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
