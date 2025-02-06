package com.tonyxlab.echojournal.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

import com.tonyxlab.echojournal.domain.model.Mood

@Entity(tableName = "echo_table")
data class EchoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "length")
    val length: Int,
    @ColumnInfo(name = "mood")
    val mood: Mood,

    @ColumnInfo(name = "uri")
    val uri: String
)

@Entity(
    tableName = "topic_table",
    foreignKeys = [
        ForeignKey(
            entity = EchoEntity::class,
            parentColumns = ["id"],
            childColumns = ["echo_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["echo_id"])]
)
data class TopicEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "topic_id")
    val topicId: Long = 0,
    @ColumnInfo(name = "echo_id")
    val echoId: String,
    @ColumnInfo(name = "topic")
    val topic: String
)

data class EchoWithTopics(

    @Embedded
    val echoEntity: EchoEntity,
    @Relation(parentColumn = "id", entityColumn = "echo_id")
    val topicEntities: List<TopicEntity>
)
