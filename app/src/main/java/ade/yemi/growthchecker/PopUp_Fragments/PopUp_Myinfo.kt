package ade.yemi.growthchecker.PopUp_Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.shortvibrate
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment

class PopUp_Myinfo : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_pop_up__myinfo, container, false)
        var cancell = view.findViewById<CardView>(R.id.cd_myinfopopupcancel)

        cancell.setOnClickListener {
            cancell.shortvibrate()
            cancell.clicking()
            dismiss()
        }
        return view
    }
}