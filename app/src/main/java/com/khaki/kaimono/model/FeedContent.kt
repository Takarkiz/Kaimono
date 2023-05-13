package com.khaki.kaimono.model

class FeedContent {

    val contents get() = _contents.toList()
    private val _contents = mutableListOf<ContentType>()

    fun create(contents: List<ContentType>) {
        _contents.clear()
        _contents.addAll(contents)
    }

    fun updatePollSelectedOption(id: Long, selectedId: Long) {
        val prevValue = getContent<ContentType.Poll>(id)
        modify(
            prevValue.copy(selectedId = selectedId)
        )
    }

    fun completePoll(id: Long) {
        val prevValue = getContent<ContentType.Poll>(id)
        modify(
            prevValue.copy(
                hasPolled = true,
                options = prevValue.options.map {
                    it.copy(count = it.count + if (it.id == prevValue.selectedId) 1 else 0)
                }
            )
        )
    }

    fun modify(content: ContentType) {
        _contents.indexOfFirst { it.id == content.id }.let {
            _contents[it] = content
        }
    }


    @Suppress("UNCHECKED_CAST")
    fun <T: ContentType> getContent(id: Long): T {
        return _contents.first { it.id == id } as T
    }

}