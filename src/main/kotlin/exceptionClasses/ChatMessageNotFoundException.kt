package exceptionClasses

class ChatMessageNotFoundException(messageId: Int) : Exception("messageId = $messageId") {
}