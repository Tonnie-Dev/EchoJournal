package com.tonyxlab.echojournal.domain.repository

import com.tonyxlab.echojournal.domain.model.Mood
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun saveMood(moodTitle: String)

    suspend fun getMood(): String

    suspend fun saveTopics(topicListIds: List<Long>)

    suspend fun getTopics(): List<Long>
}