package ade.yemi.growthchecker_free.Fragments.Pages.subpages

import ade.yemi.growthchecker_free.Activities.MainActivity
import ade.yemi.growthchecker_free.Fragments.Pages.AnalyticsPage
import ade.yemi.growthchecker_free.Fragments.Pages.BaseViewStubFragment
import ade.yemi.growthchecker_free.Helpers.Graph.GenerateFloat
import ade.yemi.growthchecker_free.Helpers.Graph.cumulativelast
import ade.yemi.growthchecker_free.PopUp_Fragments.QuitChallenge
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.*
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import ade.yemi.roomdatabseapp.Graph.CustomMarker
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class RunningChallenge : BaseViewStubFragment() {
    lateinit var viewModel: ChallengeViewModel
    override fun onCreateViewAfterViewStubInflated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        var scrollview = view.findViewById<HorizontalScrollView>(R.id.HS_ungoingpage)
        var cardcontainer = view.findViewById<CardView>(R.id.cd_ungoingpagecontainer)
        var reportcontainer = view.findViewById<CardView>(R.id.cd_ungoingpagereport)
        var lineChartHome = view.findViewById<LineChart>(R.id.lineChartHome)
        var handlertext = view.findViewById<TextView>(R.id.tv_nochartdata)
        var challengeinfo = view.findViewById<TextView>(R.id.tv_runningChallengetype)
        var challengecumulative = view.findViewById<TextView>(R.id.tv_runningCumulative)
        var comment1 = view.findViewById<TextView>(R.id.tv_challengestartcomment1)
        var comment2 = view.findViewById<TextView>(R.id.tv_challengestartcomment2)
        var comment3 = view.findViewById<TextView>(R.id.tv_challengestartcomment3)
        var image = view.findViewById<ImageView>(R.id.iv_runningChallengeImage)
        var takeassess = view.findViewById<CardView>(R.id.cd_takeassessmentRunning)
        var trackgrowth = view.findViewById<CardView>(R.id.cd_trackgrowthRunning)
        var daydisplay = view.findViewById<TextView>(R.id.tv_runningDay)




        reportcontainer.zoom_in()
        scrollview.zoom_in()
        cardcontainer.zoom_in()
        viewModel = ViewModelProviders.of(this).get(ChallengeViewModel::class.java)
        viewModel.getAllChallengesObservers().observe(requireActivity(), Observer {

            var details = it.first().Point
            daydisplay.text = "Day ${details.size}"
            var commenntings = thecomments(cumulativelast(details))
            challengecumulative.text = "${cumulativelast(details)} points"
            challengeinfo.text = it.first().ChallengeType
            challengeimage(it.first().Days, image)

            comment1.text = commenntings.state
            comment2.text = commenntings.solution
            comment3.text = commenntings.encourage


            if (details.size < 1){
                handlertext.visibility = View.VISIBLE
            }else{
                handlertext.visibility = View.GONE
            }
            val entries = GenerateFloat(details)
            val vl = LineDataSet(entries, "Cumulative (Zoom and pan with finger)")
            vl.setDrawValues(false)
            vl.setDrawFilled(true)
            vl.lineWidth = 3f
            vl.fillColor = R.color.purple_700
            vl.fillAlpha = R.color.primary3
            lineChartHome.xAxis.labelRotationAngle = 0f
            lineChartHome.data = LineData(vl)
            lineChartHome.axisRight.isEnabled = false
            lineChartHome.setTouchEnabled(true)
            lineChartHome.setPinchZoom(true)
            lineChartHome.description.text = "Days"
            lineChartHome.setNoDataText("")
            lineChartHome.animateX(100, Easing.EaseInExpo)
            val markerView = context?.let { it1 -> CustomMarker(it1, R.layout.marker_view) }
            lineChartHome.marker = markerView
        })


        var dialog = QuitChallenge()
        takeassess.setOnClickListener {
            takeassess.clicking()
            takeassess.shortvibrate()
            (activity as MainActivity).showassessmentdialog(dialog)
        }


        trackgrowth.setOnClickListener {
            trackgrowth.clicking()
            trackgrowth.shortvibrate()
            (activity as MainActivity).ChangeToAnalytics(AnalyticsPage())
        }
    }

    override fun getViewStubLayoutResource(): Int {
        return R.layout.fragment_running_challenge
    }

    private fun challengeimage(string: String, imageView: ImageView){
        when(string){
            "14" -> imageView.setImageResource(R.drawable.forteendays)
            "30" -> imageView.setImageResource(R.drawable.thirtydays)
            "60" -> imageView.setImageResource(R.drawable.sixtydays)
            "100" -> imageView.setImageResource(R.drawable.hundreddays)
            "200" -> imageView.setImageResource(R.drawable.twohundreddays)
        }
    }
}