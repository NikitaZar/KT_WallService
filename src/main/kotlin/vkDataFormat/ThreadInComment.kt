package vkDataFormat

data class ThreadInComment(
    val count: Int = -1,
    val items: Any = Any(),
    val canPost: Boolean = false,
    val showReplyButton: Boolean = false,
    val groupsCanPost: Boolean = false
)