import org.junit.Test
import org.junit.Assert.*

class WallServiceTest {

    val wallService = WallService()

    @Test
    fun add() {
        var post = Post()
        post = wallService.add(post)
        assertEquals(post.id, 0)
    }

    @Test
    fun update() {
        var post = Post()
        post = wallService.add(post)

        assertTrue(wallService.update(post))
        assertFalse(wallService.update(Post()))
    }
}