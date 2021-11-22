package chatService

data class ChatMessage(
    val messageId: Int,
    val authorId: Int,
    var messageText: String = "",
    var isUnread: Boolean = true,
    var isDeleted: Boolean = false
)