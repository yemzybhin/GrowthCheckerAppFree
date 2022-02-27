package ade.yemi.growthchecker.Fragments.Pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.shortvibrate
import android.annotation.SuppressLint
import android.app.ActionBar
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import java.util.*
import kotlin.concurrent.schedule

class Menu : DialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        setStyle(STYLE_NO_TITLE, R.style.mydialog)
        var view = inflater.inflate(R.layout.fragment_menu, container, false)



        var myinfo = view.findViewById<CardView>(R.id.cd_MenuMyInfo)
        var cancel = view.findViewById<CardView>(R.id.cd_homemenucancel)

        myinfo.setOnClickListener {
            Toast.makeText( requireContext(), "Working", Toast.LENGTH_SHORT).show()
        }
        cancel.setOnClickListener {
            cancel.shortvibrate()
            dismiss()
        }
        return view
    }

}