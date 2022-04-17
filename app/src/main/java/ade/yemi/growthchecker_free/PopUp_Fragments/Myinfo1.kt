package ade.yemi.growthchecker_free.PopUp_Fragments

import ade.yemi.growthchecker_free.Activities.MainActivity
import ade.yemi.growthchecker_free.Data.DataStoreManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.clicking
import ade.yemi.growthchecker_free.Utilities.setimage
import ade.yemi.growthchecker_free.Utilities.shortvibrate
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Myinfo1 : DialogFragment() {
    private var name = "Anonymous"
    private var age = 0
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
                context?.let { DataStoreManager.getInt(it, "ageee") }
            }
            val pushresult3 = async {
                context?.let { DataStoreManager.getInt(it, "picnum") }
            }
             name = pushresult1.await()!!
             age = pushresult2.await()!!
             picnum = pushresult3.await()!!


            title.text = "Hi, $name"
            namee.text = name
            agee.text = age.toString()
            setimage(image, picnum)
            }
            edit.setOnClickListener {
                (activity as MainActivity).loading()

                Handler().postDelayed({
                    dismiss()
                    var dialog = Myinfo2()
                    (activity as MainActivity).ShowMainpopUp(edit,dialog)

                }, 0)

            }

            cancel.setOnClickListener {
                cancel.clicking()
                cancel.shortvibrate()
                dismiss()
            }

        (activity as MainActivity).cancelload()
        return popup
    }

}