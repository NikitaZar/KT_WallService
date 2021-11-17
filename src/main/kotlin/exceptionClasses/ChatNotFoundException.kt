package exceptionClasses

class ChatNotFoundException(chatId: Int) : Exception("chatId = $chatId") {
}