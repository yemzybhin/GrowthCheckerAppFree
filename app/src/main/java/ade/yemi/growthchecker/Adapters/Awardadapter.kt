package ade.yemi.growthchecker.Adapters

import ade.yemi.growthchecker.Helpers.Graph.GenerateFloat
import ade.yemi.growthchecker.Helpers.Graph.cumulativelast
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.getpoints
import ade.yemi.growthchecker.Utilities.setrankimage
import ade.yemi.growthchecker.Utilities.setranking
import ade.yemi.roomdatabseapp.Data.Challenge
import ade.yemi.roomdatabseapp.Graph.CustomMarker
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.awardlayout.view.*
import kotlinx.android.synthetic.main.olderchallengesview.view.*

class Awardadapter (): RecyclerView.Adapter<Awardadapter.MyViewHolder>() {

    var items  = ArrayList<Challenge>()

    fun setListData(data: ArrayList<Challenge>) {
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Awardadapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.awardlayout, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Awardadapter.MyViewHolder, position: Int) {

        val context = holder.itemView.context
        holder.bind(items[position])
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val awardimage = view.iv_awardimage
        val challengetext = view.tv_awardschallengetype
        val challengeimage = view.iv_awardschallengeimage
        val pointsearned = view.tv_awardpoint
        val attainable = view.tv_awardpointattainable
        val ranking = view.tv_awardranking

        fun bind(data: Challenge) {
            var awardpoints = getpoints(data.Point, data)
            challengetext.text = data.ChallengeType
            when(data.Days){
                "14" -> challengeimage.setImageResource(R.drawable.forteendays)
                "30" -> challengeimage.setImageResource(R.drawable.thirtydays)
                "60" -> challengeimage.setImageResource(R.drawable.sixtydays)
                "100" -> challengeimage.setImageResource(R.drawable.hundreddays)
                "200" -> challengeimage.setImageResource(R.drawable.twohundreddays)
            }
            pointsearned.text = "Total Points Earned: ${awardpoints.attained}"
            attainable.text = "Total Attainable Points: ${awardpoints.attainable}"
            ranking.text = setranking(awardpoints.attained, awardpoints.attainable)
            setrankimage(awardpoints.attained, awardpoints.attainable, data.Days, awardimage)
        }
    }
}