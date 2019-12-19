package com.example.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.android.synthetic.main.guess_record.view.*

class RecordActivity : AppCompatActivity() {
    var guesscnt = 0
    val players = arrayOfNulls<String>(11)
    val guesscount = arrayOfNulls<String>(11)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var l_done = 'N'
        intent.getIntExtra("guesscount",guesscnt)

        players[0] = "player1"
        guesscount[0] = "1"
        players[0] = "playe2"
        guesscount[0] = "2"
//        recy.layoutManager = LinearLayoutManager(this@RecordActivity)
//        recy.setHasFixedSize(true)
//        recy.adapter = RecordAdapter()

        FirebaseDatabase.getInstance().getReference("1").child("guesscnt").setValue(guesscnt)
        //FirebaseDatabase.getInstance().getReference("name").child("1").child("Guesscount").
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var j = 1
                var k = 0
                var l_str = ""
                if (dataSnapshot.getChildrenCount().toInt() >10){ j = dataSnapshot.getChildrenCount().toInt() - 10}
                for ( i in j..dataSnapshot.getChildrenCount()) { //place
                    Log.d("onDataChange", "dataSnapshot:$dataSnapshot")
                    var l_children = dataSnapshot.child(i.toString()).child("guesscnt").value
                    Log.d("onDataChange", "dataSnapshot:$l_children")
                    players[k] = "Player"+i.toString()
                    l_str = players[k].toString()
                    Log.d("onDataChange", "players:$l_str")
                    guesscount[k] = l_children.toString()
                    l_str = guesscount[k].toString()
                    Log.d("onDataChange", "guesscount:$l_str")
                    k = k + 1
                }
                l_done = 'Y'
            }
        })
//        while (l_done.toString() !='Y'.toString()){
//            Log.d("onDataChange", "adapter")
//        }
//

    }

//    inner class RecordAdapter(): RecyclerView.Adapter<RecordHolder>() {
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.guess_record,parent,false)
//            val holder = RecordHolder(view)
//            return holder
//        }
//
//        override fun getItemCount(): Int {
//            return players.size
//        }
//
//        override fun onBindViewHolder(holder: RecordHolder, position: Int) {
//            holder.playerstext.text = players.get(position)
//            holder.guesscounttext.text = guesscount.get(position)
//        }
//
//
//    }
//    inner class RecordHolder(View:View) : RecyclerView.ViewHolder (view) {
//        var playerstext : TextView = view.players
//        var guesscounttext : TextView = View.guesscount
//    }


    inner class RecordAdapter : RecyclerView.Adapter<RecordHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.guess_record,parent,false)
            val holder = RecordHolder(view)
            return holder
        }

        override fun getItemCount(): Int {
            return players.size
        }

        override fun onBindViewHolder(holder: RecordHolder, position: Int) {
            holder.playerstext.text = players.get(position)
            holder.guesscounttext.text = guesscount.get(position)
        }

    }

    class  RecordHolder(view: View) : RecyclerView.ViewHolder(view){
        var playerstext : TextView = view.players
        var guesscounttext : TextView = view.guesscount
    }
}
