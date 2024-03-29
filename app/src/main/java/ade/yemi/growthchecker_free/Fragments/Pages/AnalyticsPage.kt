package ade.yemi.growthchecker_free.Fragments.Pages

import ade.yemi.growthchecker_free.Activities.MainActivity
import ade.yemi.growthchecker_free.Data.DataStoreManager
import ade.yemi.growthchecker_free.Fragments.Pages.subpages.Analyticsfragment1
import ade.yemi.growthchecker_free.Fragments.Pages.subpages.Analyticsfragment2
import ade.yemi.growthchecker_free.Fragments.Pages.subpages.checks.analytic1check
import ade.yemi.growthchecker_free.Fragments.Pages.subpages.checks.analytic2check
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker_free.R
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AnalyticsPage : BaseViewStubFragment() {
    lateinit var viewModel: ChallengeViewModel
    private var ungoing = false

    override fun onCreateViewAfterViewStubInflated(
        inflatedView: View,
        savedInstanceState: Bundle?
    ) {
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

        (activity as MainActivity).cancelload()
    }

    override fun getViewStubLayoutResource(): Int {
        return R.layout.fragment_analytics_page
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