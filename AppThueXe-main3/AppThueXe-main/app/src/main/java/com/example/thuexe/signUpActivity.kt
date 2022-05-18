package com.example.thuexe

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity



import com.example.myapplication.extensions.Extensions.toast
import com.example.myapplication.utils.FirebaseUtils.firebaseAuth
import com.example.myapplication.utils.FirebaseUtils.firebaseUser
import com.example.thuexe.utils.Car
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.sign_up_layout.*

class signUpActivity : AppCompatActivity() {
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>
    private  var db: DatabaseReference
    init {
        db = FirebaseDatabase.getInstance().getReference()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_layout)
        createAccountInputsArray = arrayOf(emailSignUp, passwordSignUp_1, passwordSignUp_2)
        signUpButton.setOnClickListener {
            signIn()
        }


    }

    /* check if there's a signed-in user*/

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, signInActivity ::class.java))
            toast("welcome back")
        }
    }

    private fun notEmpty(): Boolean = emailSignUp.text.toString().trim().isNotEmpty() &&
            passwordSignUp_1.text.toString().trim().isNotEmpty() &&
            passwordSignUp_2.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
            passwordSignUp_1.text.toString().trim() == passwordSignUp_2.text.toString().trim()
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
            userEmail = emailSignUp.text.toString().trim()
            userPassword = passwordSignUp_1.text.toString().trim()

            /*create a user*/
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var user=User(nameSignUp.text.toString(),emailSignUp.text.toString(),"","nam",phoneSignUp.text.toString().toInt(),0,0,"")
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
        firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("email sent to $userEmail")
                }
            }
        }
    }
}
