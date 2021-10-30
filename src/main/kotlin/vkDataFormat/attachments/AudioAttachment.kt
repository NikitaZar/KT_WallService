package vkDataFormat.attachments

data class AudioAttachment(
    override val typeAttachment: String = "Audio",
    override val id: Int = -1,
    override val ownerId: Int = -1,
    val artist: String = "",
    val title: String = "",
    val duration: Int = -1,
    val url: String = "",
    val lyriksId: Int = -1,
    val albumId: Int = -1,
    val genreId: Int = -1,
    val date: Int = -1,
    val noSearch: Boolean = false,
    val isHQ: Boolean = false
) : Attachmentable {
}
