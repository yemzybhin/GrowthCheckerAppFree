package ade.yemi.roomdatabseapp.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity(tableName = "ChallengeInfo")
data class Challenge(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "Challenge_Type") val ChallengeType: String,
    @ColumnInfo(name = "Days") val Days: String,
    @ColumnInfo(name = "Points") val Point: MutableList<String>
)

class PointConverter{
    @TypeConverter
    fun fromInt(value: String): MutableList<String>{
        val listType =object :TypeToken<MutableList<String>>(){}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromMutablelist(list : MutableList<String?>): String{
        return Gson().toJson(list)
    }
}

