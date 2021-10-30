package vkDataFormat.attachments

data class AlbumAttachment(
    override val typeAttachment: String = "Album",
    override val id: Int,
    val thumb: PhotoAttachment,
    val ownerId: Int = -1,
    val title: String = "",
    val description: String = "",
    val created: Int = -1,
    val updated: Int = -1,
    val size: Int = -1,
) : Attachmentable
