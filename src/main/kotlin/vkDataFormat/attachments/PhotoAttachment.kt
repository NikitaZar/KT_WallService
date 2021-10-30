package vkDataFormat.attachments

import vkDataFormat.PhotoSized

data class PhotoAttachment(
    override val typeAttachment: String = "Photo",
    override val id: Int = -1,
    val albumId: Int = -1,
    val ownerId: Int = -1,
    val useId: Int = -1,
    val text: String = "",
    val date: Int = 0,
    val sizes: ArrayList<PhotoSized> = arrayListOf<PhotoSized>(),
    val width: Int = 0,
    val height: Int = 0,
) : Attachmentable
