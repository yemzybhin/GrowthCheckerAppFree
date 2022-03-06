package ade.yemi.growthchecker.Fragments.Pages

import ade.yemi.growthchecker.Data.DataStoreManager
import ade.yemi.growthchecker.Fragments.Pages.subpages.Analyticsfragment1
import ade.yemi.growthchecker.Fragments.Pages.subpages.Analyticsfragment2
import ade.yemi.growthchecker.Fragments.Pages.subpages.checks.analytic1check
import ade.yemi.growthchecker.Fragments.Pages.subpages.checks.analytic2check
import ade.yemi.growthchecker.Helpers.Graph.GenerateFloat
import ade.yemi.growthchecker.Helpers.Graph.cumulativelast
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.thecomments
import ade.yemi.growthchecker.Utilities.zoom_in
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import ade.yemi.roomdatabseapp.Graph.CustomMarker
import androidx.fragment.app.FragmentContainer
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AnalyticsPage : Fragment() {
    lateinit var viewModel: ChallengeViewModel
    private var ungoing = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_analytics_page, container, false)

        viewModel = ViewModelProviders.of(this).get(ChallengeViewModel::class.java)
        viewModel.getAllChallengesObservers().observe(requireActivity(), Observer {
        lifecycleScope.launch {
            val pushresult1 = async {
                context?.let { DataStoreManager.getBoolean(it, "challengeungoing") }
            }
            ungoing = pushresult1.await()!!
            if (ungoing == true){
                replacefragment1(Analyticsfragment1())
            }else{
                replacefragment1(analytic1check())
            }
            when{
            it.size == 0 -> replacefragment2(analytic2check())
            it.size == 1 && ungoing == true -> replacefragment2(analytic2check())
            it.size == 1 && ungoing == false -> replacefragment2(Analyticsfragment2())
            else -> replacefragment2(Analyticsfragment2())
        }
        }
        })
        return view
    }
    private fun replacefragment1(fragment:Fragment) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fr_analytics1, fragment)
        fragmentTransaction.commit()
    }
    private fun replacefragment2(fragment:Fragment) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fr_analytics2, fragment)
        fragmentTransaction.commit()
    }
}