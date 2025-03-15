package com.tonyxlab.echojournal.domain.model

import com.tonyxlab.echojournal.utils.Constants

data class Topic(
    val id: Long = Constants.INITIAL_DATABASE_ID,
    val name: String
)
