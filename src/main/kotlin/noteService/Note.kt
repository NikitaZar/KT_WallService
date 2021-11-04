package noteService

data class Note(
    val id: Int,
    val ownerId: Int = 0,
    var title: String,
    var text: String,
    var date: Int,
    var comments: Int,
    var readComments: Int,
    var privacyView: Array<String>,
    var privacyComment: Array<String>,
    var canComment: Int,
    var textWiki: String?,
    var isDeleted: Boolean = false
)