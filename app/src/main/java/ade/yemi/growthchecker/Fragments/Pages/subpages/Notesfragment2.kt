package ade.yemi.growthchecker.Fragments.Pages.subpages

import ade.yemi.growthchecker.Adapters.NoteClickDeleteInterface
import ade.yemi.growthchecker.Adapters.NoteClickInterface
import ade.yemi.growthchecker.Adapters.NoteRvAdapter
import ade.yemi.growthchecker.Data.NoteData.Note
import ade.yemi.growthchecker.Data.NoteData.NoteViewmodel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.NoteCommunicator
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Notesfragment2 : Fragment(), NoteClickDeleteInterface, NoteClickInterface {


    private lateinit var communicator: NoteCommunicator
    lateinit var viewmodal: NoteViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_notesfragment2, container, false)
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

        return view
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