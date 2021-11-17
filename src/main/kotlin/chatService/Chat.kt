package chatService

data class Chat(
    val userId1:Int,
    val userId2:Int,
    var messages: MutableMap<Int, ChatMessage> = mutableMapOf(),
    var lastMessageId: Int = -1,
    var isDeleted: Boolean = false
    )
