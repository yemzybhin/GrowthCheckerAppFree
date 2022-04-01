package ade.yemi.growthchecker.Fragments.Pages

import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.Data.DataStoreManager
import ade.yemi.growthchecker.Fragments.Pages.subpages.RunningChallenge
import ade.yemi.growthchecker.Fragments.Pages.subpages.norunningchallenge
import ade.yemi.growthchecker.Helpers.Graph.GenerateFloat
import ade.yemi.growthchecker.Helpers.Graph.cumulativelast
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.levelimage
import ade.yemi.growthchecker.Utilities.setimage
import ade.yemi.growthchecker.Utilities.thecomments
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import ade.yemi.roomdatabseapp.Graph.CustomMarker
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_homepage.*
import kotlinx.android.synthetic.main.fragment_homepage.view.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Homepage : Fragment() {
    lateinit var viewModel: ChallengeViewModel
    private var picnum = 0
    private var username = ""
    private var challengeungoin = false
    private var challengeungoing = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_homepage, container, false)
        var image = view.findViewById<ImageView>(R.id.iv_homeimage)
        lifecycleScope.launch {
            val pushresult = async {
                context?.let { DataStoreManager.getBoolean(it, "challengeungoing") }
            }
            val pushresult1 = async {
                context?.let { DataStoreManager.getInt(it, "picnum") }
            }
            val pushresult2 = async {
                context?.let { DataStoreManager.getString(it, "name") }
            }

            challengeungoin = pushresult.await()!!
            challengeungoing = challengeungoin
            if (challengeungoing == false){
                replacefragment(norunningchallenge())
            }else{
            replacefragment(RunningChallenge())
        }
            picnum = pushresult1.await()!!
            username = pushresult2.await()!!
            setimage(image, picnum)
            view.tv_homename.text = "Hi, $username"
            viewModel = ViewModelProviders.of(this@Homepage).get(ChallengeViewModel::class.java)
            viewModel.getAllChallengesObservers().observe(requireActivity(), Observer {
                var totalpoints = 0
                if (it.size != 0){
                    for (i in it){
                        for (j in i.Point){
                            totalpoints = totalpoints + j.toInt()
                        }
                    }
                    view.tv_scorecount.text = "$totalpoints"
                    levelimage(totalpoints,view.iv_homelevelimage,  view.tv_homelevelcomment)
                }
                else{
                    view.iv_homelevelimage.setImageResource(R.drawable.l1)
                    view.tv_homelevelcomment.text = "No comment yet!"
                }
            })
    }

        return view
    }

    private fun replacefragment(fragment:Fragment) {
//        supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, fragment).commit()
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fr_ungoingchallenge, fragment)
        fragmentTransaction.commit()
    }
//    private fun savedata(){
//        lifecycleScope.launch {
//            context?.let { DataStoreManager.saveBoolean(it, "challengeviewChallenge", challengeungoing) }
//        }
//    }
//    private fun initdata(){
//        lifecycleScope.launch {
//            val pushresult = async {
//                context?.let { DataStoreManager.getBoolean(it, "challengeungoing") }
//            }
//            challengeungoin = pushresult.await()!!
//            challengeungoing = challengeungoin
//        }
//    }

}