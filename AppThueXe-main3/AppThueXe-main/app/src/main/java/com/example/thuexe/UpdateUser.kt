package com.example.thuexe

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.extensions.Extensions.toast
import com.example.myapplication.utils.FirebaseUtils
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.personal_setting_layout.*
import kotlinx.android.synthetic.main.sign_up_layout.*
import kotlinx.android.synthetic.main.update_user.*

class UpdateUser: AppCompatActivity() {

    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>
    private  var db: DatabaseReference
    private lateinit var key : String
    init {
        db = FirebaseDatabase.getInstance().getReference()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_user)

        val user = FirebaseUtils.firebaseAuth.currentUser
        if (user != null) {
            // customerId.setText(user.email)
            db.child("User").addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    // toast(snapshot.getValue(Car::class.java).toString())
                    val comment = snapshot.getValue<User>()
                    if(user.email==comment!!.Email){
                   //     toast(snapshot.key.toString())
                        key=snapshot.key.toString()
                        nameUpdate.setText( comment!!.name)
                        emailUpdate.setText(comment!!.ngaysinh.toString())
                        phoneUpdate.setText(comment!!.sodienthoai.toString())
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
        UpdateButton.setOnClickListener {
            toast("Chỉnh sửa thành công")
            val intent = Intent(
                this, personalSettingActivity::class.java)

            startActivity(intent)
        }

    }
}