package chatService

import exceptionClasses.*

class ChatService {

    companion object {
        const val START_ID = 1
        const val NOT_FOUND_ID = -1
    }

    private val chats: MutableMap<Int, Chat> = mutableMapOf()

    private fun isChatOfUsers(chat: Chat, userId1: Int, userId2: Int) =
        (chat.userId1 == userId1 && chat.userId2 == userId2) || (chat.userId1 == userId2 && chat.userId2 == userId1)

    private fun isUserJoinChar(chat: Chat, userId: Int) = (chat.userId1 == userId || chat.userId2 == userId)

    private fun getChatByUsers(userId1: Int, userId2: Int) =
        chats.filter { isChatOfUsers(it.value, userId1, userId2) && !it.value.isDeleted }.values
            .let { if (it.isNotEmpty()) it.first() else addChat(userId1, userId2) }

    fun getChatId(userId1: Int, userId2: Int) =
        chats.filter { isChatOfUsers(it.value, userId1, userId2) && !it.value.isDeleted }.keys
            .let { if (it.isNotEmpty()) it.first() else NOT_FOUND_ID }

    private fun getChat(chatId: Int) = chats[chatId] ?: throw ChatNotFoundException(chatId)

    private fun getMessage(chat: Chat, messageId: Int) =
        chat.messages[messageId] ?: throw ChatMessageNotFoundException(messageId)

    private fun addChat(userId: Int, partnerId: Int) =
        Chat(userId, partnerId).let { chat ->
            chats[if (chats.isEmpty()) START_ID else chats.keys.last() + 1] = chat; chat
        }

    fun addMessage(userId: Int, partnerId: Int, messageText: String) =
        getChatByUsers(userId, partnerId).let {
            val messageId = if (it.messages.isEmpty()) START_ID else it.messages.keys.last() + 1
            it.messages[messageId] = ChatMessage(userId, messageText)
            messageId
        }

    fun editMessage(chatId: Int, messageId: Int, newMessageText: String) =
        getMessage(getChat(chatId), messageId).let { it.messageText = newMessageText; true }

    fun getUnreadChatsCount(userId: Int) = chats.count { chat ->
        chat.value.messages.values.find { it.isUnread } != null && isUserJoinChar(chat.value, userId) //TODO
    }

    fun getChats(userId: Int) = chats.values.filter { chat -> !chat.isDeleted && isUserJoinChar(chat, userId) }

    fun getMessages(chatId: Int, lastMessageId: Int, messageCount: Int): List<ChatMessage> { //TODO
        val chat = getChat(chatId)
        val lastMessageIndex = chat.messages.keys.indexOfLast { it == lastMessageId - 1 }
        val messagesIds = chat.messages.keys.filterIndexed { index, _ ->
            (index >= lastMessageIndex) && (index < lastMessageIndex + messageCount)
        }
        val messagesMap = chat.messages.filter { messagesIds.contains(it.key) }
        val messages = ArrayList(messagesMap.values.filter { !it.isDeleted })
        messages.forEach { it.isUnread = false }

        if (messages.isEmpty()) deleteChat(chatId)

        return messages
    }

    fun deleteMessage(messageId: Int, chatId: Int) = getMessage(getChat(chatId), messageId).let { it.isDeleted = true }

    fun deleteChat(chatId: Int) = getChat(chatId).let { it.isDeleted = true }
}