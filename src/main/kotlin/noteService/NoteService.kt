package noteService

import Comment
import exceptionClasses.CommentNotFoundException
import exceptionClasses.NoteNotFoundException


class NoteService {
    companion object {
        val ownerId = 123456
    }

    private var notes = emptyList<Note>()
    private var comments = emptyList<Comment>()

    fun add(
        title: String,
        text: String,
        privacyView: Array<String>,
        privacyComment: Array<String>
    ): Int {
        val id: Int = if (notes.isEmpty()) {
            1
        } else {
            notes.last().id + 1
        }

        notes = notes.plus(
            Note(
                id,
                ownerId,
                title,
                text,
                0,
                0,
                0,
                privacyView,
                privacyComment,
                1,
                null
            )
        )
        return id
    }

    fun createComment(noteId: Int, replyTo: Int, message: String): Int {
        val id = if (comments.isEmpty()) {
            1
        } else {
            comments.last().id
        }

        try {
            notes.first { it.id == noteId }
        } catch (nsee: NoSuchElementException) {
            throw NoteNotFoundException()
        }

        comments = comments.plus(
            Comment(
                id = id,
                ownerId = ownerId,
                fromId = noteId,
                replyToUser = replyTo,
                text = message
            )
        )
        return id
    }

    fun delete(noteId: Int): Int {
        return try {
            val note = notes.first { it.id == noteId }
            note.isDeleted = true
            val commentsToNote = comments.filter { it.fromId == note.id }
            commentsToNote.forEach { it.isDeleted = true }
            1
        } catch (nsee: NoSuchElementException) {
            throw NoteNotFoundException()
        }
    }

    fun deleteComment(commentId: Int): Int {
        return try {
            val comment = comments.first { it.id == commentId }
            comment.isDeleted = true
            1
        } catch (nsee: NoSuchElementException) {
            throw CommentNotFoundException()
        }
    }

    fun edit(
        noteId: Int,
        title: String,
        text: String,
        privacyView: Array<String>,
        privacyComment: Array<String>
    ): Int {
        return try {
            val note = notes.first { it.id == noteId && !it.isDeleted }
            val newNote =
                note.copy(title = title, text = text, privacyView = privacyView, privacyComment = privacyComment)
            val noteIndex = notes.indexOf(note)
            val mNotes = notes.toMutableList()
            mNotes[noteIndex] = newNote
            notes = mNotes
            1
        } catch (nsee: NoSuchElementException) {
            throw NoteNotFoundException()
        }
    }

    fun editComment(commentId: Int, message: String): Int {
        return try {
            val comment = comments.first { it.id == commentId && !it.isDeleted }
            val newComment =
                comment.copy(text = message)
            val commentIndex = comments.indexOf(comment)
            val mComments = comments.toMutableList()
            mComments[commentIndex] = newComment
            comments = mComments
            1
        } catch (nsee: NoSuchElementException) {
            throw CommentNotFoundException()
        }

    }

    fun get(noteIds: Array<Int>, userIds: Array<Int>, offset: Int, count: Int, sort: Int): List<Note> {
        var list = notes.filter { noteIds.contains(it.id) && userIds.contains(it.ownerId) && !it.isDeleted }
        list = list.filterIndexed { index, _ -> (index >= offset) }
        list = list.filterIndexed { index, _ -> (index < count) }
        val dateComparator = Comparator { note1: Note, note2: Note -> note1.date - note2.date }
        list = if (sort > 0) list.sortedWith(dateComparator) else list.sortedWith(dateComparator).reversed()
        if (list.isEmpty()) throw NoteNotFoundException()
        return list
    }

    fun getById(noteId: Int): Note {
        try {
            return notes.first { it.id == noteId }
        } catch (nsee: NoSuchElementException) {
            throw NoteNotFoundException()
        }
    }

    fun getComment(noteId: Int, ownerId: Int, sort: Int, offset: Int, count: Int): List<Comment> {
        var list = comments.filter { noteId == it.fromId && ownerId == it.ownerId && !it.isDeleted }
        list = list.filterIndexed { index, _ -> (index >= offset) }
        list = list.filterIndexed { index, _ -> (index < count) }
        val dateComparator = Comparator { comment1: Comment, comment2: Comment -> comment1.date - comment2.date }
        list = if (sort > 0) list.sortedWith(dateComparator) else list.sortedWith(dateComparator).reversed()
        if (list.isEmpty()) throw CommentNotFoundException()
        return list
    }

    fun restoreComment(commentId: Int): Int {
        return try {
            val comment = comments.first { it.id == commentId }
            comment.isDeleted = false
            1
        } catch (nsee: NoSuchElementException) {
            throw CommentNotFoundException()
        }
    }
}