package ade.yemi.growthchecker_free.PopUp_Fragments

import ade.yemi.growthchecker_free.Data.NoteData.Note
import ade.yemi.growthchecker_free.Data.NoteData.NoteViewmodel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ade.yemi.growthchecker_free.R
import ade.yemi.growthchecker_free.Utilities.clicking
import ade.yemi.growthchecker_free.Utilities.setOnSingleClickListener
import ade.yemi.growthchecker_free.Utilities.shortvibrate
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_popup__add_note.*
import kotlinx.android.synthetic.main.fragment_popup__add_note.view.*

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
        var notety = view.findViewById<TextView>(R.id.tv_notetype)
        var noteuse = view.findViewById<EditText>(R.id.et_noteuse)
        var showing = view.findViewById<TextView>(R.id.tv_noteusestuff)

        viewmodel = ViewModelProviders.of(this).get(NoteViewmodel::class.java)

        val notetype = arguments?.getString("notetype")
        if (notetype.equals("Edit")){
         noteId = arguments?.getInt("id", -1)!!
            val notetitle = arguments?.getString("title")
            val notecontent = arguments?.getString("content")
            val notetype1 = arguments?.getString("notetype1")
            val noteuse1 = arguments?.getInt("noteuse")
            noteuse.visibility = View.VISIBLE
            view.tv_noteusestuff.visibility = View.VISIBLE
            addnote.text = "Update Note"
            notety.text = notetype1
            noteuse.setText("$noteuse1")
            titletext.setText(notetitle)
            contenttext.setText(notecontent)
        }else{
            addnote.text = "Add Note"
            titletext.setText("")
            contenttext.setText("")
            notety.text = "Encouragement"
            noteuse.setText("0")
            showing.text = "How useful has the note been?"
            noteuse.visibility = View.GONE
            showing.visibility = View.GONE
        }

        view.type1.setOnClickListener {
            type1.shortvibrate()
            type1.clicking()
            notety.text = "Encouragement"
        }
        view.type2.setOnClickListener {
            type2.shortvibrate()
            type2.clicking()
            notety.text = "Congratulatory"
        }
        view.type3.setOnClickListener {
            type3.shortvibrate()
            type3.clicking()
            notety.text = "Achievement"
        }
        view.type4.setOnClickListener {
            type4.shortvibrate()
            type4.clicking()
            notety.text = "Warning"
        }
        view.type5.setOnClickListener {
            type5.shortvibrate()
            type5.clicking()
            notety.text = "Relapse"
        }

        addnote.setOnSingleClickListener {
            addnote.clicking()
            addnote.shortvibrate()
                val title = titletext.text.toString()
                val content = contenttext.text.toString()
                val use = noteuse.text.toString()

            if(notetype.equals("Edit")){
                if (title.isNotEmpty() && content.isNotEmpty() && use.isNotEmpty()){
                    val toadd = Note(noteId, title, content, notety.text.toString(), use.toInt())
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
                     val toshow = Note(0, title, content, notety.text.toString(), 0)
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
            cancelbutton.clicking()
            cancelbutton.shortvibrate()
            dismiss()
        }



        return view
    }

}