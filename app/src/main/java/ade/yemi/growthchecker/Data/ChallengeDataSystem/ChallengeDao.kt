package ade.yemi.roomdatabseapp.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM ChallengeInfo ORDER BY id DESC")
    fun getAllChallengeInfo(): MutableList<Challenge>?
    @Insert
    fun insertChallenge(challenge: Challenge?)
    @Update
    fun updateChallenge(challenge: Challenge?)
}