package chatService

import exceptionClasses.*

class ChatService {

    companion object {
        const val START_ID = 1
        const val NOT_FOUND_ID = -1
    }

    private val chats: MutableMap<Int, Chat> = mutableMapOf()

    private inline fun isChatOfUsers(chat: Chat, userId1: Int, userId2: Int) =
        (chat.userId1 == userId1 && chat.userId2 == userId2) || (chat.userId1 == userId2 && chat.userId2 == userId1)

    private inline fun isUserJoinChar(chat: Chat, userId: Int) = (chat.userId1 == userId || chat.userId2 == userId)

    private fun getChatByUsers(userId1: Int, userId2: Int) =
        chats.filter { isChatOfUsers(it.value, userId1, userId2) && !it.value.isDeleted }.values
            .let { it.firstOrNull() ?: addChat(userId1, userId2) }

    fun getChatId(userId1: Int, userId2: Int) =
        chats.filter { isChatOfUsers(it.value, userId1, userId2) && !it.value.isDeleted }.keys
            .let { it.firstOrNull() ?: NOT_FOUND_ID }

    private fun getChat(chatId: Int) = chats[chatId] ?: throw ChatNotFoundException(chatId)

    private fun getMessage(chat: Chat, messageId: Int) =
        chat.messages[messageId] ?: throw ChatMessageNotFoundException(messageId)

    private fun addChat(userId: Int, partnerId: Int) =
        Chat(userId, partnerId).let {
            val chatId = chats.keys.lastOrNull()?.plus(1) ?: START_ID
            chats[chatId] = it
            it
        }

    fun addMessage(userId: Int, partnerId: Int, messageText: String) =
        getChatByUsers(userId, partnerId).let {
            val messageId = it.messages.keys.lastOrNull()?.plus(1) ?: START_ID
            it.messages[messageId] = ChatMessage(userId, messageText)
            messageId
        }

    fun editMessage(chatId: Int, messageId: Int, newMessageText: String) =
        getMessage(getChat(chatId), messageId).let { it.messageText = newMessageText; true }

    fun getUnreadChatsCount(userId: Int) =
        chats.values
            .filter { !it.isDeleted }
            .filter { isUserJoinChar(it, userId) }
            .count { chat ->
                chat.messages.values.any { message -> message.isUnread && message.authorId != userId }
            }

    fun getChats(userId: Int) = chats.values.filter { chat -> !chat.isDeleted && isUserJoinChar(chat, userId) }

    fun getMessages(chatId: Int, lastMessageId: Int, messageCount: Int) =
        getChat(chatId).messages
            .filter { it.key >= lastMessageId }
            .values.asSequence()
            .filter { !it.isDeleted }
            .take(messageCount)
            .toList()
            .onEach { it.isUnread = false }


    fun deleteMessage(messageId: Int, chatId: Int) {
        getMessage(getChat(chatId), messageId).isDeleted = true
        getChat(chatId).messages.values.find { !it.isDeleted } ?: deleteChat(chatId)
    }

    fun deleteChat(chatId: Int) = getChat(chatId).let { it.isDeleted = true }
}