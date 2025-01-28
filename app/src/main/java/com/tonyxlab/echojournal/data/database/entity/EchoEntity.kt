package com.tonyxlab.echojournal.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tonyxlab.echojournal.domain.model.Mood

@Entity(tableName = "echo_table")
data class EchoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "length")
    val length: Int,
    @ColumnInfo(name = "mood")
    val mood: Mood,
    @ColumnInfo(name = "topics")
    val topics: List<String>,
    @ColumnInfo(name = "uri")
    val uri: String
)
