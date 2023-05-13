package com.khaki.kaimono.model

data class ComponentModel(
    val title: String,
    val content: List<ContentType>,
    val menu: String,
    val hooter: String,
)

sealed class ContentType {

    abstract val id: Long

    data class Text(
        override val id: Long,
        val text: String,
    ) : ContentType()

    data class Image(
        override val id: Long,
        val url: String,
    ) : ContentType()

    data class Video(
        override val id: Long,
        val url: String,
    ) : ContentType()

    data class Audio(
        override val id: Long,
        val url: String,
    ) : ContentType()

    data class Poll(
        override val id: Long,
        val question: String,
        val options: List<PollOption>,
        val selectedId: Long?,
        val hasPolled: Boolean,
    ) : ContentType() {

        data class PollOption(
            val id: Long,
            val text: String,
            val count: Int,
        )
    }
}
