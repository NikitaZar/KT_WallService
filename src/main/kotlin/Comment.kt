import vkDataFormat.*
import vkDataFormat.attachments.*

data class Comment(
    val id: Int,
    val fromId: Int,
    val date: Int,
    val text: String,
    val donut: Donut,
    val replyToUser: Int,
    val replyToComment: Int,
    val attachments: ArrayList<Attachmentable>,
    val parentsStack: ArrayList<Int>,
    val thread: ThreadInComment
)