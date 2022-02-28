package ade.yemi.growthchecker.Fragments.Pages

import ade.yemi.growthchecker.Activities.MainActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.shortvibrate
import android.content.Intent
import androidx.cardview.widget.CardView

class Startchallenge : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       var view = inflater.inflate(R.layout.fragment_startchallenge, container, false)
        var start = view.findViewById<CardView>(R.id.cd_challengestartstart)

        start.setOnClickListener {
            start.clicking()
            start.shortvibrate()
            startActivity(Intent(requireContext(), MainActivity :: class.java))
        }
        return view
    }
}