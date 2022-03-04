package ade.yemi.roomdatabseapp.Data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ChallengeViewModel(app: Application): AndroidViewModel(app) {
    lateinit var allChallenges : MutableLiveData<MutableList<Challenge>>

    init{
        allChallenges = MutableLiveData()
        getAllChallenges()
    }

    fun getAllChallengesObservers(): MutableLiveData<MutableList<Challenge>> {
        return allChallenges
    }

    fun getAllChallenges() {
        val userDao = ChallengeDb.getAppDatabase((getApplication()))?.challengeDao()
        val list = userDao?.getAllChallengeInfo()

        allChallenges.postValue(list)
    }

    fun insertChallengeInfo(entity: Challenge){
        val challengeDao = ChallengeDb.getAppDatabase(getApplication())?.challengeDao()
        challengeDao?.insertChallenge(entity)
        getAllChallenges()
    }

    fun updateChallenge (entity: Challenge){
        val challengeDao = ChallengeDb.getAppDatabase(getApplication())?.challengeDao()
        challengeDao?.updateChallenge(entity)
        getAllChallenges()
    }

}