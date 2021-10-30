package vkDataFormat.attachments

data class GraffitiAttachment(
    override val typeAttachment: String = "Graffiti",
    override val id: Int = -1,
    val ownerId: Int = -1,
    val photo130: String = "",
    val photo604: String = "",
) : Attachmentable