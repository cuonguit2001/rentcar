package com.example.thuexe

import Adapter.userCommentAdapter
import Model.Car
import Model.hopdong
import Model.userCommentModel
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.extensions.Extensions.toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.car_detail_layout.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class carDetailActivity : AppCompatActivity() {
    private lateinit var imageSwitcher: ImageSwitcher
    private lateinit var imageBefore: RelativeLayout
    private lateinit var imageNext: RelativeLayout
    private lateinit var recyclerView_userCmt: RecyclerView
    private lateinit var booking_textBtn: TextView
    private lateinit var back_Btn: Button
    private lateinit var checkDate: TextView
    private lateinit var startDate: EditText
    private lateinit var endDate: EditText
    private lateinit var startDate2: ArrayList<String>
    private lateinit var endDate2: ArrayList<String>
    val ls : ArrayList<hopdong> = ArrayList()
    private lateinit var checkDate_warning: TextView
    private lateinit var car_detail_layout: RelativeLayout
    private lateinit var Bs: String
    private lateinit var name: TextView
    private lateinit var gia: TextView
    var sdf= SimpleDateFormat("dd/MM/yyyy");
    private  var db: DatabaseReference
    init {
        db = FirebaseDatabase.getInstance().getReference()

    }
    private  var db1: DatabaseReference
    init {
        db1 = FirebaseDatabase.getInstance().getReference()

    }
//    private var imageSwitcher: ImageSwitcher = findViewById(R.id.imageSwitcher)
//    private var imageBefore: RelativeLayout = findViewById(R.id.imageBefore)
//    private var imageNext: RelativeLayout = findViewById(R.id.imageNext)
//    private var recyclerView_userCmt: RecyclerView = findViewById(R.id.recyclerView_userCmt)
//    private var booking_textBtn: TextView = findViewById(R.id.booking_textButton)
//    private var back_Btn: Button = findViewById(R.id.back_Button)
//    private var checkDate: TextView = findViewById(R.id.checkDate1)
//    private var startDate: EditText = findViewById(R.id.StartDate1)
//    private var endDate: EditText = findViewById(R.id.EndDate1)
//    private var checkDate_warning: TextView = findViewById(R.id.checkDate_warning1)
//    private var car_detail_layout: RelativeLayout = findViewById(R.id.car_detail_layout)

    private var recycleLayoutManager: RecyclerView.LayoutManager? = null
    private lateinit var list_userComment: ArrayList<userCommentModel>

    val images = arrayOf(R.drawable.bmw_m5_img, R.drawable.mercedes, R.drawable.ford_mustang)
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.car_detail_layout)

        imageSwitcher = findViewById(R.id.imageSwitcher)
        imageBefore = findViewById(R.id.imageBefore)
        imageNext = findViewById(R.id.imageNext)
        recyclerView_userCmt= findViewById(R.id.recyclerView_userCmt)
        booking_textBtn = findViewById(R.id.booking_textButton)
        back_Btn= findViewById(R.id.back_Button)
        checkDate = findViewById(R.id.checkDate1)
        startDate = findViewById(R.id.StartDate1)
        endDate = findViewById(R.id.EndDate1)
        checkDate_warning = findViewById(R.id.checkDate_warning1)
        car_detail_layout = findViewById(R.id.car_detail_layout)

        back_Btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        setForSwitching()

        imageNext.setOnClickListener {
            counter++
            if(counter == images.size){
                counter = 0
            }
            val imageForChange = images[counter]
            imageSwitcher.setImageResource(imageForChange)
        }

        imageBefore.setOnClickListener {
            counter--
            if(counter < 0){
                counter = images.size-1
            }
            val imageForChange = images[counter]
            imageSwitcher.setImageResource(imageForChange)
        }

        recycleLayoutManager = LinearLayoutManager(this)

        recyclerView_userCmt.layoutManager = recycleLayoutManager

        list_userComment = arrayListOf()
        getData()

        checkDate.setOnClickListener {
            if(isNullDate()){
                checkDate_warning.setText("Vui lòng điền ngày thuê xe.")
                checkDate_warning.setTextColor(Color.RED)
            } else{
                if(!isinValidDate()){
                    checkDate_warning.setText("Nhập ngày thuê sai! Vui lòng nhập lại.")
                    checkDate_warning.setTextColor(Color.RED)
                } else{
                    if(!isValidDate()){
                        checkDate_warning.setText("Xe đang được thuê vào ngày này.")
                        checkDate_warning.setTextColor(Color.RED)
                    }
                    else{
                        checkDate_warning.setText("Xe có sẵn.")
                        checkDate_warning.setTextColor(Color.GREEN)
                    }
                }

            }
        }

        booking_textBtn.setOnClickListener {
            if(isValidDate()){
                val intent = Intent(this, bookingCarActivity::class.java)
                intent.putExtra("startDate", startDate.text.toString())
                intent.putExtra("endDate", endDate.text.toString())
                intent.putExtra("name", name_detail.text.toString())
                intent.putExtra("gia", gia_detail.text.toString().replace(".","").replace("VND/Ngay",""))
                intent.putExtra("bs", Bs)
                toast(gia_detail.text.toString().replace("VND/Ngay",""))
                startActivity(intent)
            }
            else {

                Toast.makeText(car_detail_layout.context,"Ngày không hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        startDate.setOnClickListener {
            val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                    _, mYear, mMonth, mDay -> startDate.setText("$mDay/$mMonth/$mYear")
            }, year, month, day)
            dpd.show()
        }
        endDate.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                    _, mYear, mMonth, mDay -> endDate.setText("$mDay/$mMonth/$mYear")
            }, year, month, day)
            dpd.show()
        }

        db.child("Timxe").get().addOnSuccessListener {
            Bs=it.value.toString()
        }.addOnFailureListener{
        }
        name = findViewById(R.id.name_detail)
        db.child("Car").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                // toast(snapshot.getValue(Car::class.java).toString())
                val comment = snapshot.getValue<Car>()
                if(comment!!.Bienso==Bs){
                    //    toast("123214")

                    name_detail.setText(comment!!.name.toString())
                    var gia=comment!!.gia.toString()
                    gia=gia.toString().replace("00000000","00.000.000")
                    gia=gia.toString().replace("0000000","0.000.000")
                    gia=gia.toString().replace("000000",".000.000")
                    gia=gia.toString().replace("00000","00.000")
                    gia=gia.toString().replace("0000","0.000")
                    gia_detail.setText(gia+"VND/Ngay")
                }



            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

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
        db1.child("hopdong").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                // toast(snapshot.getValue(Car::class.java).toString())
                val comment = snapshot.getValue<hopdong>()
                if(comment!!.Bienso==Bs){
                  //  toast(comment!!.bd)
                   ls.add(comment!!)
                }



            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

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

    fun setForSwitching(){
        setFactory()
        setAnimations()
    }

    fun setFactory(){
        imageSwitcher.setFactory{
            getImageView()
        }
    }

    fun getImageView(): ImageView{
        val imageView = ImageView(this)
        imageView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT)
        imageView.scaleType=ImageView.ScaleType.FIT_XY
        imageView.setImageResource(images[0])
        return imageView
    }

    fun setAnimations(){
        val animOut = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)
        val animIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)

        val animOut1 = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        val animIn1 = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)

        imageSwitcher.outAnimation = animOut1
        imageSwitcher.inAnimation = animIn1
    }

    fun getData() {
        list_userComment.add(userCommentModel("JohnWih", R.drawable.user_img,"Đánh giá chiếc xe này rất ngon"))
        list_userComment.add(userCommentModel("Nothing", R.drawable.user_img,"Đánh giá chiếc xe này rất ngon"))
        list_userComment.add(userCommentModel("NoHject", R.drawable.user_img,"Đánh giá chiếc xe này rất ngon"))
        list_userComment.add(userCommentModel("JemMye", R.drawable.user_img,"Đánh giá chiếc xe này rất ngon"))
        list_userComment.add(userCommentModel("Nguyễn Thành An", R.drawable.user_img,"Đánh giá chiếc xe này rất ngon"))

        recyclerView_userCmt.adapter = userCommentAdapter(list_userComment)
    }

    fun isinValidDate(): Boolean{
        if(isNullDate()){
            return false
        }
        if(startDate.text.toString().compareTo(endDate.text.toString()) >0 ) {
            toast("7658")
            return false

        }
//        var sdf=SimpleDateFormat("dd/mm/yyyy");
//        toast(sdf.format(startDate.text.toString()))
        return true
    }
    fun isValidDate(): Boolean{
        if(isNullDate()){
            return false
        }
        var startDate1= startDate.text.toString()
        var  endDate1= endDate.text.toString()

        for(i in ls){
          //  toast(startDate1)
           // toast(i.bd.compareTo(startDate1, ignoreCase = true).toString())
            if(i.bd.compareTo(startDate1, ignoreCase = true)<0 &&i.kt.compareTo(startDate1, ignoreCase = true)>0){
                return false
            }
            if(i.bd.compareTo(endDate1, ignoreCase = true)<0 &&i.kt.compareTo(endDate1, ignoreCase = true)>0){
                return false
            }
        }

        return true
    }
    fun isNullDate(): Boolean{
        if(startDate.text.toString() == "" || endDate.text.toString() == "")
            return true
        return false
    }
}


