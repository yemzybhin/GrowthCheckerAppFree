package ade.yemi.growthchecker_free.Data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name = "All_Data")
object DataStoreManager {
    suspend fun saveInt(context: Context, key: String, value :Int) = withContext(Dispatchers.IO){
        val wrappedkey = intPreferencesKey(key)
        context.settingDataStore.edit {
            it[wrappedkey] = value
        }
    }
    suspend fun saveString(context: Context, key: String, value :String) = withContext(Dispatchers.IO){
        val wrappedkey = stringPreferencesKey(key)
        context.settingDataStore.edit {
            it[wrappedkey] = value
        }
    }
    suspend fun saveBoolean(context: Context, key: String, value :Boolean) = withContext(Dispatchers.IO){
        val wrappedkey = booleanPreferencesKey(key)
        context.settingDataStore.edit {
            it[wrappedkey] = value
        }
    }
    suspend fun getInt(context: Context, key:String, value:Int = 0):Int = withContext(Dispatchers.IO){
        val wrappedkey = intPreferencesKey(key)
        val valueflow: Flow<Int> = context.settingDataStore.data.map {
            it[wrappedkey] ?: value
        }
        return@withContext valueflow.first()
    }
    suspend fun getBoolean(context: Context, key:String, value:Boolean = false):Boolean = withContext(Dispatchers.IO){
        val wrappedkey = booleanPreferencesKey(key)
        val valueflow: Flow<Boolean> = context.settingDataStore.data.map {
            it[wrappedkey] ?: value
        }
        return@withContext valueflow.first()
    }

    suspend fun getString(context: Context, key:String, value:String = ""):String = withContext(Dispatchers.IO){
        val wrappedkey = stringPreferencesKey(key)
        val valueflow: Flow<String> = context.settingDataStore.data.map {
            it[wrappedkey] ?: value
        }
        return@withContext valueflow.first()
    }

}