package com.tonyxlab.echojournal.utils


fun renderSecondsToStrings(seconds: Int): String {

    return if (seconds == 0) {

        "00:00"
    } else {

        val minutes = seconds / 60
        val secs = seconds % 60

        minutes.formatSecs()
                .plus(":")
                .plus(secs.formatSecs())
    }

}

fun Int.formatSecs(): String {

    return if (this in 0..9) "0$this" else this.toString()

}