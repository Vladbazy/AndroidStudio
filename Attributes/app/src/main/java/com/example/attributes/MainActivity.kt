package com.example.attributes

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        val editText: EditText = findViewById(R.id.edit_text)

        findViewById<Button>(R.id.button_black_text).setOnClickListener {
            editText.setTextColor(Color.BLACK)
        }
        findViewById<Button>(R.id.button_red_text).setOnClickListener {
            editText.setTextColor(Color.RED)
        }

        findViewById<Button>(R.id.button_size_8).setOnClickListener {
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8f)
        }
        findViewById<Button>(R.id.button_size_24).setOnClickListener {
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
        }

        findViewById<Button>(R.id.button_white_bg).setOnClickListener {
            editText.setBackgroundColor(Color.WHITE)
        }
        findViewById<Button>(R.id.button_yellow_bg).setOnClickListener {
            editText.setBackgroundColor(Color.YELLOW)
        }
    }
}