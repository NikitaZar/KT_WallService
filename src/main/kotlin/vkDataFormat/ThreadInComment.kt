package vkDataFormat

data class ThreadInComment(
    val count: Int,
    val items: Any,
    val canPost: Boolean,
    val showReplyButton: Boolean,
    val groupsCanPost: Boolean
)