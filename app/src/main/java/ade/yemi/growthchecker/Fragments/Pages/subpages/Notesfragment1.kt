package ade.yemi.growthchecker.Fragments.Pages.subpages

import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.Data.DataStoreManager
import ade.yemi.growthchecker.Helpers.Graph.GenerateFloat
import ade.yemi.growthchecker.Helpers.Graph.cumulativelast
import ade.yemi.growthchecker.PopUp_Fragments.DailyAssessment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.NoteCommunicator
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.shortvibrate
import ade.yemi.growthchecker.Utilities.thecomments
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import ade.yemi.roomdatabseapp.Graph.CustomMarker
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Notesfragment1 : Fragment() {

    lateinit var viewModel: ChallengeViewModel
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_notesfragment1, container, false)
      var challengetype = view.findViewById<TextView>(R.id.tv_noteschallengehead)
      var adder1text = view.findViewById<TextView>(R.id.tv_notes1adder1)
      var adder2text = view.findViewById<TextView>(R.id.tv_notesadder2)
      var adder3text = view.findViewById<TextView>(R.id.tv_notesadder3)


      viewModel = ViewModelProviders.of(this).get(ChallengeViewModel::class.java)
      viewModel.getAllChallengesObservers().observe(requireActivity(), Observer {
          lifecycleScope.launch {
              val pushresult1 = async {
                  context?.let { DataStoreManager.getString(it, "Ungoingchallengeadder1") }
              }
              val pushresult2 = async {
                  context?.let { DataStoreManager.getString(it, "Ungoingchallengeadder2") }
              }
              val pushresult3 = async {
                  context?.let { DataStoreManager.getString(it, "Ungoingchallengeadder3") }
              }
              var adder1 = pushresult1.await()
              var adder2 = pushresult2.await()
              var adder3 = pushresult3.await()

              adder1text.text = adder1
              adder2text.text = adder2
              adder3text.text = adder3
          }
          var challengetext =  it.first().ChallengeType
          challengetype.text = challengetext
      })


      return view
    }
}