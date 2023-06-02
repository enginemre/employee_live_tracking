package com.hakmar.employeelivetracking.common.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.util.AppConstants
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = AppConstants.DATASTORE_NAME)

class DataStoreImpl @Inject constructor(
    private val context: Context
) : DataStoreRepository {
    override suspend fun stringReadKey(key: String): String? {
        return try {
            val prefKey = stringPreferencesKey(key)
            val preference = context.datastore.data.first()
            preference[prefKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun stringPutKey(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        context.datastore.edit {
            it[prefKey] = value
        }
    }

    override suspend fun intReadKey(key: String): Int? {
        return try {
            val prefKey = intPreferencesKey(key)
            val preference = context.datastore.data.first()
            preference[prefKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun intPutKey(key: String, value: Int) {
        val prefKey = intPreferencesKey(key)
        context.datastore.edit {
            it[prefKey] = value
        }
    }

    override suspend fun doublePutKey(key: String, value: Double) {
        val prefKey = doublePreferencesKey(key)
        context.datastore.edit {
            it[prefKey] = value
        }
    }

    override suspend fun doubleReadKey(key: String): Double? {
        return try {
            val prefKey = doublePreferencesKey(key)
            val preference = context.datastore.data.first()
            preference[prefKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun clearDataStore(key: String) {
        val prefKey = stringPreferencesKey(key)
        context.datastore.edit {
            if (it.contains(prefKey))
                it.remove(prefKey)
        }
    }

}