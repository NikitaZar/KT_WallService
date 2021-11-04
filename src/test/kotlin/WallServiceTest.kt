import exceptionClasses.PostNotFoundException
import exceptionClasses.UnknownReasonException
import org.junit.Test
import org.junit.Assert.*
import java.util.*

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

    @Test
    fun createComment_successful() {
        val wallService = WallService()
        wallService.add(Post())
        wallService.add(Post())
        wallService.createComment(Comment(2))
    }

    @Test(expected = PostNotFoundException::class)
    fun createComment_ThrowPostNotFoundException() {
        val wallService = WallService()
        wallService.add(Post())
        wallService.createComment(Comment(10))
    }

    @Test
    fun reportComment_successful() {
        val wallService = WallService()
        wallService.add(Post())
        wallService.add(Post())
        wallService.createComment(Comment(1))
        wallService.createComment(Comment(2))

        wallService.reportComment(11, 1, 0)
        wallService.reportComment(12, 2, 1)
        wallService.reportComment(13, 2, 3)
        wallService.reportComment(14, 2, 4)
        wallService.reportComment(15, 2, 5)
        wallService.reportComment(16, 2, 6)
        wallService.reportComment(17, 2, 8)
    }

    @Test(expected = PostNotFoundException::class)
    fun reportComment_ThrowPostNotFoundException() {
        val wallService = WallService()
        wallService.add(Post())
        wallService.reportComment(1, 2, 0)
    }

    @Test(expected = UnknownReasonException::class)
    fun reportComment_UnknownReasonException_7() {
        val wallService = WallService()
        wallService.add(Post())
        wallService.reportComment(1, 1, 7)
    }

    @Test(expected = UnknownReasonException::class)
    fun reportComment_UnknownReasonException_9() {
        val wallService = WallService()
        wallService.add(Post())
        wallService.reportComment(1, 1, 9)
    }

    @Test(expected = UnknownReasonException::class)
    fun reportComment_UnknownReasonException_negativeNumber() {
        val wallService = WallService()
        wallService.add(Post())
        wallService.reportComment(1, 1, -1)
    }
}