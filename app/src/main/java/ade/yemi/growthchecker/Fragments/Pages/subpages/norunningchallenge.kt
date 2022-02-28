package ade.yemi.growthchecker.Fragments.Pages.subpages

import ade.yemi.growthchecker.Activities.Activity2
import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.Fragments.Pages.AchievementsPage
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.delay
import ade.yemi.growthchecker.Utilities.shortvibrate
import ade.yemi.growthchecker.Utilities.zoom_in
import android.content.Intent
import androidx.cardview.widget.CardView

class norunningchallenge : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_norunningchallenge, container, false)
        var card1 = view.findViewById<CardView>(R.id.cd_norunning_startchallenge)
        var card2 = view.findViewById<CardView>(R.id.cd_norunning_viewbadges)
        card1.zoom_in()
        card2.zoom_in()

        var challengeintent = Intent(requireContext(), Activity2::class.java)
        card1.setOnClickListener {
            card1.shortvibrate()
            card1.clicking()
            challengeintent.putExtra("ActivityFragtoset", "DetailedChallengeView")
            startActivity(Intent(challengeintent))
        }
        card2.setOnClickListener {
            card2.shortvibrate()
            card2.clicking()
            (activity as MainActivity).ChangeToAchivement(AchievementsPage())
        }
        return view
    }

}