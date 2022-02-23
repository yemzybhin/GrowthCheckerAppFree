package ade.yemi.growthchecker.Fragments.Pages.subpages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.click
import ade.yemi.growthchecker.Utilities.zoom_in
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import java.util.*

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


        card1.setOnClickListener {
            card1.click()
        }
        card2.setOnClickListener {
            card2.click()
        }
        return view
    }

}