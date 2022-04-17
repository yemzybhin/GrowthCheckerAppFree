package ade.yemi.growthchecker_free.Fragments.Pages

import ade.yemi.growthchecker_free.Activities.MainActivity
import ade.yemi.growthchecker_free.Adapters.Awardadapter
import ade.yemi.growthchecker_free.Data.DataStoreManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker_free.R
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.ArrayList

class AchievementsPage : Fragment() {
    lateinit var recyclerViewAdapter: Awardadapter
    lateinit var viewModel: ChallengeViewModel
    private var ungoing = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_achievements_page, container, false)
        var rv_recyclerrr = view.findViewById<RecyclerView>(R.id.rc_achievements)
        var handlerf = view.findViewById<CardView>(R.id.cardView333)
        handlerf.visibility = View.GONE
        rv_recyclerrr.suppressLayout(true)

        rv_recyclerrr.apply {
            layoutManager = LinearLayoutManager(requireContext())
            recyclerViewAdapter = Awardadapter()
            adapter = recyclerViewAdapter
        }



        viewModel = ViewModelProviders.of(this).get(ChallengeViewModel::class.java)

        lifecycleScope.launch {
            val pushresult = async {
                context?.let { DataStoreManager.getBoolean(it, "challengeungoing") }
            }
            ungoing = pushresult.await()!!
            viewModel.getAllChallengesObservers().observe(requireActivity(), Observer {

                if (ungoing == false){
                    var touse = ArrayList(it)
                    if (touse.isEmpty() == true){
                        handlerf.visibility = View.VISIBLE
                    }else{
                        handlerf.visibility = View.GONE
                    }
                    recyclerViewAdapter.setListData(touse)
                    recyclerViewAdapter.notifyDataSetChanged()

                }else{
                    var touse = ArrayList(it)
                    touse.removeFirst()
                    if (touse.isEmpty() == true){
                        handlerf.visibility = View.VISIBLE
                    }else{
                        handlerf.visibility = View.GONE
                    }
                    recyclerViewAdapter.setListData(touse)
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            })
        }
        (activity as MainActivity).cancelload()
        return view
    }
}