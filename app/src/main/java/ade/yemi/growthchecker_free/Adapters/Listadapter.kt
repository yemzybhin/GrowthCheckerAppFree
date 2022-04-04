package ade.yemi.growthchecker_free.Adapters

import ade.yemi.growthchecker_free.Helpers.Graph.GenerateFloat
import ade.yemi.growthchecker_free.Helpers.Graph.cumulativelast
import ade.yemi.growthchecker_free.R
import ade.yemi.roomdatabseapp.Data.Challenge
import ade.yemi.roomdatabseapp.Graph.CustomMarker
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.olderchallengesview.view.*

class Listadapter (): RecyclerView.Adapter<Listadapter.MyViewHolder>() {

    var items  = ArrayList<Challenge>()

    fun setListData(data: ArrayList<Challenge>) {
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Listadapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.olderchallengesview, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Listadapter.MyViewHolder, position: Int) {

        val context = holder.itemView.context
        holder.bind(items[position])
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val challengetype = view.tv_olderchallnegeschallenge
        val cumulative = view.tv_concludedcumulative
        val graph = view.lc_olderchallenges

        fun bind(data: Challenge) {
            challengetype.text = data.ChallengeType
            var toput = cumulativelast(data.Point)
            cumulative.text = "Cumulative point is $toput"
            val entries = GenerateFloat(data.Point)
            val vl = LineDataSet(entries, "Cumulative")
            vl.setDrawValues(false)
            vl.setDrawFilled(true)
            vl.lineWidth = 3f
            vl.fillColor = R.color.purple_700
            vl.fillAlpha = R.color.teal_700
            graph.xAxis.labelRotationAngle = 0f
            graph.data = LineData(vl)
            graph.axisRight.isEnabled = false
            graph.setTouchEnabled(true)
            graph.setPinchZoom(true)
            graph.description.text = "Days"
            graph.setNoDataText("Not Enough Data!")
            graph.animateX(500, Easing.EaseInExpo)
            val markerView = CustomMarker(itemView.context, R.layout.marker_view)
            graph.marker = markerView
        }
    }
}