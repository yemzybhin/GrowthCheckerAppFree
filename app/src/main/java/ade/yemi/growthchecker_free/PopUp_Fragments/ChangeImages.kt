package ade.yemi.growthchecker_free.PopUp_Fragments

import ade.yemi.growthchecker_free.Activities.MainActivity
import ade.yemi.growthchecker_free.Data.DataStoreManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.clicking
import ade.yemi.growthchecker_free.Utilities.profileimages
import ade.yemi.growthchecker_free.Utilities.setimage
import ade.yemi.growthchecker_free.Utilities.shortvibrate
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
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
            popup.tv_imagenum.text = picnum.toString()
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
        popup.btn_saveimage.setOnClickListener {
            popup.btn_saveimage.clicking()
            popup.btn_saveimage.shortvibrate()

            if (popup.tv_imagenum.text == "0"){
                //Toast.makeText(requireContext(), "Choose An Image", Toast.LENGTH_SHORT).show()
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

        var mAdView : AdView
        MobileAds.initialize(requireContext()) {}
        mAdView = popup.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        (activity as MainActivity).cancelload()

        return popup
    }
}