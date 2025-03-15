package com.tonyxlab.echojournal.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tonyxlab.echojournal.domain.model.Mood
import kotlinx.datetime.Instant

@Entity(tableName = "echoes_table")
data class EchoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "mood")
    val mood: Mood,
    @ColumnInfo(name = "audio_file_path")
    val audioFilePath: String,
    @ColumnInfo(name = "audio_duration")
    val audioDuration: Int,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "topics")
    val topics: List<String>,
    @ColumnInfo(name = "creation_time_stamp")
    val creationTimestamp: Instant

)