package vkDataFormat

import vkDataFormat.enums.*

data class PostSource(
    val type: PostSourceType = PostSourceType.VK,
    val platform: PostSourcePlatform = PostSourcePlatform.WPHONE,
    val data: PostSourceData = PostSourceData.POLL,
    val url: String = ""
)
