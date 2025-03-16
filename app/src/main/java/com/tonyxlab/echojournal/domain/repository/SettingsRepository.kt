package com.tonyxlab.echojournal.domain.repository

import com.tonyxlab.echojournal.domain.model.Mood

interface SettingsRepository {

    fun saveMood(key: String, moodTitle: String)
    fun getMood(key: String, defaultMoodValue: String = Mood.Undefined.name): String

    fun saveTopics(key: String, topicListId: List<Long>)
    fun getTopics(key: String): List<Long>
}