import vkDataFormat.*
import vkDataFormat.attachments.*

data class Comment(
    val id: Int = -1,
    val fromId: Int = -1,
    val date: Int = -1,
    val text: String = "",
    val donut: Donut = Donut(),
    val replyToUser: Int = -1,
    val replyToComment: Int = -1,
    val attachments:Array<Attachmentable> = emptyArray(),
    val parentsStack: Array<Int> = emptyArray() ,
    val thread: ThreadInComment =ThreadInComment()
)