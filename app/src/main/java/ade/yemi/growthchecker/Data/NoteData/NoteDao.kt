package ade.yemi.growthchecker.Data.NoteData

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * from notestable ORDER BY id ASC")
    fun getAllNote(): LiveData<List<Note>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note?)
    @Update
    suspend fun updateNote(note: Note?)
    @Delete
    suspend fun deleteNote(note: Note?)
}
