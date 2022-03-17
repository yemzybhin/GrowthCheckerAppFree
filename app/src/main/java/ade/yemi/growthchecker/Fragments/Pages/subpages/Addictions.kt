package ade.yemi.growthchecker.Fragments.Pages.subpages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.Addictionlist
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.shortvibrate
import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.fragment_addictions.view.*

class Addictions : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_addictions, container, false)
        var title = view.findViewById<TextView>(R.id.tv_addictiontitle)
        var overview = view.findViewById<TextView>(R.id.tv_overview)
        var symptoms = view.findViewById<TextView>(R.id.tv_symptoms)
        var causes = view.findViewById<TextView>(R.id.tv_causes)

        var addictions = Addictionlist()
        title.text = addictions[0].addiction
        overview.text = addictions[0].overview
        symptoms.text = addictions[0].symptoms
        causes.text = addictions[0].causes

        var cards = listOf<Button>(view.addiction1, view.addiction2, view.addiction3, view.addiction4, view.addiction5, view.addiction6, view.addiction7, view.addiction8, view.addiction9)
        for (i in 0..cards.size-1){

            cards[i].setOnClickListener {
                cards[i].clicking()
                cards[i].shortvibrate()
                for (j in 0..cards.size-1){
                    cards[j].setTextColor(ContextCompat.getColor(requireContext(), R.color.primarycolor2))
                    cards[j].setBackgroundColor(Color.parseColor("#FFFFFF"))
                }
                cards[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                cards[i].setBackgroundColor(Color.parseColor("#403765"))
                title.text = addictions[i].addiction
                overview.text = addictions[i].overview
                symptoms.text = addictions[i].symptoms
                causes.text = addictions[i].causes
            }
        }
        return view
    }
}