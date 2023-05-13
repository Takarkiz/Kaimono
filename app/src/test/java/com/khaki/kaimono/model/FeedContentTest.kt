package com.khaki.kaimono.model

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class FeedContentTest {

    private var feedContent = FeedContent()

    @BeforeEach
    fun setUp() {
        feedContent.create(
            listOf(
                ContentType.Text(1, "Hello"),
                ContentType.Image(2, "https://www.google.com"),
                ContentType.Video(3, "https://www.google.com"),
                ContentType.Audio(4, "https://www.google.com"),
                ContentType.Poll(
                    5,
                    "What is your favorite color?",
                    listOf(
                        ContentType.Poll.PollOption(1, "Red", 2),
                        ContentType.Poll.PollOption(2, "Blue", 3),
                        ContentType.Poll.PollOption(3, "Green", 5),
                    ),
                    null,
                    false
                ),
            )
        )
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun test_updateSelectedOption() {
        feedContent.updatePollSelectedOption(5, 2)
        Assertions.assertEquals(
            2,
            (feedContent.contents[4] as ContentType.Poll).selectedId
        )
    }

    @Test
    fun test_completePoll() {
        feedContent.completePoll(5)
        Assertions.assertTrue(
            (feedContent.contents[4] as ContentType.Poll).hasPolled
        )
    }

    @Test
    fun test_modify() {
        feedContent.modify(
            ContentType.Text(1, "Hello World")
        )
        Assertions.assertTrue(
            feedContent.contents[0] is ContentType.Text
        )
        Assertions.assertEquals(
            "Hello World",
            (feedContent.contents[0] as ContentType.Text).text
        )
    }

    @Test
    fun test_getContent() {
        val content = feedContent.getContent<ContentType.Text>(1)
        Assertions.assertEquals(
            "Hello",
            content.text
        )
    }
}