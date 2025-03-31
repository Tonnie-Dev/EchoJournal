package com.tonyxlab.echojournal.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tonyxlab.echojournal.data.repository.`<no name provided>`.moodKey
import com.tonyxlab.echojournal.data.repository.`<no name provided>`.topicsKey
import com.tonyxlab.echojournal.domain.json.JsonSerializer
import com.tonyxlab.echojournal.domain.model.Mood
import com.tonyxlab.echojournal.domain.repository.SettingsRepository
import com.tonyxlab.echojournal.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(Constants.DATA_PREFS)

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val jsonSerializer: JsonSerializer
) : SettingsRepository {

    private val dataStore = context.dataStore

    private val topicsIdSerializer: KSerializer<List<Long>> = ListSerializer(Long.serializer())


    override suspend fun saveMood(moodTitle: String) {
        dataStore.edit { prefs -> prefs[moodKey] = moodTitle }
    }

    override suspend fun getMood(): String {
        val prefs = dataStore.data.first()
        return prefs[moodKey] ?: Mood.Undefined.name
    }

    override suspend fun saveTopics(topicListIds: List<Long>) {
        val topicsListAsJson = jsonSerializer.toJson(topicsIdSerializer, topicListIds)
        dataStore.edit { prefs ->

            prefs[topicsKey] = topicsListAsJson
        }
    }

    override suspend fun getTopics(): List<Long> {

        val prefs = dataStore.data.first()

        val topicsJson = prefs[moodKey] ?: "[]"
        return jsonSerializer.fromJson(topicsIdSerializer, topicsJson)

    }

    private companion object {

        val moodKey = stringPreferencesKey(Constants.MOODS_PREF_KEY)
        val topicsKey = stringPreferencesKey(Constants.TOPICS_PREF_KEY)

    }
}