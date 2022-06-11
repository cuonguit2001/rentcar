package com.example.thuexe

import Model.User
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.extensions.Extensions.toast
import com.example.myapplication.utils.FirebaseUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_sign_up.*
import kotlinx.android.synthetic.main.sign_up_layout.*


class new_signUp : AppCompatActivity() {
    private lateinit var DoB: TextInputEditText
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var gioitinh: String
    lateinit var createAccountInputsArray: Array<EditText>
    private  var db: DatabaseReference
    init {
        db = FirebaseDatabase.getInstance().getReference()

    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_sign_up)
        gioitinh="Nam"
//        new_nameSignUp.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable) {
//                fullname_signUp.helperText=""
//
//
//            }
//
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//        })
        createAccountInputsArray = arrayOf(new_emailSignUp, new_passwordSignUp_1, new_passwordSignUp_2)
        new_signup.setOnClickListener {
            signIn()

        }
    }
    override fun onStart() {

        super.onStart()
        val user: FirebaseUser? = FirebaseUtils.firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, signInActivity ::class.java))
            toast("welcome back")
        }
    }
    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.male ->
                    if (checked) {
                        gioitinh="Nam"
                    }
                R.id.female ->
                    if (checked) {
                        gioitinh="Nu"
                    }
                R.id.other ->
                    if (checked) {
                        gioitinh="Khac"
                    }
            }
        }
    }
    private fun notEmpty(): Boolean = emailSignUp.text.toString().trim().isNotEmpty() &&
            new_passwordSignUp_1.text.toString().trim().isNotEmpty() &&
            new_passwordSignUp_2.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
            new_passwordSignUp_1.text.toString().trim() == new_passwordSignUp_2.text.toString().trim()
        ) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        } else {
            toast("passwords are not matching !")
        }
        return identical
    }

    private fun signIn() {
        if (identicalPassword()) {
            // identicalPassword() returns true only  when inputs are not empty and passwords are identical
            userEmail = new_emailSignUp.text.toString().trim()
            userPassword = new_passwordSignUp_1.text.toString().trim()

            /*create a user*/
            FirebaseUtils.firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var user= User(new_nameSignUp.text.toString(),new_emailSignUp.text.toString(),DoB_input.text.toString(),gioitinh,new_phoneSignUp.text.toString().toInt(),0,0,"")
                        db.push().key?.let { db.child("User").child(it).setValue(user) }
                        toast("created account successfully !")
                        sendEmailVerification()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        toast("failed to Authenticate !")
                    }
                }
        }
    }

    /* send verification email to the new user. This will only
    *  work if the firebase user is not null.
    */

    private fun sendEmailVerification() {
        FirebaseUtils.firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("email sent to $userEmail")
                }
            }
        }
    }

}