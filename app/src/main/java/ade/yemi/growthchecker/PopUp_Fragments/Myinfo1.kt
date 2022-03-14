package ade.yemi.growthchecker.PopUp_Fragments

import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.Data.DataStoreManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.setimage
import ade.yemi.growthchecker.Utilities.shortvibrate
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Myinfo1 : DialogFragment() {
    private var name = "Anonymous"
    private var age = ""
    private var picnum = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     var popup = inflater.inflate(R.layout.fragment_myinfo1, container, false)

        var cancel = popup.findViewById<CardView>(R.id.cd_myinfopopupcancel1)
        var title = popup.findViewById<TextView>(R.id.tv_myinfotitle)
        var namee = popup.findViewById<TextView>(R.id.tv_myinfoname)
        var agee = popup.findViewById<TextView>(R.id.tv_myinfoage)
        var image = popup.findViewById<ImageView>(R.id.iv_myinfoprofile1)
        var edit = popup.findViewById<Button>(R.id.btn_myinfoedit1)

        lifecycleScope.launch {
            val pushresult1 = async {
                context?.let { DataStoreManager.getString(it, "name") }
            }
            val pushresult2 = async {
                context?.let { DataStoreManager.getString(it, "ageee") }
            }
            val pushresult3 = async {
                context?.let { DataStoreManager.getInt(it, "picnum") }
            }
             name = pushresult1.await()!!
             age = pushresult2.await()!!
             picnum = pushresult3.await()!!


            title.text = "Hi, $name"
            namee.text = name
            agee.text = age
            setimage(image, picnum)
            }
            edit.setOnClickListener {
                var dialog = Myinfo2()
                (activity as MainActivity).ShowMainpopUp(edit,dialog)
                dismiss()
            }

            cancel.setOnClickListener {
                cancel.clicking()
                cancel.shortvibrate()
                dismiss()
            }

        return popup
    }

}