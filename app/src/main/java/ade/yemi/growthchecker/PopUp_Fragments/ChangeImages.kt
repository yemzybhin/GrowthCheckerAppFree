package ade.yemi.growthchecker.PopUp_Fragments

import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.Data.DataStoreManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.profileimages
import ade.yemi.growthchecker.Utilities.setimage
import ade.yemi.growthchecker.Utilities.shortvibrate
import android.content.Intent
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_change_images.view.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ChangeImages : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var popup = inflater.inflate(R.layout.fragment_change_images, container, false)


        lifecycleScope.launch {
            val pushresult3 = async {
                context?.let { DataStoreManager.getInt(it, "picnum") }}

            var picnum = pushresult3.await()!!
        setimage(popup.image17, picnum)}

        var profilelist = listOf(
                profileimages(popup.image1, R.drawable.a40),
                profileimages(popup.image2, R.drawable.a41),
                profileimages(popup.image3, R.drawable.a42),
                profileimages(popup.image4, R.drawable.a43),
                profileimages(popup.image5, R.drawable.a44),
                profileimages(popup.image6, R.drawable.a45),
                profileimages(popup.image7, R.drawable.a46),
                profileimages(popup.image8, R.drawable.a47),
                profileimages(popup.image9, R.drawable.a48),
                profileimages(popup.image10, R.drawable.a49),
                profileimages(popup.image11, R.drawable.a50),
                profileimages(popup.image12, R.drawable.a51),
                profileimages(popup.image13, R.drawable.a53),
                profileimages(popup.image14, R.drawable.a54),
                profileimages(popup.image15, R.drawable.a55),
                profileimages(popup.image16, R.drawable.a56),
                profileimages(popup.image18, R.drawable.a1),
                profileimages(popup.image19, R.drawable.a2),
                profileimages(popup.image20, R.drawable.a3),
                profileimages(popup.image21, R.drawable.a4),
                profileimages(popup.image22, R.drawable.a5),
                profileimages(popup.image23, R.drawable.a6),
                profileimages(popup.image24, R.drawable.a7),
                profileimages(popup.image25, R.drawable.a8),
                profileimages(popup.image26, R.drawable.a9),
                profileimages(popup.image27, R.drawable.a10),
                profileimages(popup.image28, R.drawable.a11),
                profileimages(popup.image29, R.drawable.a12),
                profileimages(popup.image30, R.drawable.a13),
                profileimages(popup.image31, R.drawable.a14),
                profileimages(popup.image32, R.drawable.a15),
                profileimages(popup.image33, R.drawable.a16),
                profileimages(popup.image34, R.drawable.a17),
                profileimages(popup.image35, R.drawable.a18),
                profileimages(popup.image36, R.drawable.a19),
                profileimages(popup.image37, R.drawable.a20),
                profileimages(popup.image38, R.drawable.a21),
                profileimages(popup.image39, R.drawable.a22),
                profileimages(popup.image40, R.drawable.a23),
                profileimages(popup.image41, R.drawable.a24),
                profileimages(popup.image42, R.drawable.a25),
                profileimages(popup.image43, R.drawable.a26),
                profileimages(popup.image44, R.drawable.a27),
                profileimages(popup.image45, R.drawable.a28),
                profileimages(popup.image46, R.drawable.a29),
                profileimages(popup.image47, R.drawable.a30),
                profileimages(popup.image48, R.drawable.a31),
                profileimages(popup.image49, R.drawable.a32),
                profileimages(popup.image50, R.drawable.a33),
                profileimages(popup.image51, R.drawable.a34),
                profileimages(popup.image52, R.drawable.a35),
                profileimages(popup.image53, R.drawable.a36),
                profileimages(popup.image54, R.drawable.a37),
                profileimages(popup.image55, R.drawable.a38),
                profileimages(popup.image56, R.drawable.a39), )




        for (i in profilelist){
            i.image.setOnClickListener {
                i.image.clicking()
                i.image.shortvibrate()
                popup.tv_imagenum.setText("${profilelist.indexOf(i) + 1}")
                popup.image17.setImageResource(i.num)
            }
        }

