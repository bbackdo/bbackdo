package com.example.bbackdo

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bbackdo.TeamActivity.Extras.room
import com.example.bbackdo.databinding.CellMemberBinding
import com.example.bbackdo.databinding.LayoutTeamBinding
import com.example.bbackdo.dto.Team
import com.example.bbackdo.dto.User
import com.example.bbackdo.lib.Authentication
import com.example.bbackdo.lib.Authentication.uid
import com.example.bbackdo.lib.Database
import com.example.bbackdo.lib.Util
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.getValue
import splitties.activities.start
import splitties.bundle.putExtras

class TeamAdapter(
    private val context: Context,
    private var listData: ArrayList<Team>
) :
    RecyclerView.Adapter<TeamAdapter.ViewHolder>() {


    val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamAdapter.ViewHolder {
        val view =
            LayoutTeamBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamAdapter.ViewHolder, position: Int) {
        val team: Team = listData[position]

        holder.binding(team)
    }

    override fun getItemCount(): Int {
        return listData.size
    }


    inner class ViewHolder(private val bind: LayoutTeamBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun binding(team: Team) {
            with(bind) {

                teamName.setText(team.name)

                //중첩 adapter
                var dataList = arrayListOf<User>()
                var memberList = HashMap<String, Any>()
                Database.getReference("teams/${team.tid}/members").get().addOnSuccessListener {
                    val membertest = it.value
                    if (it.getValue() != null){
                        Log.d("hhhhhrang", it.getValue().toString())
                        memberList =  it.getValue<HashMap<String, Any>>()!!

                    }

                    val pastTid = team.tid
                    val updates = hashMapOf(
                        "teams/${team.tid}/members/$uid" to false,
                        "users/$uid/teams/${team.tid}" to false

                    )
                    //Toast.makeText(context, "${teamName.text} ${memberList}", Toast.LENGTH_SHORT).show()

                    Log.d("test",team.tid.toString())
                    """
                          teamName.setOnClickListener{
                        memberList.add(membertest)
                        Database.getReference("").updateChildren(updates as Map<String, Any>).addOnSuccessListener {  }
                        //기존 팀에서 삭제
                        Log.d("test",team.tid.toString())
                    }
                        
                    """.trimIndent()




                    Database.getReference("users").get().addOnSuccessListener { users ->
                        users.children.forEach {
                            //Toast.makeText(context, "$memberList", Toast.LENGTH_SHORT).show()
                            //Toast.makeText(context, "${it.key}", Toast.LENGTH_SHORT).show()
                            if (it.key in memberList.keys){
                                dataList.add(it.getValue<User>()!!)
                            }
                            //Toast.makeText(context, "${dataList}", Toast.LENGTH_SHORT).show()
//                        Log.d("memberList", memberList.toString())
                            //if ("{".plus(it.key).plus("=false}") in memberList) {
//                            Log.d("추가된 유저", it.getValue<User>().toString())
                            //  dataList.add(it.getValue<User>()!!)
                            //}
                            val adapter = EachTeamAdapter(context, dataList)

                            teamRcv.adapter = adapter
                        }
                    }
                }


            }


        }

    }

}

class EachTeamAdapter(
    private val context: Context,
    private var listData: ArrayList<User>
) :
    RecyclerView.Adapter<EachTeamAdapter.ViewHolder>() {


    val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EachTeamAdapter.ViewHolder {
        val view =
            CellMemberBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EachTeamAdapter.ViewHolder, position: Int) {
        val user: User = listData[position]
        holder.binding(user)
    }

    override fun getItemCount(): Int {
        return listData.size
    }


    inner class ViewHolder(private val bind: CellMemberBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun binding(user: User) {
            with(bind) {
                memberIdText.text = user.nickname
                if (user.readyState) {
                    readyStateText.text = "Ready"
                    readyStateText.setTextColor(Color.parseColor("#009000"))

                }
                else {
                    readyStateText.text = "Not Ready"
                    readyStateText.setTextColor(Color.DKGRAY)
                }
            }


        }

    }
}


