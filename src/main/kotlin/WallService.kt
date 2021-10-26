class WallService {
    private val posts = arrayListOf<Post>()

    fun add(post: Post): Post {
        val newPost = post.copy(id = post.hashCode())
        posts.add(newPost)
        return newPost
    }

    fun update(post: Post): Boolean {
        for (p in posts) {
            if (p.id == post.id) {
                val newPost = post.copy(ownerId = p.ownerId, date = p.date)
                return true
            }
        }
        return false
    }
}