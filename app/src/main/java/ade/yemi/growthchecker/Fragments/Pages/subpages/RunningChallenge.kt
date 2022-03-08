package ade.yemi.growthchecker.Fragments.Pages.subpages

import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.Data.DataStoreManager
import ade.yemi.growthchecker.Fragments.Pages.AnalyticsPage
import ade.yemi.growthchecker.Helpers.Graph.GenerateFloat
import ade.yemi.growthchecker.Helpers.Graph.cumulativelast
import ade.yemi.growthchecker.PopUp_Fragments.DailyAssessment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.*
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import ade.yemi.roomdatabseapp.Graph.CustomMarker
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_running_challenge.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.ArrayList

class RunningChallenge : Fragment() {
    lateinit var viewModel: ChallengeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_running_challenge, container, false)

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

        lifecycleScope.launch {
            val pushresult = async {
                context?.let { DataStoreManager.getBoolean(it, "assessmentnotification") }
            }
            var assessmentnotification = pushresult.await()
            var dialog = DailyAssessment()
            takeassess.setOnClickListener {
                takeassess.clicking()
                takeassess.shortvibrate()
                if (assessmentnotification == true){
                    (activity as MainActivity).showassessmentdialog(dialog)
                }
                else{
                    Toast.makeText(requireContext(), "You have filled an assessment for today", Toast.LENGTH_LONG).show()
                }
            }
        }

        trackgrowth.setOnClickListener {
            trackgrowth.clicking()
            trackgrowth.shortvibrate()
            (activity as MainActivity).ChangeToAnalytics(AnalyticsPage())
        }
        return view
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