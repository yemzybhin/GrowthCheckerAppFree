package ade.yemi.growthchecker_free.PopUp_Fragments

import ade.yemi.growthchecker_free.Activities.Activity2
import ade.yemi.growthchecker_free.Activities.MainActivity
import ade.yemi.growthchecker_free.Data.DataStoreManager
import ade.yemi.growthchecker_free.Data.Preferencestuff
import ade.yemi.growthchecker_free.Fragments.Pages.AnalyticsPage
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.*
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_homepage.view.*
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_menu.view.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Menu : DialogFragment(){
    lateinit var viewModel: ChallengeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_menu, container, false)
        var myinfo = view.findViewById<CardView>(R.id.cd_MenuMyInfo)
        var addiction = view.findViewById<CardView>(R.id.cd_menuAddiction)
        var abouts = view.findViewById<CardView>(R.id.cd_menuSettings)
        var cancel = view.findViewById<CardView>(R.id.cd_homemenucancel)
        val menulayout = view.findViewById<LinearLayout>(R.id.menulayout)
        var menuname = view.findViewById<TextView>(R.id.tv_menuname)
        var menuage = view.findViewById<TextView>(R.id.tv_menuage)
        var image = view.findViewById<ImageView>(R.id.iv_menuimage)
        var ungoingtext = view.findViewById<TextView>(R.id.menu_challengeungoing)

        var progresstext = view.findViewById<TextView>(R.id.menu_percent)
        var progressview = view.findViewById<View>(R.id.menu_percentshow)
        var baseview = view.findViewById<View>(R.id.baseprogressview)

        var animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in)
        animation.setInterpolator(AccelerateDecelerateInterpolator())
//        animation.interpolator(AccelerateDecelerateInterpolator())
        menulayout.startAnimation(animation)


        var intent = Intent(requireContext(), Activity2::class.java)

        lifecycleScope.launch {

            val pushresult4 = async {
                context?.let { DataStoreManager.getBoolean(it, "challengeungoing") }}

            var preferencestuff = Preferencestuff(requireContext())
            var name = preferencestuff.getUserAttributes("userName")
            var age = preferencestuff.getUserAttributes("userAge")
            var picnum = preferencestuff.getUserAttributes("displayImage")
            var ungoing = pushresult4.await()


            when{
                name!!.length < 1 -> name = "Anonymous"
            }
            setimage(image, picnum!!.toInt())
            menuname.text = name
            menuage.text = age.toString()

            viewModel = ViewModelProviders.of(this@Menu).get(ChallengeViewModel::class.java)
            viewModel.getAllChallengesObservers().observe(requireActivity(), Observer {

                if (ungoing == true){
                    ungoingtext.text = it.first().ChallengeType

                    var toset1 = it.first().Point.size
                    var toset2 = 100/(it.first().Days.toDouble())
                    var toset = toset1 * toset2
                    progresstext.text = "${toset.toInt()}%"

                    val mParams = progressview.layoutParams as FrameLayout.LayoutParams
                    mParams.apply {
                        width = (toset * 2.1).toInt()
                        height *= 1
                    }
                    progressview.layoutParams = mParams
                }else{
                    ungoingtext.text = "No Ungoing Challenge"
                    progressview.visibility = View.GONE
                    baseprogressview.visibility = View.GONE
                    progresstext.text = "No progress yet"
                }
                var totalpoints = 0
                if (it.size != 0){
                    for (i in it){
                        for (j in i.Point){
                            totalpoints = totalpoints + j.toInt()
                        }
                    }
                    levelimage(totalpoints,view.menu_levelimage,  view.menu_comment)
                }
                else{
                    view.menu_levelimage.setImageResource(R.drawable.l1)
                    view.menu_comment.text = "No comment yet!"
                }
            })
        myinfo.setOnSingleClickListener {
            myinfo.clicking()
            myinfo.shortvibrate()
            dismiss()
            (activity as MainActivity).shoowinfo()
        }
        addiction.setOnSingleClickListener {
            addiction.shortvibrate()
                dismiss()
                (activity as MainActivity).addiction()
        }
        abouts.setOnSingleClickListener {
            abouts.clicking()
            abouts.shortvibrate()
            (activity as MainActivity).loading()
            Handler().postDelayed({
                intent.putExtra("ActivityToset", "Aboutspage")
                startActivity(intent)
                dismiss()
            }, 0)
        }
        cancel.setOnSingleClickListener {
            cancel.shortvibrate()
            dismiss()
        }
        }
        return view
    }
}