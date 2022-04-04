package ade.yemi.growthchecker_free.Fragments.Pages.subpages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker_free.R

class Overview : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view  = inflater.inflate(R.layout.fragment_overview, container, false)

        return view
    }
}