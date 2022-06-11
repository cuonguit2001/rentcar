package com.example.thuexe

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class signUpActivity : AppCompatActivity() {
    private lateinit var nameSignUp: EditText
    private lateinit var emailSignUp: EditText
    private lateinit var phoneSignUp: EditText
    private lateinit var passwordSignUp_1: EditText
    private lateinit var passwordSignUp_2: EditText
    private lateinit var signUpButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_layout)

        nameSignUp = findViewById(R.id.nameSignUp)
        emailSignUp = findViewById(R.id.emailSignUp)
        phoneSignUp = findViewById(R.id.phoneSignUp)
        passwordSignUp_1 = findViewById(R.id.passwordSignUp_1)
        passwordSignUp_2 = findViewById(R.id.passwordSignUp_2)
        signUpButton = findViewById(R.id.signUpButton)

        var nameValid: Boolean = false
        var emailValid: Boolean = false
        var phoneValid: Boolean = false
        var passwordValid: Boolean = false
        var signUpResult: Boolean = true

        nameSignUp.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        emailSignUp.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                emailValid = true
            }

        })
        phoneSignUp.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                passwordValid = true
            }

        })
        passwordSignUp_1.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                passwordValid = true
            }

        })
        passwordSignUp_2.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                passwordValid = passwordSignUp_2.text.toString().equals(passwordSignUp_1.text.toString())
            }

        })

        signUpButton.setOnClickListener(View.OnClickListener {
            if (nameValid && phoneValid && emailValid && passwordValid){
                val intent = Intent(this, signInActivity::class.java)
                startActivity(intent)
            }
        })
    }
}