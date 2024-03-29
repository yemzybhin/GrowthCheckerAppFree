package ade.yemi.growthchecker_free.Fragments.Pages

import ade.yemi.growthchecker_free.Activities.MainActivity
import ade.yemi.growthchecker_free.Fragments.Pages.subpages.Notesfragment2
import ade.yemi.growthchecker_free.PopUp_Fragments.Popup_AddNote
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.clicking
import ade.yemi.growthchecker_free.Utilities.setOnSingleClickListener
import ade.yemi.growthchecker_free.Utilities.shortvibrate
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment


class NotesPage : BaseViewStubFragment() {
    private var dialog = Popup_AddNote()
    override fun onCreateViewAfterViewStubInflated(
        view : View,
        savedInstanceState: Bundle?
    ) {
        var addnew = view.findViewById<CardView>(R.id.cd_addnewnote)
        replacefragment(R.id.fr_NotesFrag2, Notesfragment2())

        addnew.setOnSingleClickListener {
            addnew.clicking()
            addnew.shortvibrate()
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.mydialog)
            dialog.show(childFragmentManager, "huig")
        }
        (activity as MainActivity).cancelload()
    }

    override fun getViewStubLayoutResource(): Int {
        return R.layout.fragment_notes_page
    }

    private fun replacefragment(int: Int, fragment:Fragment) {
//        supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, fragment).commit()
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(int, fragment)
        fragmentTransaction.commit()
    }
}