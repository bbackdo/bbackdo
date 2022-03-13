package com.bback.bbackdo

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bback.bbackdo.TeamActivity.Extras.room
import com.bback.bbackdo.databinding.CellMemberBinding
import com.bback.bbackdo.databinding.LayoutTeamBinding
import com.bback.bbackdo.dto.Team
import com.bback.bbackdo.dto.User
import com.bback.bbackdo.lib.Authentication
import com.bback.bbackdo.lib.Authentication.uid
import com.bback.bbackdo.lib.Database
import com.bback.bbackdo.lib.Util
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
                teamName.isSelected = true
                teamimg.setImageResource(
                    when(teamName.text){
                        "안동소주 팀" -> R.drawable.team1img
                        "문배주 팀" -> R.drawable.team2img
                        "진도홍주 팀" -> R.drawable.team3img
                        "이강주 팀" -> R.drawable.team4img

                        else -> R.drawable.team1img
                    }
                )

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

                    teamName.setOnClickListener{
                        Database.getReference("users/$uid/teams").removeValue()
                        listData.map {
                            Database.getReference("teams/${it.tid}/members/$uid").removeValue()
                        }
                        //memberList.put(it.key, membertest)
                        Database.getReference("").updateChildren(updates as Map<String, Any>).addOnSuccessListener {  }
                        //기존 팀에서 삭제
                        Log.d("test",team.tid.toString())
                    }
                        





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
                memberIdText.isSelected = true
                if (user.readyState) {
                    readyStateText.setTextColor(Color.parseColor("#1F177C"))

                }
                else {
                    readyStateText.setTextColor(Color.LTGRAY)
                }
            }


        }

    }
}


