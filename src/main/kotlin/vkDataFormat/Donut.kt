package vkDataFormat

data class Donut(
    val isDonut: Boolean = false,
    val paidDuration: Int = 0,
    val placeholder: Placeholder = Placeholder(),
    val canPublishFreeCopy: Boolean = false,
    val editMode: EditMode = EditMode.ALL,
)