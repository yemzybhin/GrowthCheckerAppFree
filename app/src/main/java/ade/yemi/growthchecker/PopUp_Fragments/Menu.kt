package ade.yemi.growthchecker.PopUp_Fragments

import ade.yemi.growthchecker.Activities.Activity2
import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.Data.DataStoreManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.*
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import android.content.Intent
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
        var menuname = view.findViewById<TextView>(R.id.tv_menuname)
        var menuage = view.findViewById<TextView>(R.id.tv_menuage)
        var image = view.findViewById<ImageView>(R.id.iv_menuimage)
        var ungoingtext = view.findViewById<TextView>(R.id.menu_challengeungoing)

        var progresstext = view.findViewById<TextView>(R.id.menu_percent)
        var progressview = view.findViewById<View>(R.id.menu_percentshow)
        var baseview = view.findViewById<View>(R.id.baseprogressview)


        var intent = Intent(requireContext(), Activity2::class.java)

        lifecycleScope.launch {
            val pushresult1 = async {
                context?.let { DataStoreManager.getString(it, "name") }}
            val pushresult2 = async {
                context?.let { DataStoreManager.getString(it, "ageee") }}
            val pushresult3 = async {
                context?.let { DataStoreManager.getInt(it, "picnum") }}
            val pushresult4 = async {
                context?.let { DataStoreManager.getBoolean(it, "challengeungoing") }}


            var name = pushresult1.await()!!
            var age = pushresult2.await()!!
            var picnum = pushresult3.await()!!
            var ungoing = pushresult4.await()


            when{
                name.length < 1 -> name = "Anonymous"
                age.isEmpty()-> age = "Age not set"
            }
            setimage(image, picnum)
            menuname.text = name
            menuage.text = age

            viewModel = ViewModelProviders.of(this@Menu).get(ChallengeViewModel::class.java)
            viewModel.getAllChallengesObservers().observe(requireActivity(), Observer {

                if (ungoing == true){
                    ungoingtext.text = it.first().ChallengeType

                    var toset1 = it.first().Point.size + 1
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

            delay(200)
            dismiss()
            var dialog = Myinfo1()
            (activity as MainActivity).ShowMainpopUp(myinfo,dialog)

        }
        addiction.setOnSingleClickListener {
            var dialog = BehaviouralConcern()
            dismiss()
            (activity as MainActivity).ShowMainpopUp(addiction,dialog)
        }

        abouts.setOnSingleClickListener {
            abouts.clicking()
            abouts.shortvibrate()
            intent.putExtra("ActivityToset", "Aboutspage")
            startActivity(intent)
            dismiss()
        }
        cancel.setOnSingleClickListener {
            cancel.shortvibrate()
            dismiss() }

        }
        return view
    }

    private fun savedata(){
        lifecycleScope.launch {
            context?.let { DataStoreManager.saveInt(it, "WelcomePageCheck", 0) }
        }
    }

}