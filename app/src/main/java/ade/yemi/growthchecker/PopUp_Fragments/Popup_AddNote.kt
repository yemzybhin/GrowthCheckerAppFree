package ade.yemi.growthchecker.PopUp_Fragments

import ade.yemi.growthchecker.Data.NoteData.Note
import ade.yemi.growthchecker.Data.NoteData.NoteViewmodel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker.R
import ade.yemi.growthchecker.Utilities.setOnSingleClickListener
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class Popup_AddNote : DialogFragment() {
//    lateinit var noteTitle: EditText
//    lateinit var noteContent: EditText
    lateinit var viewmodel: NoteViewmodel
    var noteId = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_popup__add_note, container, false)
        var titletext = view.findViewById<EditText>(R.id.et_notetitle)
        var contenttext = view.findViewById<TextView>(R.id.et_notecontent)
        var addnote = view.findViewById<Button>(R.id.btn_addnote)
        var cancelbutton = view.findViewById<CardView>(R.id.cd_dismissaddnote)

        viewmodel = ViewModelProviders.of(this).get(NoteViewmodel::class.java)

        val notetype = arguments?.getString("notetype")
        if (notetype.equals("Edit")){
         noteId = arguments?.getInt("id", -1)!!
            val notetitle = arguments?.getString("title")
            val notecontent = arguments?.getString("content")
            addnote.text = "Update Note"
            titletext.setText(notetitle)
            contenttext.setText(notecontent)
        }else{
            addnote.text = "Add Note"
            titletext.setText("")
            contenttext.setText("")
        }

        addnote.setOnSingleClickListener {
                val title = titletext.text.toString()
                val content = contenttext.text.toString()

            if(notetype.equals("Edit")){
                if (title.isNotEmpty() && content.isNotEmpty()){
                    val toadd = Note(noteId, title, content)
                    viewmodel.updatenote(toadd)
                    Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
                    titletext.setText("")
                    contenttext.setText("")
                    dismiss()
                }
                else{
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                }

            }else{
                if (title.isNotEmpty() && content.isNotEmpty()){
                     val toshow = Note(0, title, content)
                    viewmodel.addnote(toshow)
                    titletext.setText("")
                    contenttext.setText("")
                    Toast.makeText(requireContext(), "Note Added", Toast.LENGTH_SHORT).show()
                    dismiss()
                }else{
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }

        }
        cancelbutton.setOnSingleClickListener {
            dismiss()
        }



        return view
    }

}