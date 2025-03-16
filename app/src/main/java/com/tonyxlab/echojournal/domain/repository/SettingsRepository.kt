package com.tonyxlab.echojournal.domain.repository

import com.tonyxlab.echojournal.domain.model.Mood
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun saveMood(moodTitle: String)

    fun getMood(): Flow<String>

    suspend fun saveTopics(topicListIds: List<Long>)

    fun getTopics(): Flow<List<Long>>
}