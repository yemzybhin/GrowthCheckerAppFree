package ade.yemi.growthchecker_free.Fragments.Pages.subpages
import ade.yemi.growthchecker_free.Adapters.Listadapter
import ade.yemi.growthchecker_free.Data.DataStoreManager
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.ArrayList

class Analyticsfragment2 : Fragment() {
    lateinit var recyclerViewAdapter: Listadapter
    lateinit var viewModel: ChallengeViewModel
    private var ungoing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_analyticsfragment2, container, false)
        var rv_recyclerr = view.findViewById<RecyclerView>(R.id.rv_recycler)

//        rv_recyclerr.isNestedScrollingEnabled = false

        rv_recyclerr.apply {
            layoutManager = LinearLayoutManager(requireContext())
            recyclerViewAdapter = Listadapter()
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
                recyclerViewAdapter.setListData(touse)
                recyclerViewAdapter.notifyDataSetChanged()
            }else{
                var touse = ArrayList(it)
                touse.removeFirst()
                recyclerViewAdapter.setListData(touse)
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })
        }
        return view
    }

}