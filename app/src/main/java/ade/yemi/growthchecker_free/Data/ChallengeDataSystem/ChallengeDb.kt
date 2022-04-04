package ade.yemi.roomdatabseapp.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Challenge::class], version = 1)
@TypeConverters(PointConverter::class)
abstract class ChallengeDb: RoomDatabase() {

    abstract fun challengeDao():ChallengeDao?

    companion object {
        private var INSTANCE: ChallengeDb?= null

        fun getAppDatabase(context: Context): ChallengeDb? {

            if(INSTANCE == null ) {

                INSTANCE = Room.databaseBuilder<ChallengeDb>(
                    context.applicationContext, ChallengeDb::class.java, "AppDBB"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
}