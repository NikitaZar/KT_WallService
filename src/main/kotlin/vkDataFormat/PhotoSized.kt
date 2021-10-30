package vkDataFormat

import vkDataFormat.enums.PhotoSizeType

data class PhotoSized(
    val url: String = "",
    val width: Int = -1,
    val height: Int = -1,
    val type: PhotoSizeType = PhotoSizeType.P
)