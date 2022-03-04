package ade.yemi.growthchecker.Fragments.Pages

import ade.yemi.growthchecker.Data.DataStoreManager
import ade.yemi.growthchecker.Fragments.Pages.subpages.norunningchallenge
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Homepage : Fragment() {
    private var challengeungoing = false
    private var challengeungoin = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_homepage, container, false)
        initdata()
        if (challengeungoing == false){
            replacefragment(norunningchallenge())
        }else{
            replacefragment(norunningchallenge())
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
    private fun initdata(){
        lifecycleScope.launch {
            val pushresult = async {
                context?.let { DataStoreManager.getBoolean(it, "challengeungoing") }
            }
            challengeungoin = pushresult.await()!!
            challengeungoing = challengeungoin
        }
    }
}