package ade.yemi.growthchecker_free.Data.NoteData

import androidx.lifecycle.LiveData

class NoteRepository(private val notesDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = notesDao.getAllNote()

    suspend fun insert(note: Note){
        notesDao.insertNote(note)
    }
    suspend fun delete(note: Note){
        notesDao.deleteNote(note)
    }
    suspend fun update(note: Note){
        notesDao.updateNote(note)
    }
}