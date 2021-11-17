import exceptionClasses.CommentNotFoundException
import exceptionClasses.NoteNotFoundException
import noteService.NoteService
import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @Test
    fun add() {
        val noteService = NoteService()
        val id1 = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val id2 = noteService.add("Заметка 2", "Текст заметки", emptyArray(), emptyArray())

        assertEquals(id1, 1)
        assertEquals(id2, 2)
    }

    @Test
    fun createComment_successful() {
        val noteService = NoteService()
        val idNote = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val idComment = noteService.createComment(idNote, 1, "Текст комментария")

        assertEquals(idComment, 1)
    }

    @Test(expected = NoteNotFoundException::class)
    fun createComment_NoteNotFound() {
        val noteService = NoteService()
        val idNote = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        noteService.createComment(idNote + 1, 1, "Текст комментария")
    }

    @Test
    fun delete_successful() {
        val noteService = NoteService()
        val idNote = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val rv = noteService.delete(idNote)
        assertEquals(rv, 1)
    }

    @Test(expected = NoteNotFoundException::class)
    fun delete_NoteNotFound() {
        val noteService = NoteService()
        val idNote = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val rv = noteService.delete(idNote + 1)
        assertEquals(rv, 1)
    }

    @Test
    fun deleteComment_successful() {
        val noteService = NoteService()
        val idNote = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val idComment = noteService.createComment(idNote, 1, "Текст комментария")
        val rv = noteService.deleteComment(idComment)
        assertEquals(rv, 1)
    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteComment_NoteNotFound() {
        val noteService = NoteService()
        val idNote = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val idComment = noteService.createComment(idNote + 1, 1, "Текст комментария")
    }

    @Test
    fun edit_successful() {
        val noteService = NoteService()
        val idNote = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val rv = noteService.edit(idNote, "Заметка 1(1)", "Текст заметки (1)", emptyArray(), emptyArray())
        assertEquals(rv, 1)
    }

    @Test(expected = NoteNotFoundException::class)
    fun edit_NoteNotFound() {
        val noteService = NoteService()
        val idNote = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val rv = noteService.edit(idNote + 1, "Заметка 1(1)", "Текст заметки (1)", emptyArray(), emptyArray())
    }

    @Test
    fun editComment_successful() {
        val noteService = NoteService()
        val idNote = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val idComment = noteService.createComment(idNote, 1, "Текст комментария")
        val rv = noteService.editComment(idComment, "Текст комментария (1)")
        assertEquals(rv, 1)
    }

    @Test
    fun get() {
        val noteService = NoteService()
        val idNote1 = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val idNote2 = noteService.add("Заметка 2", "Текст заметки", emptyArray(), emptyArray())
        val idNote3 = noteService.add("Заметка 3", "Текст заметки", emptyArray(), emptyArray())

        var rv = noteService.get(arrayOf(idNote1), arrayOf(NoteService.ownerId), 0, 1, 1)

        assertEquals(rv.first().title, "Заметка 1")

        rv = noteService.get(arrayOf(idNote1, idNote2), arrayOf(NoteService.ownerId), 0, 2, 1)

        assertEquals(rv.component1().title, "Заметка 1")
        assertEquals(rv.component2().title, "Заметка 2")

        rv = noteService.get(arrayOf(idNote1, idNote2), arrayOf(NoteService.ownerId), 1, 1, 1)

        assertEquals(rv.component1().title, "Заметка 2")
    }

    @Test(expected = NoteNotFoundException::class)
    fun get_NoteNotFound() {
        val noteService = NoteService()
        val idNote1 = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val idNote2 = noteService.add("Заметка 2", "Текст заметки", emptyArray(), emptyArray())
        val idNote3 = noteService.add("Заметка 3", "Текст заметки", emptyArray(), emptyArray())

        var rv = noteService.get(arrayOf(idNote3 + 1), arrayOf(NoteService.ownerId), 0, 1, 1)
    }

    @Test
    fun getById_successful() {
        val noteService = NoteService()
        val idNote1 = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())

        val rv = noteService.getById(idNote1)

        assertEquals(rv.id, idNote1)
    }

    @Test
    fun getComment_successful() {
        val noteService = NoteService()
        val idNote = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val idComment = noteService.createComment(idNote, 1, "Текст комментария")

        val rv = noteService.getComment(idNote, NoteService.ownerId, 1, 0, 1)

        assertEquals(rv.first().id, idComment)
    }

    @Test(expected = CommentNotFoundException::class)
    fun getComment_CommentNotFound() {
        val noteService = NoteService()
        val idNote = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val idComment = noteService.createComment(idNote, 1, "Текст комментария")

        val rv = noteService.getComment(idNote + 1, NoteService.ownerId, 1, 0, 1)
    }

    @Test
    fun restoreComment_successful() {
        val noteService = NoteService()
        val idNote = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val idComment = noteService.createComment(idNote, 1, "Текст комментария")

        val rv = noteService.restoreComment(idComment)

        assertEquals(rv, 1)
    }

    @Test(expected = CommentNotFoundException::class)
    fun restoreComment_CommentNotFound() {
        val noteService = NoteService()
        val idNote = noteService.add("Заметка 1", "Текст заметки", emptyArray(), emptyArray())
        val idComment = noteService.createComment(idNote, 1, "Текст комментария")

        val rv = noteService.restoreComment(idComment + 1)
    }
}