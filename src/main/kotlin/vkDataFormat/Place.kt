package vkDataFormat

data class Place(
    val id: Int,
    val title: String,
    val latitude: Int,
    val longitude: Int,
    val created: Int,
    val icon: String,
    val checkins: Int,
    val update: Int,
    val type: Int,
    val country: Int,
    val city: Int,
    val address: String
)
