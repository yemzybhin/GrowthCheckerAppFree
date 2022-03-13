package ade.yemi.growthchecker.PopUp_Fragments

import ade.yemi.growthchecker.Activities.Activity2
import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.Data.DataStoreManager
import ade.yemi.growthchecker.Fragments.Pages.AboutsPage
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
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Build
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.graphics.alpha
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.profileimages.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.w3c.dom.Text
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
        var abouts = view.findViewById<CardView>(R.id.cd_menuSettings)
        var cancel = view.findViewById<CardView>(R.id.cd_homemenucancel)
        var menuname = view.findViewById<TextView>(R.id.tv_menuname)
        var menuage = view.findViewById<TextView>(R.id.tv_menuage)
        var image = view.findViewById<ImageView>(R.id.iv_menuimage)


        var intent = Intent(requireContext(), Activity2::class.java)

        lifecycleScope.launch {
            val pushresult1 = async {
                context?.let { DataStoreManager.getString(it, "name") }}
            val pushresult2 = async {
                context?.let { DataStoreManager.getString(it, "ageee") }}
            val pushresult3 = async {
                context?.let { DataStoreManager.getInt(it, "picnum") }}
            val pushresult4 = async {
                context?.let { DataStoreManager.getString(it, "addiction") }}

            var name = pushresult1.await()!!
            var age = pushresult2.await()!!
            var picnum = pushresult3.await()!!
            var addictioninfo = pushresult4.await()!!

            when{
                name.length < 1 -> name = "Anonymous"
                age.isEmpty()-> age = "Age not set"
            }
            setimage(image, picnum)
            menuname.text = name
            menuage.text = age

        myinfo.setOnSingleClickListener {

            delay(200)
            dismiss()
            var dialog = Myinfo1()
            (activity as MainActivity).ShowMainpopUp(myinfo,dialog)

        }
        addiction.setOnSingleClickListener {
            addiction.clicking()
            addiction.shortvibrate()
            Toast.makeText( requireContext(), "Working", Toast.LENGTH_SHORT).show()
        }

        abouts.setOnSingleClickListener {
            abouts.clicking()
            abouts.shortvibrate()
            intent.putExtra("ActivityToset", "Aboutspage")
            startActivity(intent)
        }
        cancel.setOnSingleClickListener {
            cancel.shortvibrate()
            dismiss() }

        }
        return view
    }

    private fun chooseimages(picnum: Int){
        var popup = Dialog(requireContext())
        popup.setCancelable(false)
        popup.setContentView(R.layout.profileimages)
        popup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popup.show()

        setimage(popup.image17, picnum)

        popup.image1.setOnClickListener {
            popup.image1.clicking()
            popup.image1.shortvibrate()
            popup.tv_imagenum.setText("1")
            popup.image17.setImageResource(R.drawable.a40)
        }
        popup.image2.setOnClickListener {
            popup.image2.clicking()
            popup.image2.shortvibrate()
            popup.tv_imagenum.setText("2")
            popup.image17.setImageResource(R.drawable.a41)
        }
        popup.image3.setOnClickListener {
            popup.image3.clicking()
            popup.image3.shortvibrate()
            popup.tv_imagenum.setText("3")
            popup.image17.setImageResource(R.drawable.a42)
        }
        popup.image4.setOnClickListener {
            popup.image4.clicking()
            popup.image4.shortvibrate()
            popup.tv_imagenum.setText("4")
            popup.image17.setImageResource(R.drawable.a43)
        }
        popup.image5.setOnClickListener {
            popup.image5.clicking()
            popup.image5.shortvibrate()
            popup.tv_imagenum.setText("5")
            popup.image17.setImageResource(R.drawable.a44)
        }
        popup.image6.setOnClickListener {
            popup.image6.clicking()
            popup.image6.shortvibrate()
            popup.tv_imagenum.setText("6")
            popup.image17.setImageResource(R.drawable.a45)
        }
        popup.image7.setOnClickListener {
            popup.image7.clicking()
            popup.image7.shortvibrate()
            popup.tv_imagenum.setText("7")
            popup.image17.setImageResource(R.drawable.a46)
        }
        popup.image8.setOnClickListener {
            popup.image8.clicking()
            popup.image8.shortvibrate()
            popup.tv_imagenum.setText("8")
            popup.image17.setImageResource(R.drawable.a47)
        }
        popup.image9.setOnClickListener {
            popup.image9.clicking()
            popup.image9.shortvibrate()
            popup.tv_imagenum.setText("9")
            popup.image17.setImageResource(R.drawable.a48)
        }
        popup.image10.setOnClickListener {
            popup.image10.clicking()
            popup.image10.shortvibrate()
            popup.tv_imagenum.setText("10")
            popup.image17.setImageResource(R.drawable.a49)
        }
        popup.image11.setOnClickListener {
            popup.image11.clicking()
            popup.image11.shortvibrate()
            popup.tv_imagenum.setText("11")
            popup.image17.setImageResource(R.drawable.a50)
        }
        popup.image12.setOnClickListener {
            popup.image12.clicking()
            popup.image12.shortvibrate()
            popup.tv_imagenum.setText("12")
            popup.image17.setImageResource(R.drawable.a51)
        }
        popup.image13.setOnClickListener {
            popup.image13.clicking()
            popup.image13.shortvibrate()
            popup.tv_imagenum.setText("13")
            popup.image17.setImageResource(R.drawable.a53)
        }
        popup.image14.setOnClickListener {
            popup.image14.clicking()
            popup.image14.shortvibrate()
            popup.tv_imagenum.setText("14")
            popup.image17.setImageResource(R.drawable.a54)
        }
        popup.image15.setOnClickListener {
            popup.image15.clicking()
            popup.image15.shortvibrate()
            popup.tv_imagenum.setText("15")
            popup.image17.setImageResource(R.drawable.a55)
        }
        popup.image16.setOnClickListener {
            popup.image16.clicking()
            popup.image16.shortvibrate()
            popup.tv_imagenum.setText("16")
            popup.image17.setImageResource(R.drawable.a56)
        }

        popup.btn_saveimage.setOnClickListener {
            var saveimagenum = popup.tv_imagenum.text.toString().toInt()
            lifecycleScope.launch {
                context?.let { DataStoreManager.saveInt(it, "picnum", saveimagenum) }
                dismiss()
            }
        }
    }
    private fun savedata(){
        lifecycleScope.launch {
            context?.let { DataStoreManager.saveInt(it, "WelcomePageCheck", 0) }
        }
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