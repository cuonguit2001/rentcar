package com.example.thuexe

import Model.Car
import Model.User
import Model.hopdong
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.extensions.Extensions.toast
import com.example.myapplication.utils.FirebaseUtils
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_new_sign_up.*
import kotlinx.android.synthetic.main.booking_car_layout.*
import kotlinx.android.synthetic.main.car_detail_layout.*
import java.text.SimpleDateFormat
import java.util.*

class bookingCarActivity:AppCompatActivity() {
    private lateinit var confirm_button: TextView
    private lateinit var confirm_layout: RelativeLayout
    private lateinit var back_imageBtn: Button
    private lateinit var startDate2: EditText
    private lateinit var endDate2: EditText
    private lateinit var number_of_day_rental: TextView
    private  var db: DatabaseReference
    private lateinit var Bs: String
    init {
        db = FirebaseDatabase.getInstance().getReference()

    }
    var sdf= SimpleDateFormat("dd/MM/yyyy");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_car_layout)

        startDate2 = findViewById(R.id.StartDate2)
        endDate2 = findViewById(R.id.EndDate2)

        val intent = getIntent()
        val startDate = intent.getStringExtra("startDate")
        val endDate = intent.getStringExtra("endDate")

        startDate2.setText(startDate)
        endDate2.setText(endDate)
       // toast(startDate.toString())
      //  toast(endDate.toString())
        number_of_day_rental = findViewById(R.id.number_of_day_rental)
        var date11 = sdf.parse(startDate)
        var date22 = sdf.parse(endDate)

        val getDiff: Long = date22.getTime() - date11.getTime()

        val getDaysDiff = getDiff / (24 * 60 * 60 * 1000)
  //  toast(getDaysDiff.toString())
        number_of_day_rental.setText(getDaysDiff.toString() +" ngày")

        var total: TextView = findViewById(R.id.total)
        var totalPay: TextView = findViewById(R.id.totalPay)
        totalPay.setText(total.text)

        var bs=intent.getStringExtra("bs")
         name_booking.setText(intent.getStringExtra("name"))
        var gia=intent.getStringExtra("gia").toString()
        gia=gia.toString().replace("00000000","00.000.000")
        gia=gia.toString().replace("0000000","0.000.000")
        gia=gia.toString().replace("000000",".000.000")
        gia=gia.toString().replace("00000","00.000")
        gia=gia.toString().replace("0000","0.000")
        gia=gia.toString().replace("000",".000")
        gia=gia.toString().replace("..",".")
        don_gia.setText(gia+"VND")
        var tong1=getDaysDiff* gia.replace(".","").toInt()+100000
     //   toast(tong1.toString())
        var tong=tong1.toString()
        tong=tong.toString().replace("00000000","00.000.000")
        tong=tong.toString().replace("0000000","0.000.000")
        tong=tong.toString().replace("000000",".000.000")
        tong=tong.toString().replace("00000","00.000")
        tong=tong.toString().replace("0000","0.000")
        tong=tong.toString().replace("100",".100")
        tong=tong.toString().replace("200",".200")
        tong=tong.toString().replace("300",".300")
        tong=tong.toString().replace("600",".600")
        tong=tong.toString().replace("700",".700")
        tong=tong.toString().replace("800",".800")
        tong=tong.toString().replace("900",".900")
        tong=tong.toString().replace("000",".000")
        tong=tong.toString().replace("..",".")
       total.setText(tong.toString()+"VND")
        totalPay.setText(tong.toString()+"VND")
        //   toast(tong.toString())


        back_imageBtn = findViewById(R.id.back_Button)
        back_imageBtn.setOnClickListener {
            val intent = Intent(this, carDetailActivity::class.java)
            startActivity(intent)
        }

        confirm_button = findViewById(R.id.confirm_text_button)
        confirm_layout = findViewById(R.id.confirm_layout)
        confirm_button.setOnClickListener {
            val user = FirebaseUtils.firebaseAuth.currentUser
            toast(bs.toString())
            if (user != null) {
                toast(user.email.toString())
                var user= hopdong(user.email.toString(),tong1.toInt(),bs.toString(),startDate.toString(),endDate.toString(),"cho xac nhan")
                db.push().key?.let { db.child("hopdong").child(it).setValue(user) }
                Toast.makeText(confirm_layout.context,"Thành công!", Toast.LENGTH_SHORT).show()
            }

        }


    }

}