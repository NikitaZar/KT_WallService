package vkDataFormat

import vkDataFormat.enums.*

data class PostSource(
    val type: PostSourceType,
    val platform: PostSourcePlatform,
    val data: PostSourceData,
    val url: String
)
