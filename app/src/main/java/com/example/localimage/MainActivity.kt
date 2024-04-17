package com.example.localimage

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class MainActivity : AppCompatActivity() {

    private lateinit var textInputLayoutMobile: TextInputLayout
    private lateinit var editTextMobile: TextInputEditText
    private lateinit var textInputLayoutPin: TextInputLayout
    private lateinit var editTextPin: TextInputEditText
    private lateinit var submitButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textInputLayoutMobile = findViewById(R.id.textInputLayoutMobile)
        editTextMobile = findViewById(R.id.editTextMobile)
        textInputLayoutPin = findViewById(R.id.textInputLayoutPin)
        editTextPin = findViewById(R.id.editTextPin)
        submitButton = findViewById(R.id.submitButton)

        editTextMobile.filters = arrayOf(InputFilter.LengthFilter(10))
        editTextPin.filters = arrayOf(InputFilter.LengthFilter(4))

        submitButton.setOnClickListener {
            val fieldsToValidate = listOf(
                Pair(textInputLayoutMobile, editTextMobile),
                Pair(textInputLayoutPin, editTextPin)
            )
            validateFields(fieldsToValidate)
        }
    }


    private fun validateFields(fields: List<Pair<TextInputLayout, TextInputEditText>>) {
        for ((textInputLayout, editText) in fields) {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                textInputLayout.error = "${textInputLayout.hint} cannot be empty"
            } else {
                textInputLayout.error = null
            }

            when (textInputLayout) {
                textInputLayoutMobile -> {
                    if (text.length != 10) {
                        textInputLayout.error = "${textInputLayout.hint} must be 10 digits"
                    }
                }
                textInputLayoutPin -> {
                    if (text.length != 4) {
                        textInputLayout.error = "${textInputLayout.hint} must be 4 digits"
                    }
                }
            }
        }
    }
}