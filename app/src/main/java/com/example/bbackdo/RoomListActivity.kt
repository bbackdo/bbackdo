package com.example.bbackdo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//알림, 액티비티
//동그라미를 누르면... 할 수 있게
//업을 떄/잡을 떄/졌을 때/자리

class RoomListActivity : AppCompatActivity() {

    //var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_list)

        //파이어스토어 인스턴스 초기화
        //firestore = FirebaseFirestore.getInstance()

        //recyclerview.adpater = RecyclerViewAdapter()
        //recyclerview.layoutManager = LinearLayoutManager(this)

        val buttonMake = findViewById<Button>(R.id.buttonMake)

        buttonMake.setOnClickListener {

        }
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var roomList: ArrayList<Room> = arrayListOf()

        init {

        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_room, parent, false)

            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            val textTitle = findViewById<TextView>(R.id.textTitle)
            val buttonEnter = findViewById<Button>(R.id.buttonEnter)

            init {

            }

            fun setTitle(title: String) {

            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView

            //viewHolder.title.text
        }

        override fun getItemCount(): Int {
            return roomList.size
        }
    }
}