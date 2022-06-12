package ade.yemi.growthchecker_free.Fragments.Pages.subpages

import ade.yemi.growthchecker_free.Adapters.NoteClickDeleteInterface
import ade.yemi.growthchecker_free.Adapters.NoteClickInterface
import ade.yemi.growthchecker_free.Adapters.NoteRvAdapter
import ade.yemi.growthchecker_free.Data.NoteData.Note
import ade.yemi.growthchecker_free.Data.NoteData.NoteViewmodel
import ade.yemi.growthchecker_free.Fragments.Pages.BaseViewStubFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.NoteCommunicator
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Notesfragment2 : BaseViewStubFragment(), NoteClickDeleteInterface, NoteClickInterface {


    private lateinit var communicator: NoteCommunicator
    lateinit var viewmodal: NoteViewmodel
    override fun onCreateViewAfterViewStubInflated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        var noteRV = view.findViewById<RecyclerView>(R.id.rv_notesrecycler)
        var nonotes = view.findViewById<ImageView>(R.id.nonotescheck)


        noteRV.layoutManager = LinearLayoutManager(requireContext())


        val noteRvAdapter = NoteRvAdapter(this, this, this)
        noteRV.adapter = noteRvAdapter
        viewmodal = ViewModelProviders.of(this).get(NoteViewmodel::class.java)
        viewmodal.allNotes.observe( requireActivity(), Observer {
                list ->
            list?.let {
                if (it.size != 0){
                    nonotes.visibility = View.GONE

                }else{
                    nonotes.visibility = View.VISIBLE
                }

                noteRvAdapter.updateList(it)
            }
        })

    }

    override fun getViewStubLayoutResource(): Int {
        return R.layout.fragment_notesfragment2
    }

    override fun onDeleteIconClick(note: Note) {
        viewmodal.deletenote(note)
        Toast.makeText(requireContext(), "Note has been deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onNoteClick(note: Note){
        communicator = activity as NoteCommunicator
        communicator.passnotedetails(note.id, note.Notetitle, note.Notecontent,note.Notetype ,note.Noteuse )
    }

}