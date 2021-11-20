import chatService.ChatService
import exceptionClasses.ChatMessageNotFoundException
import exceptionClasses.ChatNotFoundException
import exceptionClasses.NoteNotFoundException
import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    @Test
    fun addMessage_first_successful() {
        val chatService = ChatService()
        val userId = 1
        val partnerId = 1
        val messageText = "Привет"

        val messageId = chatService.addMessage(userId, partnerId, messageText)

        assertEquals(messageId, 1)
    }

    @Test
    fun addMessage_second_successful() {
        val chatService = ChatService()
        val userId = 1
        val partnerId = 1
        val messageText = "Привет"

        chatService.addMessage(userId, partnerId, messageText)
        val messageId = chatService.addMessage(userId, partnerId, messageText)

        assertEquals(messageId, 2)
    }

    @Test
    fun editMessage() {
        val chatService = ChatService()
        val userId = 1
        val partnerId = 1
        val messageId = 2
        val messageText = "Привет"
        val newMessageText = "Пока"

        chatService.addMessage(userId, partnerId, messageText)
        chatService.addMessage(userId, partnerId, messageText)
        val chatId = chatService.getChatId(userId, partnerId)
        val rv = chatService.editMessage(chatId, messageId, newMessageText)

        assertTrue(rv)
    }

    @Test
    fun getUnreadChatsCount() {
        val chatService = ChatService()
        val userId1 = 1
        val userId2 = 2
        val userId3 = 3
        val userId4 = 4
        val messageText = "Привет"

        chatService.addMessage(userId1, userId2, messageText)
        chatService.addMessage(userId2, userId3, messageText)
        chatService.addMessage(userId1, userId3, messageText)
        chatService.addMessage(userId2, userId1, messageText)
        chatService.addMessage(userId3, userId1, messageText)
        chatService.addMessage(userId4, userId1, messageText)
        chatService.addMessage(userId4, userId1, messageText)

        val unreadChats1 = chatService.getUnreadChatsCount(userId1)
        val unreadChats2 = chatService.getUnreadChatsCount(userId2)
        val unreadChats3 = chatService.getUnreadChatsCount(userId3)
        val unreadChats4 = chatService.getUnreadChatsCount(userId4)

        assertEquals(unreadChats1, 3)
        assertEquals(unreadChats2, 1)
        assertEquals(unreadChats3, 2)
        assertEquals(unreadChats4, 0)
    }

    @Test
    fun getChats() {
        val chatService = ChatService()
        val userId1 = 1
        val userId2 = 2
        val userId3 = 3
        val userId4 = 4
        val messageText = "Привет"

        chatService.addMessage(userId1, userId2, messageText)
        chatService.addMessage(userId2, userId3, messageText)
        chatService.addMessage(userId1, userId3, messageText)

        val chats1 = chatService.getChats(userId1)
        val chats2 = chatService.getChats(userId2)
        val chats3 = chatService.getChats(userId3)
        val chats4 = chatService.getChats(userId4)

        assertEquals(chats1.size, 2)
        assertEquals(chats2.size, 2)
        assertEquals(chats3.size, 2)
        assertEquals(chats4.size, 0)
    }

    @Test
    fun getMessages() {
        val chatService = ChatService()
        val userId1 = 1
        val userId2 = 2
        val messageText = "Текст"
        var messageId = -1

        for (i in 1..4) {
            messageId = chatService.addMessage(userId1, userId2, messageText + i)
        }
        val chatId = chatService.getChatId(userId1, userId2)
        val messages = chatService.getMessages(chatId, messageId - 2, 2)

        assertEquals(messages.size, 2)
        assertEquals(messages[0].messageText, messageText + 2)
        assertEquals(messages[1].messageText, messageText + 3)
        assertFalse(messages[0].isUnread)
        assertFalse(messages[1].isUnread)
    }

    @Test
    fun deleteMessage() {
        val chatService = ChatService()
        val userId1 = 1
        val userId2 = 2
        val messageText = "Текст"
        var messageId = -1

        for (i in 1..2) {
            messageId = chatService.addMessage(userId1, userId2, messageText + i)
        }

        val chatId = chatService.getChatId(userId1, userId2)

        chatService.deleteMessage(messageId - 1, chatId)

        val messages = chatService.getMessages(chatId, messageId, 2)

        assertEquals(messages.size, 1)
    }

    @Test
    fun deleteMessage_all() {
        val chatService = ChatService()
        val userId1 = 1
        val userId2 = 2
        val messageText = "Текст"
        var messageId = -1

        for (i in 1..2) {
            messageId = chatService.addMessage(userId1, userId2, messageText + i)
        }

        var chatId = chatService.getChatId(userId1, userId2)

        chatService.deleteMessage(messageId - 1, chatId)
        chatService.deleteMessage(messageId, chatId)
        chatId = chatService.getChatId(userId1, userId2)

        assertEquals(chatId, -1)
    }

    @Test
    fun deleteChat() {
        val chatService = ChatService()
        val userId1 = 1
        val userId2 = 2
        val messageText = "Текст"

        for (i in 1..2) {
            chatService.addMessage(userId1, userId2, messageText + i)
        }
        val chatId = chatService.getChatId(userId1, userId2)

        chatService.deleteChat(chatId)

        val chats1 = chatService.getChats(userId1)
        val chats2 = chatService.getChats(userId2)

        assertEquals(chats1.size, 0)
        assertEquals(chats2.size, 0)
    }

    @Test(expected = ChatNotFoundException::class)
    fun getChat_chatNotFound() {
        val chatService = ChatService()

        chatService.getMessages(10, 1, 1)
    }

    @Test(expected = ChatMessageNotFoundException::class)
    fun getMessage_ChatMessageNotFound() {
        val chatService = ChatService()
        val userId1 = 1
        val userId2 = 2
        val messageText = "Текст"

        val messageId = chatService.addMessage(userId1, userId2, messageText)
        val chatId = chatService.getChatId(userId1, userId2)
        chatService.editMessage(chatId, messageId+1, messageText)
    }

}