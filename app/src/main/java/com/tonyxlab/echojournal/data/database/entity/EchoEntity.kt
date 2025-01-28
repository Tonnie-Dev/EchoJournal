package com.tonyxlab.echojournal.data.database.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.tonyxlab.echojournal.domain.model.Mood

data class EchoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description:String,
    @ColumnInfo(name = "timestamp")
    val timestamp:Long,
    @ColumnInfo(name = "length")
    val length: Int,
    @ColumnInfo(name = "mood")
    val mood: Mood,
    @ColumnInfo(name = "topics")
    val topics:List<String>,
    @ColumnInfo(name = "uri")
    val uri: Uri
)
