package com.example.simple_list

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val myEditText = findViewById<EditText>(R.id.Number)
        val myButton = findViewById<Button>(R.id.OK)
        val evenButton = findViewById<RadioButton>(R.id.even)
        val oddButton = findViewById<RadioButton>(R.id.odd)
        val squareButton = findViewById<RadioButton>(R.id.square)
        val listView = findViewById<ListView>(R.id.listView)

        myButton.setOnClickListener {
            val inputText = myEditText.text.toString().toIntOrNull()

            if (inputText == null || inputText < 0) {
                Toast.makeText(this, "Vui lòng nhập số nguyên dương", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Kiểm tra nếu không có RadioButton nào được chọn
            if (!evenButton.isChecked && !oddButton.isChecked && !squareButton.isChecked) {
                Toast.makeText(this, "Vui lòng chọn một tùy chọn", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultList = when {
                evenButton.isChecked -> getEvenNumbers(inputText)
                oddButton.isChecked -> getOddNumbers(inputText)
                squareButton.isChecked -> getSquareNumbers(inputText)
                else -> emptyList()
            }

            // Cập nhật ListView
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resultList)
            listView.adapter = adapter

            evenButton.isChecked = false
            oddButton.isChecked = false
            squareButton.isChecked = false
        }
    }

    private fun getEvenNumbers(n: Int): List<String> {
        return (0..n).filter { it % 2 == 0 }.map { it.toString() }
    }

    private fun getOddNumbers(n: Int): List<String> {
        return (0..n).filter { it % 2 != 0 }.map { it.toString() }
    }

    private fun getSquareNumbers(n: Int): List<String> {
        return (0..n).filter { Math.sqrt(it.toDouble()).toInt().toDouble() == Math.sqrt(it.toDouble()) }
            .map { it.toString() }
    }
}
