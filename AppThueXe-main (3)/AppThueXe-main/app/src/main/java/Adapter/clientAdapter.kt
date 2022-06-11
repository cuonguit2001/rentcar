package Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.thuexe.Client
import com.example.thuexe.R

class clientAdapter ( private val context: Activity,private val arrayList: ArrayList<Client>):ArrayAdapter<Client>(context,
    R.layout.client_layout,arrayList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view:View = inflater.inflate(R.layout.client_layout,null)
        val  clientName:TextView = view.findViewById(R.id.clientName)
        val clientPhone:TextView = view.findViewById(R.id.clientPhone)

        clientName.text = arrayList[position].clientName
        clientPhone.text = arrayList[position].clientPhone

        return view
    }
}