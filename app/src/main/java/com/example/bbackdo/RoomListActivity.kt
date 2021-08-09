package com.example.bbackdo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//알림, 액티비티
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
        val editSearchBar = findViewById<EditText>(R.id.editSearchBar)
        val recyclerRoom = findViewById<RecyclerView>(R.id.recyclerRoom)

        if(editSearchBar.text.isNotEmpty()) {
            recyclerRoom.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            recyclerRoom.setHasFixedSize(true)

            //키보드 내리기
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editSearchBar.windowToken, 0)
        }

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