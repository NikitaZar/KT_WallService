class WallService {
    private val posts = arrayListOf<Post>()

    fun add(post: Post): Post {
        val newPost: Post = when (posts.isEmpty()) {
            true -> post.copy(id = 1)
            false -> post.copy(id = posts.last().id + 1)
        }

        posts.add(newPost)
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
}