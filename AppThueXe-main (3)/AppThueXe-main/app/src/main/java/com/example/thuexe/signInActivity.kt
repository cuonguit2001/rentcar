package com.example.thuexe

import Model.User
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.extensions.Extensions.toast
import com.example.myapplication.utils.FirebaseUtils
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.personal_infomation_layout.*
import kotlinx.android.synthetic.main.sign_in_layout.*
import android.content.Intent as Intent1

class signInActivity : AppCompatActivity() {
    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInInputsArray: Array<EditText>
    private  var db: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_layout)

        signInInputsArray = arrayOf(nameAccount, password)
        signUpText.setOnClickListener {
            startActivity(Intent1(this, new_signUp::class.java))
            finish()
        }

        signInButton.setOnClickListener {
            signInUser()
        }
    }
    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = FirebaseUtils.firebaseAuth.currentUser
        user?.let {
            startActivity(Intent1(this, MainActivity::class.java))
            toast("welcome back")
        }

    }
    private fun notEmpty(): Boolean = signInEmail.isNotEmpty() && signInPassword.isNotEmpty()

    private fun signInUser() {
        signInEmail = nameAccount.text.toString().trim()
        signInPassword = password.text.toString().trim()
        var quyen:Int=0
        if (notEmpty()) {
            FirebaseUtils.firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        val user = FirebaseUtils.firebaseAuth.currentUser
                        if (user != null) {
                            // customerId.setText(user.email)
                            val addChildEventListener =
                                db.child("User").addChildEventListener(object : ChildEventListener {
                                    override fun onChildAdded(
                                        snapshot: DataSnapshot,
                                        previousChildName: String?
                                    ) {
                                        // toast(snapshot.getValue(Car::class.java).toString())
                                        val comment = snapshot.getValue<User>()
                                        if (user.email == comment!!.Email)
                                            quyen = comment!!.quyen
                                    }

                                    override fun onChildChanged(
                                        snapshot: DataSnapshot,
                                        previousChildName: String?
                                    ) {
                                        // toast("snapshot?.getValue().toString()")
                                    }

                                    override fun onChildRemoved(snapshot: DataSnapshot) {
                                        TODO("Not yet implemented")

                                    }

                                    override fun onChildMoved(
                                        snapshot: DataSnapshot,
                                        previousChildName: String?
                                    ) {
                                        TODO("Not yet implemented")

                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")

                                    }

                                })
                            if(quyen==0){
                                startActivity(Intent1(this, MainActivity::class.java))
                                toast("signed in successfully")
                                finish()
                            } else{
                                startActivity(Intent1(this, Dashboard::class.java))
                                toast("signed in successfully")
                                finish()
                            }

                        }
                    }else {
                            toast("sign in failed")
                    }

                }
        } else {
            signInInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        }
    }
}