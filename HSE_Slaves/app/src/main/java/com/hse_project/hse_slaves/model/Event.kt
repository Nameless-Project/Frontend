package com.hse_project.hse_slaves.model

data class Event (
    val name: String,
    val description: String,
    val date: String,
    val id: String,
    val specialization: String,
    val ratio: String,
    val geo: String,
    val images: Array<ByteArray>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

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