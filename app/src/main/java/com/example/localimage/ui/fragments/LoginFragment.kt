package com.example.localimage.ui.fragments

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.localimage.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {
    private lateinit var textInputLayoutMobile: TextInputLayout
    private lateinit var editTextMobile: TextInputEditText
    private lateinit var textInputLayoutPin: TextInputLayout
    private lateinit var editTextPin: TextInputEditText
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var editTextName: TextInputEditText
    private lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textInputLayoutMobile = view.findViewById(R.id.textInputLayoutMobile)
        editTextMobile = view.findViewById(R.id.editTextMobile)
        textInputLayoutPin = view.findViewById(R.id.textInputLayoutPin)
        editTextPin = view.findViewById(R.id.editTextPin)
        textInputLayoutName = view.findViewById(R.id.textInputLayoutName)
        editTextName = view.findViewById(R.id.editTextName)
        submitButton = view.findViewById(R.id.submitButton)

        editTextMobile.filters = arrayOf(InputFilter.LengthFilter(10))
        editTextPin.filters = arrayOf(InputFilter.LengthFilter(4))

        submitButton.setOnClickListener {
            val fieldsToValidate = listOf(
                Pair(textInputLayoutMobile, editTextMobile),
                Pair(textInputLayoutPin, editTextPin),
                Pair(textInputLayoutName, editTextName)
            )
            val result = validateFields(fieldsToValidate)
            if (result) openGalleryFragment()
        }
    }

    private fun openGalleryFragment() {
        val bundle = Bundle()
        bundle.putString("key", editTextName.text.toString())

        val fragment = ImageGalleryFragment()
        fragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun validateFields(fields: List<Pair<TextInputLayout, TextInputEditText>>): Boolean {
        for ((textInputLayout, editText) in fields) {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                textInputLayout.error = "${textInputLayout.hint} cannot be empty"
                return false
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
        return true
    }
}