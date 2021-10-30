package vkDataFormat.SealedAttachment

sealed class SealedAttachment(val typeAttachment: String, open val id: Int)
data class AppAttachmment(
    override val id: Int,
    val name: String,
    val photo130: String,
    val photo604: String,
) : SealedAttachment("app", id)

data class PageAttachmment(
    override val id: Int,
    val groupId: Int,
    val title: String
) : SealedAttachment("page", id)
