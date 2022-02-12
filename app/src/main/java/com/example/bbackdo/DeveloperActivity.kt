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
                    "안드로이드 개발,\n시키키 담당"))
            infodatas.add(infos("김은솔", "한한양대학교 컴퓨터소프트웨어학부\n" +
                    "안드로이드 개발,\n구원투수 담당"))
            infodatas.add(infos("임성덕", "한양대학교 컴퓨터소프트웨어학부\n" +
                    "안드로이드 개발, 파이어베이스 개발 담당"))
            infodatas.add(infos("이창현", "한양대학교 컴퓨터소프트웨어학부\n" +
                    "유니티 개발, 똑똑이 담당"))

            teamdatas.add(teams("안동소주", "상북도 안동에서 전승되어온 민속주로 증류식 소주."+
                    "쌀, 보리, 조, 수수, 콩 등 다섯가지 곡물을 물에 불린 후 시루에 쪄서 여기에다 누룩을 섞어 10일 가량 발효시켜 진술을 만든다. 이 진술을 솥에 담고 그 위에 소주고리를 얹어 불을 지피면 진술이 증류되어 소주가 만들어진다. "))
            teamdatas.add(teams("문배주", "한국의 전통 증류식 소주의 하나. 대한민국의 국가무형문화재 제86-1호." +
                    "평안도 지방에서 전승되어 오는 술로 술의 향기가 문배나무의 과실에서 풍기는 향기와 같아 붙여진 이름이다. " +
                    "한국 전통주로서는 특이하게도 쌀을 전혀 쓰지 않고 밀(누룩), 수수, 조만으로 만들어지는 것이 특징이다. "))
            teamdatas.add(teams("진도홍주", "진도섬에서 고려 초부터 전해져 오던 중 조선조에는 “지초주”라 하여 최고의 진상품으로 꼽혔다." +
                    "진도홍주는 진도산 쌀과 지초를 원재료로 사용하며, 술의 색상이 지초에서 용출되어 홍색이 아름답게 착색되어 시각적인 매력을 느끼게 함과 동시에 그 술맛과 향이 재래 증류주의 독특한 향기를 지니고 있다."))
            teamdatas.add(teams("이강주", "조선시대 3대 명주의 하나로 전통소주에 배와 생강이 들어간다 해서 이강주라 불리게 되었으며 향토문화재 제 6호로 지정된 25도의 약소주이며 이강고(梨薑膏)라고 부르기도 한다." +
                    "종래의 토종 누룩을 만들어 백미를 원료로 해서 약주를 만든 후 이 술로 토종 소주를 내리고 여기에 배, 생강, 울금, 계피, 꿀을 넣어 장기간 후숙시켜 마신다. "))



            teamRecycler.adapter = infoadapter
            teamRecycler2.adapter = teaminfoadapter

            backbutton.setOnClickListener {
                finish()
            }
            sourceText.text = "* 학 이미지 " +
                    "https://www.culture.go.kr/tradition/traditionalDesignPatternView.do?seq=9179&did=77708&reffer=shape&dpid=140&sType=02&xtaxonomy=%EB%8F%99%EB%AC%BC%EB%AC%B8&uType=2D&cPage=3" +
                    "\n\n* 용 이미지 " +
                    "https://www.culture.go.kr/tradition/traditionalDesignPatternView.do?seq=8523&did=35701&reffer=shape&dpid=140&xtaxonomy=%EB%8F%99%EB%AC%BC%EB%AC%B8&xtaxonomy2=%EC%9A%A9%EB%AC%B8&uType=2D&gubun=all&cPage=" +
                    "\n\n* 경복궁 이미지 " +
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
                    teamcontent.movementMethod = ScrollingMovementMethod.getInstance()

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
