import exceptionClasses.*
import vkDataFormat.Report

class WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var reports = emptyArray<Report>()
    private val reasonsText = arrayOf(
        "спам",
        "детская порнография",
        "экстремизм",
        "насилие",
        "пропаганда наркотиков",
        "материал для взрослых",
        "оскорбление",
        "",
        "призывы к суициду"
    )

    fun add(post: Post): Post {
        val newPost: Post = when (posts.isEmpty()) {
            true -> post.copy(id = 1)
            false -> post.copy(id = posts.last().id + 1)
        }

        posts = posts.plus(newPost)

        return newPost
    }

    fun update(post: Post): Boolean {
        for ((i, p) in posts.withIndex()) {
            if (p.id == post.id) {
                val newPost = post.copy(ownerId = p.ownerId, date = p.date)
                posts[i] = newPost
                return true
            }
        }
        return false
    }

    fun createComment(comment: Comment) {
        if (posts.none { it.id == comment.id }) {
            throw PostNotFoundException()
        } else {
            comments = comments.plus(comment)
        }
    }

    fun reportComment(ownerId: Int, commentId: Int, reason: Int) {

        when (reason) {
            !in reasonsText.indices -> throw UnknownReasonException()
            7 -> throw UnknownReasonException()
        }

        if (comments.none { it.id == commentId }) {
            throw PostNotFoundException()
        }

        reports = reports.plus(Report(ownerId, commentId, reason))
    }
}