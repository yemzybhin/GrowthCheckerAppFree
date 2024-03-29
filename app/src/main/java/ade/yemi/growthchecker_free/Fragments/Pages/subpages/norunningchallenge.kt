package ade.yemi.growthchecker_free.Fragments.Pages.subpages

import ade.yemi.growthchecker_free.Activities.MainActivity
import ade.yemi.growthchecker_free.Fragments.Pages.AchievementsPage
import ade.yemi.growthchecker_free.Fragments.Pages.AnalyticsPage
import ade.yemi.growthchecker_free.Fragments.Pages.BaseViewStubFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.clicking
import ade.yemi.growthchecker_free.Utilities.shortvibrate
import ade.yemi.growthchecker_free.Utilities.zoom_in
import androidx.cardview.widget.CardView

class norunningchallenge : BaseViewStubFragment() {
    override fun onCreateViewAfterViewStubInflated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        var card1 = view.findViewById<CardView>(R.id.cd_norunning_startchallenge)
        var card2 = view.findViewById<CardView>(R.id.cd_norunning_viewbadges)
        card1.zoom_in()
        card2.zoom_in()

        card1.setOnClickListener {
            card1.shortvibrate()
            card1.clicking()
            (activity as MainActivity).ChangeToAnalytics(AnalyticsPage())
        }
        card2.setOnClickListener {
            card2.shortvibrate()
            card2.clicking()
            (activity as MainActivity).ChangeToAchivement(AchievementsPage())
        }
    }

    override fun getViewStubLayoutResource(): Int {
       return R.layout.fragment_norunningchallenge
    }

}