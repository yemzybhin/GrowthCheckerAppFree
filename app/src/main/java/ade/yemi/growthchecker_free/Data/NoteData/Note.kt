package ade.yemi.growthchecker_free.Data.NoteData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notestable")
data class Note(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "Note_Title") val Notetitle: String,
    @ColumnInfo(name = "Note_Content") val Notecontent: String,
    @ColumnInfo(name = "Note_Type") val Notetype: String,
    @ColumnInfo(name = "Note_Use") val Noteuse: Int,
)

