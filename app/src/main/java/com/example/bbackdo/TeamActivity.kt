package com.example.bbackdo

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.bbackdo.databinding.ActivityGameBinding
import com.example.bbackdo.databinding.AlertdialogEdittextBinding
import com.example.bbackdo.databinding.DialogEnterRoomBinding
import com.example.bbackdo.databinding.ItemRecyclerRoomBinding
import com.example.bbackdo.dto.Room
import com.example.bbackdo.lib.Authentication
import com.example.bbackdo.lib.Database
import com.example.bbackdo.lib.Util
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.getValue
import splitties.activities.start
import splitties.bundle.BundleSpec
import splitties.bundle.bundle
import splitties.bundle.putExtras
import splitties.bundle.withExtras

class TeamActivity : AppCompatActivity() {
    object Extras : BundleSpec(){
        var room: Room by bundle()
    }

    private val room by lazy {
        withExtras(Extras){
            room
        }
    }

    private val bind by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
    }
    private val dataList = arrayListOf<Room>()
    private val adapter = TeamAdapter(this@TeamActivity, dataList)






}