package com.example.guess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.guess_history.view.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    val values = arrayOfNulls<String>(11)
    val results = arrayOfNulls<String>(11)
    var s_num1 = 1
    var s_num2 = 1
    var s_num3 = 1
    var s_num4 = 1
    var guesscnt = 0
    var l_i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        R.id.hello_main
        //RecyclerView
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.adapter = FunctionAdapter()
        //數字不能重複
        Log.d("send", "s_num1:$s_num1")
        s_num1 = Random.nextInt(0,9)
        while (s_num2==s_num1) {
            s_num2 = Random.nextInt(0,9)
        }
        while ((s_num3==s_num1) or (s_num3==s_num2)) {
            s_num3 = Random.nextInt(0,9)
        }
        while ((s_num4==s_num1) or (s_num4==s_num2) or (s_num4==s_num3)) {
            s_num4 = Random.nextInt(0,9)
        }
        Log.d("send", "s_num1:$s_num1")
        Log.d("send", "s_num2:$s_num2")
        Log.d("send", "s_num3:$s_num3")
        Log.d("send", "s_num4:$s_num4")
    }

    inner class FunctionAdapter() : RecyclerView.Adapter<FunctionHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.guess_history,parent,false)
            val holder = FunctionHolder(view)
            return holder
        }

        override fun getItemCount(): Int {
            return values.size
        }

        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
            holder.valuestext.text = values.get(position)
            holder.resultstext.text = results.get(position)
        }

    }

    class  FunctionHolder(view: View) : RecyclerView.ViewHolder(view){
        var valuestext : TextView = view.values
        var resultstext : TextView = view.results
    }

    fun restart(view: View){
        l_i = 9
        while (l_i>=0) {
            results[l_i] = ""
            values[l_i] = ""
            l_i = l_i - 1
        }
        guesscnt = 0
        recycler.adapter = FunctionAdapter()
    }
    fun send(view: View) {
        var l_num1 = num1.text
        var l_num2 = num2.text
        var l_num3 = num3.text
        var l_num4 = num4.text
        var l_str = ""
        var l_a = 0
        var l_b = 0
        var l_b_flag = 'N'

        if ((l_num2.toString() == l_num1.toString()) or (l_num3.toString() == l_num1.toString()) or ("$l_num1" == l_num4.toString()) or
            (l_num3.toString() == l_num2.toString()) or (l_num4.toString() == l_num2.toString()) or (l_num4.toString() == l_num3.toString()) ){
            Log.d("send", "ERROR SAME")
            AlertDialog.Builder(this).setTitle("Notice").setMessage("Can't type same number !").
                setPositiveButton("OK",null).show()
            return
        }

        Log.d("send", "l_num1:$l_num1")
        Log.d("send", "l_num2:$l_num2")
        Log.d("send", "l_num3:$l_num3")
        Log.d("send", "l_num4:$l_num4")

        if ( guesscnt>0 ){
            l_i = guesscnt
            while (l_i > 0){
                values[l_i] = values[l_i-1]
                results[l_i] = results[l_i-1]
                l_i = l_i - 1
            }
        }

        guesscnt = guesscnt +1
        //最信的統一放上面
        //values[0] = ""+l_num1+l_num2+l_num3+l_num4
        values[0] = ""+l_num1+l_num2+l_num3+l_num4
        //判斷猜的結果
        l_a = 0
        l_b = 0
        l_b_flag = 'N'
        if (l_num1.toString().toInt()==s_num1) { l_a = l_a + 1 }
        else {
            if (l_num1.toString().toInt()==s_num2) {
                l_b_flag = 'Y'
            }
            if (l_num1.toString().toInt()==s_num3) {
                l_b_flag = 'Y'
            }
            if (l_num1.toString().toInt()==s_num4) {
                l_b_flag = 'Y'
            }
        }
        if (l_b_flag == 'Y') {l_b = l_b + 1}
        l_b_flag = 'N'
        if (l_num2.toString().toInt()==s_num2) { l_a = l_a + 1 }
        else {
            if (l_num2.toString().toInt()==s_num1) {
                l_b_flag = 'Y'
            }
            if (l_num2.toString().toInt()==s_num3) {
                l_b_flag = 'Y'
            }
            if (l_num2.toString().toInt()==s_num4) {
                l_b_flag = 'Y'
            }
        }
        if (l_b_flag == 'Y') {l_b = l_b + 1}
        l_b_flag = 'N'
        if (l_num3.toString().toInt()==s_num3) { l_a = l_a + 1 }
        else {
            if (l_num3.toString().toInt()==s_num1) {
                l_b_flag = 'Y'
            }
            if (l_num3.toString().toInt()==s_num2) {
                l_b_flag = 'Y'
            }
            if (l_num3.toString().toInt()==s_num4) {
                l_b_flag = 'Y'
            }
        }
        if (l_b_flag == 'Y') {l_b = l_b + 1}
        l_b_flag = 'N'
        if (l_num4.toString().toInt()==s_num4) { l_a = l_a + 1 }
        else {
            if (l_num4.toString().toInt()==s_num1) {
                l_b_flag = 'Y'
            }
            if (l_num4.toString().toInt()==s_num2) {
                l_b_flag = 'Y'
            }
            if (l_num4.toString().toInt()==s_num3) {
                l_b_flag = 'Y'
            }
        }
        if (l_b_flag == 'Y') {l_b = l_b + 1}
        results[0] = l_a.toString()+"A"+l_b.toString()+"B"
        //清空畫面
        num1.setText(l_str)
        num2.setText(l_str)
        num3.setText(l_str)
        num4.setText(l_str)
        l_str = ""+s_num1+s_num2+s_num3+s_num4
        //寫資料庫
        FirebaseDatabase.getInstance().getReference("1").child("guesscnt").setValue(guesscnt)
        //FirebaseDatabase.getInstance().getReference("name").child("1").child("Guesscount").
//        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(object : ValueEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (i in 1..dataSnapshot.getChildrenCount()) { //place
//                    Log.d("onDataChange", "dataSnapshot:$dataSnapshot")
//                    var l_children = dataSnapshot.child(i.toString()).child("guesscnt").value
//                    Log.d("onDataChange", "dataSnapshot:$l_children")
//                }
//            }
//        })
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//
//                for (i in 1..dataSnapshot.getChildrenCount()) { //place
//                    Log.d("onDataChange", "dataSnapshot:$dataSnapshot")
//                    var l_children = dataSnapshot.child(i.toString()).child("guesscnt")
//                    Log.d("onDataChange", "dataSnapshot:$l_children")
////                    for (j in 1..dataSnapshot.child("place").getChildrenCount()) { //base
////                        for (k in 0..1) {
////                            val mean: Double =
////                                (dataSnapshot.child("place$i").child("base$j").child("mean").getValue().toString() + "").toDouble()
////                            val dev: Double =
////                                (dataSnapshot.child("place$i").child("base$j").child("dev").getValue().toString() + "").toDouble()
////                            if (k == 0) {
////                                arr.get(i - 1).get(k).get(j - 1) = mean
////                            } else {
////                                arr.get(i - 1).get(k).get(j - 1) = dev
////                            }
////                        }
////
////                    }
////                }
//                }
//            }
//        })

        //重產猜測清單
        if (l_a == 4) {

            AlertDialog.Builder(this).setTitle("Message").setMessage("You Win Number is $l_str").setPositiveButton("OK"
            ) { dialog, whitch ->
                val intent = Intent(this,RecordActivity::class.java)
                intent.putExtra("guesscount",guesscnt)
                startActivity(intent)
            }.show()
            l_i = 9
            while (l_i>=0) {
                results[l_i] = ""
                values[l_i] = ""
                l_i = l_i - 1
            }
            guesscnt = 0
        }
        if (guesscnt == 10) {
            AlertDialog.Builder(this).setTitle("Message").setMessage("You Loss Number is $l_str").setPositiveButton("OK",null).show()
            l_i = 9
            while (l_i>=0) {
                results[l_i] = ""
                values[l_i] = ""
                l_i = l_i - 1
            }
            guesscnt = 0
        }
        recycler.adapter = FunctionAdapter()
    }

}
