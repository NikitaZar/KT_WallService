import org.junit.Test
import org.junit.Assert.*

class WallServiceTest {

    @Test
    fun add() {
        val wallService = WallService()
        var post = Post()
        post = wallService.add(post)
        assertNotEquals(post.id, 0)
    }

    @Test
    fun update_foundPost() {
        val wallService = WallService()
        var post = Post()
        post = wallService.add(post)

        assertTrue(wallService.update(post))
    }

    @Test
    fun update_notFoundPost() {
        val wallService = WallService()
        assertFalse(wallService.update(Post()))
    }
}