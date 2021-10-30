import vkDataFormat.*
import vkDataFormat.attachments.Attachmentable
import vkDataFormat.enums.PostType

data class Post(
    val id: Int = 0,
    val ownerId: Int = 0,
    val fromId: Int = 0,
    val createdBy: Int = 0,
    val date: Int = 0,
    val text: String = "",
    val replyOwnerId: Int = 0,
    val replyPostId: Int = 0,
    val friendsOnly: Boolean = false,
    val comments: Comment = Comment(),
    val copyright: Copyright = Copyright(),
    val likes: Likes = Likes(),
    val reports: Reports = Reports(),
    val views: Views = Views(),
    val postType: PostType = PostType.POST,
    val postSource: PostSource = PostSource(),
    val attachment: Attachmentable,
    val geo: Geo = Geo(),
    val singerId: Int = 0,
    val copyHistory: List<Post>? = listOf<Post>(),
    val canPin: Boolean = false,
    val canDelete: Boolean = false,
    val canEdit: Boolean = false,
    val isPinned: Boolean = false,
    val markedAsAds: Boolean = false,
    val isFavorite: Boolean = false,
    val donut: Donut = Donut(),
    val postPhonedId: Int = 0,
)