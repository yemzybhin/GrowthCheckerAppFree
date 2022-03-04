package ade.yemi.growthchecker.Fragments.Pages

import ade.yemi.growthchecker.Activities.Activity2
import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.Data.DataStoreManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.delay
import ade.yemi.growthchecker.Utilities.shortvibrate
import ade.yemi.roomdatabseapp.Data.Challenge
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class Startchallenge : Fragment() {
    private var ungoing = false
    private lateinit var challengeViewModel: ChallengeViewModel
    private var challengetype = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       var view = inflater.inflate(R.layout.fragment_startchallenge, container, false)
        var start = view.findViewById<CardView>(R.id.cd_challengestartstart)


        lifecycleScope.launch {
            val pushresult = async {
                context?.let { DataStoreManager.getString(it, "challengeviewChallenge") }
            }
            challengetype = pushresult.await()!!
            start.setOnClickListener {
                start.clicking()
                start.shortvibrate()
                confirmpopup(challengetype)
            }
        }

        return view
    }

    private fun challengedetails(string: String): Challenge{
        var challengetype = ""
        var days = ""
        var points = mutableListOf<String>()

        when(string){
            "challenge14" -> {challengetype = "14DaysChallenge"
                              days = "14"}
            "challenge30" -> {challengetype = "30DaysChallenge"
                days = "30"}
            "challenge60" -> {challengetype = "60DaysChallenge"
                days = "60"}
            "challenge100" -> {challengetype = "100DaysChallenge"
                days = "100"}
            "challenge200" -> {challengetype = "200DaysChallenge"
                days = "200"}
        }
        var challenge = Challenge(0, challengetype, days, points)
        return challenge
    }
    private fun confirmpopup( string: String){
        var popup = Dialog(requireContext())
        popup.setCancelable(true)
        popup.setContentView(R.layout.popup_startchallenge_confirmation)
        popup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popup.show()

        var cancel = popup.findViewById<CardView>(R.id.cd_StartChallengeConfirmationCancel)
        var start = popup.findViewById<Button>(R.id.btn_startchallengeconfirmed)
        cancel.setOnClickListener {
            cancel.clicking()
            cancel.shortvibrate()
            delay(2000)
            popup.dismiss()
        }

        start.setOnClickListener {
            var challenge = challengedetails(string)
            challengeViewModel = ViewModelProvider(this).get(ChallengeViewModel::class.java)

            try {
                challengeViewModel.insertChallengeInfo(challenge)
                Toast.makeText(requireContext(), "$string Challenge Started Successfully", Toast.LENGTH_LONG).show()
                ungoing = true
                savedata()
                popup.dismiss()
                startActivity(Intent(requireContext(), MainActivity :: class.java))
            }catch(e:Exception){
                ungoing = false
                savedata()
                popup.dismiss()
                Toast.makeText(requireContext(), "Could Not Start Challenge", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), MainActivity :: class.java))
            }
        }
    }

        private fun savedata(){
        lifecycleScope.launch {
            context?.let { DataStoreManager.saveBoolean(it, "challengeungoing", ungoing) }


        }
    }
}
//challengeungoing