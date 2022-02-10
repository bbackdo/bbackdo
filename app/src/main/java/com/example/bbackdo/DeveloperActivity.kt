package com.example.bbackdo

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bbackdo.databinding.*
import com.example.bbackdo.dto.Room
import com.example.bbackdo.lib.Authentication
import com.example.bbackdo.lib.Database
import com.example.bbackdo.lib.Util
import com.google.firebase.database.ktx.getValue
import kotlinx.parcelize.RawValue
import splitties.activities.start
import splitties.bundle.putExtras
import java.util.*
import kotlin.collections.ArrayList

class DeveloperActivity: AppCompatActivity() {

    private val bind by lazy {
        ActivityDeveloperBinding.inflate(layoutInflater)
    }

    data class infos(
        val name: String = "",
        val content: String = ""
    )
    data class teams(
        val title: String = "",
        val content: String = ""
    )

    private val infodatas = arrayListOf<infos>()
    private val teamdatas = arrayListOf<teams>()

    private val infoadapter = InfoAdapter(this, infodatas)
    private val teaminfoadapter = TeamInfoAdapter(this, teamdatas)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(bind){
            setContentView(root)
            infodatas.add(infos("김이영", "한양대학교 컴퓨터소프트웨어학부\n" +
                    "유니티 개발, 디자인 담당"))
            infodatas.add(infos("장보경", "한양대학교 컴퓨터소프트웨어학부\n" +
                    "유니티 개발, 아이디어 담당"))
            infodatas.add(infos("이가은", "한양대학교 컴퓨터소프트웨어학부\n" +
                    "안드로이드 개발, 파이어베이스 개발 담당"))
            infodatas.add(infos("김은솔", "한한양대학교 컴퓨터소프트웨어학부\n" +
                    "안드로이드 개발, 구원투수 담당"))
            infodatas.add(infos("임성덕", "한양대학교 컴퓨터소프트웨어학부\n" +
                    "안드로이드 개발, 파이어베이스 개발 담당"))
            infodatas.add(infos("이창현", "한양대학교 컴퓨터소프트웨어학부\n" +
                    "유니티 개발, 디자인 담당"))

            teamdatas.add(teams("안동소주", "content"))
            teamdatas.add(teams("문배주", "content"))
            teamdatas.add(teams("진도홍주", "content"))
            teamdatas.add(teams("이강주", "content"))

            teamRecycler.adapter = infoadapter
            teamRecycler2.adapter = teaminfoadapter

            backbutton.setOnClickListener {
                finish()
            }
            sourceText.text = "학:\n " +
                    "https://www.culture.go.kr/tradition/traditionalDesignPatternView.do?seq=9179&did=77708&reffer=shape&dpid=140&sType=02&xtaxonomy=%EB%8F%99%EB%AC%BC%EB%AC%B8&uType=2D&cPage=3" +
                    "\n용:\n" +
                    "https://www.culture.go.kr/tradition/traditionalDesignPatternView.do?seq=8523&did=35701&reffer=shape&dpid=140&xtaxonomy=%EB%8F%99%EB%AC%BC%EB%AC%B8&xtaxonomy2=%EC%9A%A9%EB%AC%B8&uType=2D&gubun=all&cPage=\n" +
                    "\n경복궁:\n" +
                    "https://kor.pngtree.com/freepng/south-korea-gyeongbokgung-building-clip-art_6621934.html"
            sourceText.movementMethod = ScrollingMovementMethod.getInstance()

        }

    }

    inner class InfoAdapter(
        private val context: Context,
        private var listData: ArrayList<infos>
    ) :
        RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

        val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoAdapter.ViewHolder {
            val view =
                DeveloperinfoBinding.inflate(inflater, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: InfoAdapter.ViewHolder, position: Int) {
            val info: infos = listData[position]
            holder.binding(info)
        }

        override fun getItemCount(): Int {
            return listData.size
        }

        inner class ViewHolder(private val bind: DeveloperinfoBinding) :
            RecyclerView.ViewHolder(bind.root) {
            fun binding(info: infos) {
                with(bind) {
                    developerName.text = info.name
                    developerinfo.text = info.content
                }

            }

        }
    }

    inner class TeamInfoAdapter(
        private val context: Context,
        private var listData: ArrayList<teams>
    ) :
        RecyclerView.Adapter<TeamInfoAdapter.ViewHolder>() {

        val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamInfoAdapter.ViewHolder {
            val view =
                DeveloperteamBinding.inflate(inflater, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: TeamInfoAdapter.ViewHolder, position: Int) {
            val team: teams = listData[position]
            holder.binding(team)
        }

        override fun getItemCount(): Int {
            return listData.size
        }

        inner class ViewHolder(private val bind: DeveloperteamBinding) :
            RecyclerView.ViewHolder(bind.root) {
            fun binding(team: teams) {
                with(bind) {
                    title.text = team.title
                    teamcontent.text = team.content

                    teamimg.setImageResource(
                        when(title.text){
                            "안동소주" -> R.drawable.team1img
                            "문배주" -> R.drawable.team2img
                            "진도홍주" -> R.drawable.team3img
                            "이강주" -> R.drawable.team4img

                            else -> R.drawable.team1img
                        }
                    )
                }

            }

        }
    }
}
