package com.example.thuexe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.extensions.Extensions.toast
import com.example.myapplication.utils.FirebaseUtils
import com.example.thuexe.utils.Car
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.personal_setting_layout.*

class personalSettingActivity : AppCompatActivity() {
    private lateinit var clearButton: ImageButton
    private lateinit var logOut: TextView
    private  var db: DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personal_setting_layout)
        val user = FirebaseUtils.firebaseAuth.currentUser
        if (user != null) {
            // customerId.setText(user.email)
            db.child("User").addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    // toast(snapshot.getValue(Car::class.java).toString())
                    val comment = snapshot.getValue<User>()
                    if(user.email==comment!!.Email)
                        customerId.text = comment!!.name

                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    // toast("snapshot?.getValue().toString()")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")

                }

            })
        }

        clearButton = findViewById(R.id.clearButton_personalSetting)
        clearButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right)
        })
        chinh_sua_ho_so.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, UpdateUser::class.java)
            startActivity(intent)
        })
        logOut = findViewById(R.id.logOut)
        logOut.setOnClickListener(View.OnClickListener {
            FirebaseUtils.firebaseAuth.signOut()
            val intent = Intent(this, signInActivity::class.java)
            startActivity(intent)

        })
    }
}