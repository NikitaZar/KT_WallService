package chatService

data class ChatMessage(
    val authorId: Int,
    var messageText: String = "",
    var isUnread: Boolean = true,
    var isDeleted: Boolean = false
)