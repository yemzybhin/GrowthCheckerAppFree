package ade.yemi.roomdatabseapp.Data

import androidx.room.*



@Dao
interface ChallengeDao {
    @Query("SELECT * FROM ChallengeInfo ORDER BY id DESC")
    fun getAllChallengeInfo(): MutableList<Challenge>?
    @Insert
    fun insertChallenge(challenge: Challenge?)
    @Update
    fun updateChallenge(challenge: Challenge?)
    @Delete
    fun deleteChallenge(challenge: Challenge?)
}