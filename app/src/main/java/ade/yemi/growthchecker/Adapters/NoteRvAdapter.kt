package ade.yemi.growthchecker.Adapters

import ade.yemi.growthchecker.Data.NoteData.Note
import ade.yemi.growthchecker.Fragments.Pages.subpages.Notesfragment2
import ade.yemi.growthchecker.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NoteRvAdapter(
    val context: Notesfragment2,
    val noteClickInterface: Notesfragment2,
    val noteClickDeleteInterface: NoteClickDeleteInterface
): RecyclerView.Adapter<NoteRvAdapter.Viewholder>(){
    private val allNotes = ArrayList<Note>()
    inner class Viewholder(itemView: View):RecyclerView.ViewHolder(itemView){
        val noteTv = itemView.findViewById<TextView>(R.id.tv_notetitle)
        val notecontent = itemView.findViewById<TextView>(R.id.tv_notecontent)
        val deletenote = itemView.findViewById<CardView>(R.id.cd_deletenote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.singlenote, parent, false)
        return Viewholder((itemView))
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.noteTv.setText(allNotes.get(position).Notetitle)
        holder.notecontent.setText(allNotes.get(position).Notecontent)

        holder.deletenote.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
        }
        holder.itemView.setOnClickListener{
            noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }
    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}

interface NoteClickDeleteInterface{
    fun onDeleteIconClick(note: Note)
}
interface NoteClickInterface{
        fun onNoteClick(note: Note)
}