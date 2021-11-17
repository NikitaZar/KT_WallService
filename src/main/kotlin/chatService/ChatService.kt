package chatService

import exceptionClasses.*

class ChatService {
    private val chats: MutableMap<Int, Chat> = mutableMapOf()

    private fun getChatByUsers(userId1: Int, userId2: Int): Chat {
        return try {
            chats.filter {
                (it.value.userId1 == userId1 && it.value.userId2 == userId2) ||
                        (it.value.userId1 == userId2 && it.value.userId2 == userId1)
            }.values.first()
        } catch (nsee: NoSuchElementException) {
            addChat(userId1, userId2)
        }
    }

    fun getChatId(userId1: Int, userId2: Int): Int {
        return try {
            chats.filter {
                ((it.value.userId1 == userId1 && it.value.userId2 == userId2) ||
                        (it.value.userId1 == userId2 && it.value.userId2 == userId1))
                        && !it.value.isDeleted
            }.keys.first()
        } catch (nsee: NoSuchElementException) {
            -1
        }
    }

    private fun getChat(chatId: Int): Chat {
        try {
            return if (!chats[chatId]!!.isDeleted) chats[chatId]!! else throw ChatNotFoundException(chatId)
        } catch (npe: NullPointerException) {
            throw ChatNotFoundException(chatId)
        }
    }

    private fun getMessage(chat: Chat, messageId: Int): ChatMessage {
        try {
            return chat.messages[messageId]!!
        } catch (npe: NullPointerException) {
            throw ChatMessageNotFoundException(messageId)
        }
    }

    private fun addChat(userId: Int, partnerId: Int): Chat {
        val chatId = if (chats.isEmpty()) 1 else chats.keys.last() + 1
        chats[chatId] = Chat(userId, partnerId)
        return chats[chatId]!!
    }

    fun addMessage(userId: Int, partnerId: Int, messageText: String): Int {
        val chat = getChatByUsers(userId, partnerId)
        val messageId = if (chat.messages.isEmpty()) 1 else chat.messages.keys.last() + 1
        chat.messages[messageId] = ChatMessage(userId, messageText)
        return messageId
    }

    fun editMessage(chatId: Int, messageId: Int, newMessageText: String): Boolean {
        getMessage(getChat(chatId), messageId).messageText = newMessageText
        return true
    }

    fun getUnreadChatsCount(userId: Int) = chats.count { chat ->
        chat.value.messages.values.find { it.isUnread } != null &&
                (chat.value.userId1 == userId || chat.value.userId2 == userId)
    }

    fun getChats(userId: Int) = chats.values.filter { chat ->
        !chat.isDeleted && (chat.userId1 == userId || chat.userId2 == userId)
    }

    fun getMessages(chatId: Int, lastMessageId: Int, messageCount: Int): List<ChatMessage> {
        val chat = getChat(chatId)
        val lastMessageIndex = chat.messages.keys.indexOfLast { it == lastMessageId - 1 }
        val messagesIds = chat.messages.keys.filterIndexed { index, _ ->
            (index >= lastMessageIndex) && (index < lastMessageIndex + messageCount)
        }
        val messagesMap = chat.messages.filter { messagesIds.contains(it.key) }
        var messages = ArrayList(messagesMap.values.filter { !it.isDeleted })
        messages.forEach { it.isUnread = false }

        if (messages.isEmpty()) deleteChat(chatId)

        return messages
    }

    fun deleteMessage(messageId: Int, chatId: Int) {
        getMessage(getChat(chatId), messageId).isDeleted = true
    }

    fun deleteChat(chatId: Int) {
        getChat(chatId).isDeleted = true
    }
}