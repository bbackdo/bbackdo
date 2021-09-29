package com.example.bbackdo

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbackdo.databinding.ActivityRoomListBinding
import com.example.bbackdo.databinding.DialogMakeRoomBinding
import com.example.bbackdo.databinding.ItemRecyclerRoomBinding
import com.google.android.gms.tasks.OnSuccessListener

//알림, 액티비티
//업을 떄/잡을 떄/졌을 때/자리

class RoomListActivity : AppCompatActivity() {

    //var firestore : FirebaseFirestore? = null
    private val room by lazy {
        ActivityRoomListBinding.inflate(layoutInflater)
    }

    private val dataList = arrayListOf<Room>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(room) {
            setContentView(root)
            Toast.makeText(this@RoomListActivity, "방만들기 액티비티", Toast.LENGTH_SHORT).show()
            // 방만들기 클릭 했을 때
            buttonMake.setOnClickListener {
                val inflater =
                    this@RoomListActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val dialogView = inflater.inflate(R.layout.dialog_make_room, null)
                val alertDialog = AlertDialog.Builder(this@RoomListActivity)


                with(alertDialog) {
                    setView(dialogView)
                    setTitle("방 만들기")
                    setPositiveButton("확인") { _: DialogInterface, _: Int ->
                        Toast.makeText(this@RoomListActivity, "방만들기 나왔나", Toast.LENGTH_SHORT).show()
                    }
                    setNeutralButton("취소", null)
                    show()

                    var listener = DialogInterface.OnClickListener{p0, _->
                        var dialog = p0 as AlertDialog
                        var makeRoom = dialog.findViewById<Button>(R.id.buttonMakeMulti)
                        var cancel = dialog.findViewById<Button>(R.id.buttonCancelMulti)
                    }



                    fun test(){

                    }
                }

            }
            //파이어스토어 인스턴스 초기화
            //firestore = FirebaseFirestore.getInstance()
        }
    }

        inner class RecyclerViewAdapter(
            private val context: Context,
            private var listData: ArrayList<Room>
        ) :
            RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
            init {

            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view =
                    ItemRecyclerRoomBinding.inflate(LayoutInflater.from(context), parent, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val room: Room = listData[position]
                holder.binding(room)
            }

            override fun getItemCount(): Int {
                return listData.size
            }


            inner class ViewHolder(private val bind: ItemRecyclerRoomBinding) :
                RecyclerView.ViewHolder(bind.root) {
                fun binding(room: Room) {
                    with(bind) {
                        textTitle.text = room.title.toString()

                        val pos = adapterPosition
                        if (pos != RecyclerView.NO_POSITION) {
                            // 참가하기 클릭 했을 때
                        }
                    }

                }

                init {

                }

                fun setTitle(title: String) {

                }
            }
        }
    }

