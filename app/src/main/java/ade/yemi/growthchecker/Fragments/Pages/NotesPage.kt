package ade.yemi.growthchecker.Fragments.Pages

import ade.yemi.growthchecker.Data.DataStoreManager
import ade.yemi.growthchecker.Fragments.Pages.subpages.Notesfragment1
import ade.yemi.growthchecker.Fragments.Pages.subpages.Notesfragment2
import ade.yemi.growthchecker.Fragments.Pages.subpages.RunningChallenge
import ade.yemi.growthchecker.Fragments.Pages.subpages.checks.analytic1check
import ade.yemi.growthchecker.Fragments.Pages.subpages.norunningchallenge
import ade.yemi.growthchecker.PopUp_Fragments.Popup_AddNote
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.setOnSingleClickListener
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NotesPage : Fragment() {
    private var dialog = Popup_AddNote()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_notes_page, container, false)
        var mynotes = view.findViewById<CardView>(R.id.cd_mynotes)
        var addnew = view.findViewById<CardView>(R.id.cd_addnewnote)

        lifecycleScope.launch {
            val pushresult = async {
                context?.let { DataStoreManager.getBoolean(it, "challengeungoing") }
            }
            var challengeungoin = pushresult.await()!!
            var challengeungoing = challengeungoin
            if (challengeungoing == false){
                replacefragment(R.id.fr_NotesFrag1, analytic1check())
            }else{
                replacefragment(R.id.fr_NotesFrag1, Notesfragment1())
            }
            replacefragment(R.id.fr_NotesFrag2, Notesfragment2())
        }
        addnew.setOnSingleClickListener {
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.mydialog)
            dialog.show(childFragmentManager, "huig")
        }
        return view
    }
    private fun replacefragment(int: Int, fragment:Fragment) {
//        supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, fragment).commit()
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(int, fragment)
        fragmentTransaction.commit()
    }
}