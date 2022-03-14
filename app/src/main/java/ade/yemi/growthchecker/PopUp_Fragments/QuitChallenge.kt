package ade.yemi.growthchecker.PopUp_Fragments

import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.AlarmReceiver
import ade.yemi.growthchecker.Data.DataStoreManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.levelimage
import ade.yemi.growthchecker.Utilities.setOnSingleClickListener
import ade.yemi.growthchecker.Utilities.shortvibrate
import ade.yemi.roomdatabseapp.Data.Challenge
import ade.yemi.roomdatabseapp.Data.ChallengeViewModel
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_homepage.view.*
import kotlinx.android.synthetic.main.fragment_quit_challenge.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class QuitChallenge : DialogFragment() {
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    lateinit var viewModel: ChallengeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_quit_challenge, container, false)
        var cancel = view.findViewById<CardView>(R.id.cd_dismissqauit)
        var quit = view.findViewById<Button>(R.id.btn_quitchallenge)
        viewModel = ViewModelProviders.of(this).get(ChallengeViewModel::class.java)
        viewModel.getAllChallengesObservers().observe(requireActivity(), Observer {
            var challengetodel = it.first()

                quit.setOnSingleClickListener {
                    quit.shortvibrate()
                    lifecycleScope.launch {
                        context?.let { DataStoreManager.saveBoolean(it, "challengeungoing", false) }}
                        viewModel.deleteChallengeInfo(challengetodel)
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        cancelalarm()
                        dismiss()
            }
        })



        cancel.setOnClickListener {
            dismiss()
        }


        return view
    }
    private fun cancelalarm(){
        alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
        alarmManager.cancel(pendingIntent)
    }

}