package com.tonyxlab.echojournal.domain.repository

interface SettingsRepository {
    suspend fun saveMood(moodTitle: String)

    suspend fun getMood(): String

    suspend fun saveTopics(topicListIds: List<Long>)

    suspend fun getTopics(): List<Long>
}