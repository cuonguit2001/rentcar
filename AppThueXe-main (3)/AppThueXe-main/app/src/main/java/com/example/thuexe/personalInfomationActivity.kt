package com.example.thuexe

import Model.User
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.extensions.Extensions.toast
import com.example.myapplication.utils.FirebaseUtils
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_new_sign_up.*
import kotlinx.android.synthetic.main.personal_infomation_layout.*
import kotlinx.android.synthetic.main.personal_setting_layout.*

class personalInfomationActivity: AppCompatActivity() {
    private lateinit var clearButton: ImageButton
    private var db: DatabaseReference
    lateinit var gioitinh: String
    private lateinit var key: String

    init {
        db = FirebaseDatabase.getInstance().getReference()

    }

    private var db1: DatabaseReference

    init {
        db1 = FirebaseDatabase.getInstance().getReference()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personal_infomation_layout)
        val user = FirebaseUtils.firebaseAuth.currentUser

        if (user != null) {
            // customerId.setText(user.email)
            db.child("User").addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    // toast(snapshot.getValue(Car::class.java).toString())
                    val comment = snapshot.getValue<User>()
                    if (user.email == comment!!.Email) {
                        //     toast(snapshot.key.toString())
                        key = snapshot.key.toString()
                        nameUpdate.setText(comment!!.name)
                        nsUpdate.setText(comment!!.ngaysinh.toString())
                        emailUpdate.setText(comment!!.Email.toString())
                        phoneUpdate.setText(comment!!.sodienthoai.toString())
                        if (comment!!.gioitinh == "Nam") {
                            male_up.isChecked = true
                        }
                        if (comment!!.gioitinh == "Nu") {
                            female_up.isChecked= true
                        }
                        if (comment!!.gioitinh == "Khac") {
                            other_up.isChecked= true
                        }
                        //  toast(comment!!.name.toString())
                    }


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

        up_info_customer.setOnClickListener(View.OnClickListener {

                var user = User(
                    nameUpdate.text.toString(),
                    emailUpdate.text.toString(),
                    nsUpdate.text.toString(),
                    gioitinh,
                    phoneUpdate.text.toString().toInt(),
                    0,
                    0,
                    ""
                )
                toast("Chinh sua thanh cong")
                db1.child("User").child(key).setValue(user)

            val intent = Intent(this, personalSettingActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right)
        })
        clearButton = findViewById(R.id.clearButton_personalSetting)
        clearButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, personalSettingActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right)
        })

    }
    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.male_up ->
                    if (checked) {
                        gioitinh="Nam"
                    }
                R.id.female_up ->
                    if (checked) {
                        gioitinh="Nu"
                    }
                R.id.other_up ->
                    if (checked) {
                        gioitinh="Khac"
                    }
            }
        }
    }
}