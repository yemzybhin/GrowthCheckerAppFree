package ade.yemi.growthchecker_free.Fragments.Pages.subpages

import ade.yemi.growthchecker_free.Data.DataStoreManager
import ade.yemi.growthchecker_free.Fragments.Pages.BaseViewStubFragment
import ade.yemi.growthchecker_free.Helpers.Graph.GenerateFloat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.*
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import ade.yemi.roomdatabseapp.Graph.CustomMarker
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_analyticsfragment1.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class Analyticsfragment1 : BaseViewStubFragment() {
    private var adder1 = ""
    private var adder2 = ""
    private var adder3 = ""
    lateinit var viewModel: ChallengeViewModel
    override fun onCreateViewAfterViewStubInflated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        var adderview1 = view.findViewById<TextView>(R.id.tv_analytics1adder1)
        var adderview2 = view.findViewById<TextView>(R.id.tv_analytics1adder2)
        var adderview3 = view.findViewById<TextView>(R.id.tv_analytics1adder3)
        var recentlyadded = view.findViewById<TextView>(R.id.tv_analytics1recentlyadded)
        var column1 = view.findViewById<TextView>(R.id.tv_analyticstabcolumn1)
        var column2 = view.findViewById<TextView>(R.id.tv_analyticstabcolumn2)
        var column3 = view.findViewById<TextView>(R.id.tv_analyticstabcolumn3)
        var handlertextt = view.findViewById<TextView>(R.id.tv_nochartdata11)
        var currenttext = view.findViewById<TextView>(R.id.tv_analyticscurrentchallenge)

        var currentimage = view.findViewById<ImageView>(R.id.iv_analyticscurrentimage)


        lifecycleScope.launch {
            val pushresult1 = async {
                context?.let { DataStoreManager.getString(it, "Ungoingchallengeadder1") }
            }
            val pushresult2 = async {
                context?.let { DataStoreManager.getString(it, "Ungoingchallengeadder2") }
            }
            val pushresult3 = async {
                context?.let { DataStoreManager.getString(it, "Ungoingchallengeadder3") }
            }
            adder1 = pushresult1.await()!!
            adder2 = pushresult2.await()!!
            adder3 = pushresult3.await()!!

            adderview1.text = "$adder1: +10 Points"
            adderview2.text = "$adder2: +7 Points"
            adderview3.text = "$adder3: +5 Points"

        }


        viewModel = ViewModelProviders.of(this).get(ChallengeViewModel::class.java)
        viewModel.getAllChallengesObservers().observe(requireActivity(), Observer {
            setimage(it.first().Days, currentimage)
            currenttext.text = it.first().ChallengeType
            var details = it.first().Point
            var sub = mutableListOf("0")


            if (details.size < 1){
                handlertextt.visibility = View.VISIBLE
            }else{
                handlertextt.visibility = View.GONE
            }

            if (details != emptyList<String>()){
                recentlyadded.text = "${it.first().Point.last()} Points"
                column1.text = generatedays(it.first().Point)
                column2.text = generatepoints(it.first().Point)
                column3.text = generatecumulative(it.first().Point)
            }else{
                recentlyadded.text = "0 Points"
                column1.text = generatedays(sub)
                column2.text = generatepoints(sub)
                column3.text = generatecumulative(sub)
            }
            val entries = GenerateFloat(details)
            val vl = LineDataSet(entries, "Cumulative (Zoom and pan with finger)")
            vl.setDrawValues(false)
            vl.setDrawFilled(true)
            vl.lineWidth = 3f
            vl.fillColor = R.color.purple_700
            vl.fillAlpha = R.color.primary3
            lc_graphforanalytics1.xAxis.labelRotationAngle = 0f
            lc_graphforanalytics1.data = LineData(vl)
            lc_graphforanalytics1.axisRight.isEnabled = false
            lc_graphforanalytics1.setTouchEnabled(true)
            lc_graphforanalytics1.setPinchZoom(true)
            lc_graphforanalytics1.description.text = "Days"
            lc_graphforanalytics1.setNoDataText("")
            lc_graphforanalytics1.animateX(100, Easing.EaseInExpo)
            val markerView = CustomMarker(requireContext(), R.layout.marker_view)
            lc_graphforanalytics1.marker = markerView
        })
    }

    override fun getViewStubLayoutResource(): Int {
        return R.layout.fragment_analyticsfragment1
    }

    private fun setimage(string: String, imageView: ImageView){
        when(string){
            "14" -> imageView.setImageResource(R.drawable.forteendays)
            "30" -> imageView.setImageResource(R.drawable.thirtydays)
            "60" -> imageView.setImageResource(R.drawable.sixtydays)
            "100" -> imageView.setImageResource(R.drawable.hundreddays)
            "200" -> imageView.setImageResource(R.drawable.twohundreddays)
        }
    }


}