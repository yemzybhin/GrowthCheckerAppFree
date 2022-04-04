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
import android.content.Intent
import android.os.Handler
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Myinfo2 : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var popup = inflater.inflate(R.layout.fragment_myinfo2, container, false)



        var image = popup.findViewById<ImageView>(R.id.iv_myinfo2image)
        var changeimage = popup.findViewById<TextView>(R.id.tv_changeimage)
        var title = popup.findViewById<TextView>(R.id.tv_myinfotitle2)
        var namee = popup.findViewById<EditText>(R.id.et_myinfoname)
        var agee = popup.findViewById<EditText>(R.id.et_myinfoage)
        var save = popup.findViewById<Button>(R.id.btn_saveMyinfodetails)
        var cancel = popup.findViewById<CardView>(R.id.cd_myinfopopupcancel22)

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
            var name = pushresult1.await()!!
            var age = pushresult2.await()!!
            var picnum = pushresult3.await()!!


            title.text = "Hi, $name"
        setimage(image, picnum)
        namee.setText(name)
        agee.setText(age.toString())

        save.setOnClickListener {
            save.clicking()
            save.shortvibrate()
            if (namee.text.isEmpty() || agee.text.isEmpty()) {
                Toast.makeText(requireContext(), "Kindly Enter all details", Toast.LENGTH_SHORT)
                    .show()
            } else {
                lifecycleScope.launch {
                    context?.let {
                        DataStoreManager.saveString(
                            it,
                            "name",
                            namee.text.toString()
                        )
                    }
                    context?.let {
                        DataStoreManager.saveInt(
                            it,
                            "ageee",
                            agee.text.toString().toInt()
                        )
                    }
                }
                startActivity(Intent(requireContext(), MainActivity::class.java))
                dismiss()

            }
        }
            changeimage.setOnClickListener {

                if (namee.text.isEmpty() || agee.text.isEmpty()) {
                    Toast.makeText(requireContext(), "No detail entered", Toast.LENGTH_SHORT)
                            .show()
                } else {
                    (activity as MainActivity).loading()

                    Handler().postDelayed({
                        lifecycleScope.launch {
                            context?.let {
                                DataStoreManager.saveString(
                                    it,
                                    "name",
                                    namee.text.toString()
                                )
                            }
                            context?.let {
                                DataStoreManager.saveInt(
                                    it,
                                    "ageee",
                                    agee.text.toString().toInt()
                                )
                            }
                        }
                        dismiss()
                        var dialog = ChangeImages()

                        (activity as MainActivity).ShowMainpopUp(changeimage,dialog)

                    }, 0)
                }

            }
        }
        cancel.setOnClickListener {
            cancel.shortvibrate()
            cancel.clicking()
            dismiss()
        }
        var mAdView : AdView
        MobileAds.initialize(requireContext()) {}
        mAdView = popup.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        (activity as MainActivity).cancelload()
        return popup
    }
}