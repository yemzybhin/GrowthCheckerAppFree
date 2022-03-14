package ade.yemi.growthchecker.PopUp_Fragments

import ade.yemi.growthchecker.Data.DataStoreManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.shortvibrate
import android.text.TextUtils
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_behavioural_concern.view.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BehaviouralConcern : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_behavioural_concern, container, false)
        lifecycleScope.launch {
            val pushresult1 = async {
                context?.let { DataStoreManager.getString(it, "addiction") } }
            var addiction = pushresult1.await()
            view.et_addictionword.setText(addiction)
        }


        view.btn_saveaddiction.setOnClickListener {
            view.btn_saveaddiction.clicking()
            view.btn_saveaddiction.shortvibrate()
            if (TextUtils.isEmpty(view.et_addictionword.toString())){
                Toast.makeText(requireContext(), "No addiction specified", Toast.LENGTH_SHORT).show()
            }else{
                lifecycleScope.launch {
                    context?.let { DataStoreManager.saveString(it, "addiction", view.et_addictionword.text.toString()) }
                }
                dismiss()
            }

        }

        view.cd_myaddictionpopupcancel22.setOnClickListener {
            view.cd_myaddictionpopupcancel22.clicking()
            view.cd_myaddictionpopupcancel22.shortvibrate()
            dismiss()
        }

        return view
    }
}