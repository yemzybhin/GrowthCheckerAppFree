package ade.yemi.growthchecker.Fragments.Pages

import ade.yemi.growthchecker.Fragments.Pages.subpages.Notesfragment2
import ade.yemi.growthchecker.PopUp_Fragments.Popup_AddNote
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.clicking
import ade.yemi.growthchecker.Utilities.setOnSingleClickListener
import ade.yemi.growthchecker.Utilities.shortvibrate
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment


class NotesPage : Fragment() {
    private var dialog = Popup_AddNote()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_notes_page, container, false)
        var addnew = view.findViewById<CardView>(R.id.cd_addnewnote)
        replacefragment(R.id.fr_NotesFrag2, Notesfragment2())

        addnew.setOnSingleClickListener {
            addnew.clicking()
            addnew.shortvibrate()
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