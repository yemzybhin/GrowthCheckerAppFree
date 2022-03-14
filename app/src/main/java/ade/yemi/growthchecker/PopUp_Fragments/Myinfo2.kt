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
import android.content.Intent
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
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
                context?.let { DataStoreManager.getString(it, "ageee") }
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
        agee.setText(age)

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
                        DataStoreManager.saveString(
                            it,
                            "ageee",
                            agee.text.toString()
                        )
                    }
                }
                startActivity(Intent(requireContext(), MainActivity::class.java))
                dismiss()

            }
        }
            changeimage.setOnClickListener {
                var dialog = ChangeImages()
                (activity as MainActivity).ShowMainpopUp(changeimage,dialog)
                dismiss()
            }
        }
        cancel.setOnClickListener {
            cancel.shortvibrate()
            cancel.clicking()
            dismiss()
        }
        return popup
    }
}