//        popup.image1.setOnClickListener {
//            popup.image1.clicking()
//            popup.image1.shortvibrate()
//            popup.tv_imagenum.setText("1")
//            popup.image17.setImageResource(R.drawable.a40)
//        }
//        popup.image2.setOnClickListener {
//            popup.image2.clicking()
//            popup.image2.shortvibrate()
//            popup.tv_imagenum.setText("2")
//            popup.image17.setImageResource(R.drawable.a41)
//        }
//        popup.image3.setOnClickListener {
//            popup.image3.clicking()
//            popup.image3.shortvibrate()
//            popup.tv_imagenum.setText("3")
//            popup.image17.setImageResource(R.drawable.a42)
//        }
//        popup.image4.setOnClickListener {
//            popup.image4.clicking()
//            popup.image4.shortvibrate()
//            popup.tv_imagenum.setText("4")
//            popup.image17.setImageResource(R.drawable.a43)
//        }
//        popup.image5.setOnClickListener {
//            popup.image5.clicking()
//            popup.image5.shortvibrate()
//            popup.tv_imagenum.setText("5")
//            popup.image17.setImageResource(R.drawable.a44)
//        }
//        popup.image6.setOnClickListener {
//            popup.image6.clicking()
//            popup.image6.shortvibrate()
//            popup.tv_imagenum.setText("6")
//            popup.image17.setImageResource(R.drawable.a45)
//        }
//        popup.image7.setOnClickListener {
//            popup.image7.clicking()
//            popup.image7.shortvibrate()
//            popup.tv_imagenum.setText("7")
//            popup.image17.setImageResource(R.drawable.a46)
//        }
//        popup.image8.setOnClickListener {
//            popup.image8.clicking()
//            popup.image8.shortvibrate()
//            popup.tv_imagenum.setText("8")
//            popup.image17.setImageResource(R.drawable.a47)
//        }
//        popup.image9.setOnClickListener {
//            popup.image9.clicking()
//            popup.image9.shortvibrate()
//            popup.tv_imagenum.setText("9")
//            popup.image17.setImageResource(R.drawable.a48)
//        }
//        popup.image10.setOnClickListener {
//            popup.image10.clicking()
//            popup.image10.shortvibrate()
//            popup.tv_imagenum.setText("10")
//            popup.image17.setImageResource(R.drawable.a49)
//        }
//        popup.image11.setOnClickListener {
//            popup.image11.clicking()
//            popup.image11.shortvibrate()
//            popup.tv_imagenum.setText("11")
//            popup.image17.setImageResource(R.drawable.a50)
//        }
//        popup.image12.setOnClickListener {
//            popup.image12.clicking()
//            popup.image12.shortvibrate()
//            popup.tv_imagenum.setText("12")
//            popup.image17.setImageResource(R.drawable.a51)
//        }
//        popup.image13.setOnClickListener {
//            popup.image13.clicking()
//            popup.image13.shortvibrate()
//            popup.tv_imagenum.setText("13")
//            popup.image17.setImageResource(R.drawable.a53)
//        }
//        popup.image14.setOnClickListener {
//            popup.image14.clicking()
//            popup.image14.shortvibrate()
//            popup.tv_imagenum.setText("14")
//            popup.image17.setImageResource(R.drawable.a54)
//        }
//        popup.image15.setOnClickListener {
//            popup.image15.clicking()
//            popup.image15.shortvibrate()
//            popup.tv_imagenum.setText("15")
//            popup.image17.setImageResource(R.drawable.a55)
//        }
//        popup.image16.setOnClickListener {
//            popup.image16.clicking()
//            popup.image16.shortvibrate()
//            popup.tv_imagenum.setText("16")
//            popup.image17.setImageResource(R.drawable.a56)
//        }

        popup.btn_saveimage.setOnClickListener {
            popup.btn_saveimage.clicking()
            popup.btn_saveimage.shortvibrate()

            if (popup.tv_imagenum.text == "0"){
                Toast.makeText(requireContext(), "Choose An Image", Toast.LENGTH_SHORT).show()
            }else{
                var saveimagenum = popup.tv_imagenum.text.toString().toInt()
                lifecycleScope.launch {
                    context?.let { DataStoreManager.saveInt(it, "picnum", saveimagenum) }
                    dismiss()
                }
            }

            startActivity(Intent(requireContext(), MainActivity::class.java))

        }


        popup.cd_myinfopopupcancel.setOnClickListener {
            popup.cd_myinfopopupcancel.clicking()
            popup.shortvibrate()
            dismiss()
        }




        return popup
    }
}