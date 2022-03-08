package ade.yemi.growthchecker.PopUp_Fragments

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
import ade.yemi.growthchecker.Utilities.setOnSingleClickListener
import ade.yemi.growthchecker.Utilities.shortvibrate
import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.graphics.alpha
import androidx.fragment.app.DialogFragment
import java.util.*
import kotlin.concurrent.schedule

class Menu : DialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_menu, container, false)
        var myinfo = view.findViewById<CardView>(R.id.cd_MenuMyInfo)
        var addiction = view.findViewById<CardView>(R.id.cd_menuAddiction)
        var myrating = view.findViewById<LinearLayout>(R.id.LL_menuMyrating)
        var settingss = view.findViewById<CardView>(R.id.cd_menuSettings)
        var cancel = view.findViewById<CardView>(R.id.cd_homemenucancel)


        var myinfopopup = PopUp_Myinfo()
        myinfo.setOnSingleClickListener {
            (activity as MainActivity).ShowMainpopUp(myinfo, myinfopopup)
            delay(200)
            dismiss()
        }
        addiction.setOnSingleClickListener {
            addiction.clicking()
            addiction.shortvibrate()
            Toast.makeText( requireContext(), "Working", Toast.LENGTH_SHORT).show()
        }
        myrating.setOnSingleClickListener {
            myrating.clicking()
            myrating.shortvibrate()
            Toast.makeText( requireContext(), "Working", Toast.LENGTH_SHORT).show()
        }
        settingss.setOnSingleClickListener {
            settingss.clicking()
            settingss.shortvibrate()
            Toast.makeText( requireContext(), "Working", Toast.LENGTH_SHORT).show()
        }
        cancel.setOnSingleClickListener {
            cancel.shortvibrate()
            dismiss()
        }
        return view
    }
}