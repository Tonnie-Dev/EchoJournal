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


fun generateLoremIpsum(wordCount: Int): String {
    val loremIpsumWords = listOf(
            "Lorem",
            "ipsum",
            "dolor",
            "sit",
            "amet",
            "consectetur",
            "adipiscing",
            "elit",
            "sed",
            "do",
            "eiusmod",
            "tempor",
            "incididunt",
            "ut",
            "labore",
            "et",
            "dolore",
            "magna",
            "aliqua",
            "Ut",
            "enim",
            "ad",
            "minim",
            "veniam",
            "quis",
            "nostrud",
            "exercitation",
            "ullamco",
            "laboris",
            "nisi",
            "ut",
            "aliquip",
            "ex",
            "ea",
            "commodo",
            "consequat",
            "Duis",
            "aute",
            "irure",
            "dolor",
            "in",
            "reprehenderit",
            "in",
            "voluptate",
            "velit",
            "esse",
            "cillum",
            "dolore",
            "eu",
            "fugiat",
            "nulla",
            "pariatur",
            "Excepteur",
            "sint",
            "occaecat",
            "cupidatat",
            "non",
            "proident",
            "sunt",
            "in",
            "culpa",
            "qui",
            "officia",
            "deserunt",
            "mollit",
            "anim",
            "id",
            "est",
            "laborum"
    )

    return (1..wordCount).joinToString(" ") {
        loremIpsumWords.random()
                .replaceFirstChar { it.titlecase() }
    }
}
