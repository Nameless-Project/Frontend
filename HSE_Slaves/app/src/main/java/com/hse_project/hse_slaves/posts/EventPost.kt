package com.hse_project.hse_slaves.posts

data class EventPost(

    var name: String,
    var description: String,
    var date: String,
    var id: String,
    var specialization: String,
    var ratio: String,
    var geo: String,
    var images: Array<ByteArray>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EventPost

        if (name != other.name) return false
        if (description != other.description) return false
        if (date != other.date) return false
        if (id != other.id) return false
        if (specialization != other.specialization) return false
        if (ratio != other.ratio) return false
        if (geo != other.geo) return false
        if (!images.contentEquals(other.images)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + specialization.hashCode()
        result = 31 * result + ratio.hashCode()
        result = 31 * result + geo.hashCode()
        result = 31 * result + images.contentHashCode()
        return result
    }
}
