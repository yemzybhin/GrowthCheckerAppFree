package ade.yemi.growthchecker_free.Fragments.Pages

import ade.yemi.growthchecker_free.Adapters.MoreAppsAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.clicking
import ade.yemi.growthchecker_free.Utilities.shortvibrate
import ade.yemi.moreapps.Network.RetrofitInterface
import ade.yemi.moreapps.models.AllAppDetails
import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AboutsPage : Fragment() {
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var  myAdapter: RecyclerView.Adapter<*>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_abouts_page, container, false)
        var recyclerView = view.findViewById<RecyclerView>(R.id.rv_moreapps)
        var retry = view.findViewById<TextView>(R.id.tv_retry)
        var loader = view.findViewById<LottieAnimationView>(R.id.lt_loader)
        var rateus = view.findViewById<Button>(R.id.btn_rateus)
        loader.visibility = View.VISIBLE
        retry.visibility = View.GONE
        manager = LinearLayoutManager(requireContext())

        getdata(loader, retry, recyclerView)

        retry.setOnClickListener {
            retry.clicking()
            retry.shortvibrate()
            getdata(loader, retry, recyclerView)
        }

        rateus.setOnClickListener {
            rateus.clicking()
            rateus.shortvibrate()
            val uriUri = Uri.parse("https://play.google.com/store/apps/details?id=ade.yemi.growthchecker")
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUri)
            startActivity(launchBrowser)
        }
        return view
    }
    private fun getdata(loader:LottieAnimationView, retry: TextView, recyclerView: RecyclerView){
        var rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var API = rf.create(RetrofitInterface::class.java)
        var call =API.post


        call?.enqueue(object : Callback<AllAppDetails?> {
            override fun onResponse(
                call: Call<AllAppDetails?>,
                response: Response<AllAppDetails?>
            ) {
                var appDetails: AllAppDetails? = response.body() as AllAppDetails
                var applist = appDetails?.apps
                recyclerView.apply {
                    myAdapter = MoreAppsAdapter(applist!!)
                    layoutManager = manager
                    adapter = myAdapter
                }
                retry.visibility = View.GONE
                loader.visibility = View.GONE
            }
            override fun onFailure(call: Call<AllAppDetails?>, t: Throwable) {
                Toast.makeText(requireContext(), "Could not load Apps. Check connection", Toast.LENGTH_SHORT).show()
                retry.visibility = View.VISIBLE
                loader.visibility = View.GONE
            }
        })
    }
}