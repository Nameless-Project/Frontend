package com.hse_project.hse_slaves.posts

data class EventPostGet(
    val id: Int?,
    val name: String,
    val description: String,
    var imageHashes: List<ByteArray>,
    val organizerIDs: List<Int>?,
    val participantsIDs: List<Int>?,
    val rating: Double?,
    val geoData: String,
    val specialization: String,
    val date: String
) {
}
