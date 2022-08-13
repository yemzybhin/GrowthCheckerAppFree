package ade.yemi.growthchecker_free.Fragments

import ade.yemi.growthchecker_free.Adapters.AllAdsAdapter
import ade.yemi.growthchecker_free.Data.Preferencestuff
import ade.yemi.growthchecker_free.Fragments.Pages.BaseViewStubFragment
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.generateAds
import ade.yemi.moreapps.Network.RetrofitInterface1
import ade.yemi.moreapps.models.AppContent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AllAds : BaseViewStubFragment(){
    private lateinit var  myAdapter: RecyclerView.Adapter<*>
    private lateinit var manager: RecyclerView.LayoutManager
    override fun onCreateViewAfterViewStubInflated(view: View, savedInstanceState: Bundle?) {
        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        var progressBar = view.findViewById<ProgressBar>(R.id.showloading)
        progressBar.visibility = View.VISIBLE
        manager = LinearLayoutManager(requireContext())
        getdata(recyclerView, progressBar)
    }
    override fun getViewStubLayoutResource(): Int {
        return R.layout.fragment_all_ads
    }
    private fun getdata(recyclerView: RecyclerView, progressBar: ProgressBar){
        var rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface1.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var API = rf.create(RetrofitInterface1::class.java)
        var call =API.post
        call?.enqueue(object : Callback<AppContent?> {
            override fun onResponse(
                    call: Call<AppContent?>,
                    response: Response<AppContent?>
            ) {
                var appContent: AppContent? = response.body() as AppContent
                var adslist = appContent?.ads

                recyclerView.apply {
                    myAdapter = AllAdsAdapter(adslist!!)
                    layoutManager = manager
                    adapter = myAdapter
                }
                if (adslist!!.size != 0){
                    var totalads = adslist!!.size * 2
                    var prefereceStuffs = Preferencestuff(requireContext())
                    prefereceStuffs.setPoint(prefereceStuffs.getPoint() + totalads)
                    Toast.makeText(requireContext(), "Ads loaded, you have gotten 2 Extra Points For Each AD", Toast.LENGTH_LONG).show()
                }
                progressBar.visibility = View.GONE
            }
            override fun onFailure(call: Call<AppContent?>, t: Throwable) {
                var touse = Preferencestuff(requireContext()).getLocalAds()
                if (touse != ""){
                    var appContent = generateAds(requireContext(), touse!!)
                    var adslist = appContent?.ads

                    recyclerView.apply {
                        myAdapter = AllAdsAdapter(adslist!!)
                        layoutManager = manager
                        adapter = myAdapter
                    }
                    if (adslist!!.size != 0){
                        var totalads = adslist!!.size * 1
                        var prefereceStuffs = Preferencestuff(requireContext())
                        prefereceStuffs.setPoint(prefereceStuffs.getPoint() + totalads)
                        Toast.makeText(requireContext(), "Ads loaded, you have gotten 2 Extra Points For Each AD", Toast.LENGTH_LONG).show()
                    }
                    progressBar.visibility = View.GONE
                }
            }
        })
    }
}