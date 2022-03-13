package ade.yemi.growthchecker.PopUp_Fragments

import ade.yemi.growthchecker.Data.DataStoreManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
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
        setimage(image, picnum)
        namee.setText(name)
        agee.setText(age)

        save.setOnClickListener {
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
                dismiss()
            }
        }
            changeimage.setOnClickListener {

            }
        }
        cancel.setOnClickListener {
            dismiss()
        }
        return popup
    }
    private fun setimage(imageView: ImageView, int: Int){
        when(int){
            1 -> imageView.setImageResource(R.drawable.a40)
            2 -> imageView.setImageResource(R.drawable.a41)
            3 -> imageView.setImageResource(R.drawable.a42)
            4 -> imageView.setImageResource(R.drawable.a43)
            5 -> imageView.setImageResource(R.drawable.a44)
            6 -> imageView.setImageResource(R.drawable.a45)
            7 -> imageView.setImageResource(R.drawable.a46)
            8 -> imageView.setImageResource(R.drawable.a47)
            9 -> imageView.setImageResource(R.drawable.a48)
            10 -> imageView.setImageResource(R.drawable.a49)
            11 -> imageView.setImageResource(R.drawable.a50)
            12 -> imageView.setImageResource(R.drawable.a51)
            13 -> imageView.setImageResource(R.drawable.a53)
            14 -> imageView.setImageResource(R.drawable.a54)
            15 -> imageView.setImageResource(R.drawable.a55)
            16 -> imageView.setImageResource(R.drawable.a56)
            else -> imageView.setImageResource(R.drawable.a40)
        }
    }
}