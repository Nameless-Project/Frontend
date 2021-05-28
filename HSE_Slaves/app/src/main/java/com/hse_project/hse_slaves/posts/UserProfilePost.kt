package com.hse_project.hse_slaves.posts

data class UserProfilePost(

    var firstName: String,
    var secondName: String,
    var thirdName: String,
    var nikName: String,
    var description: String,
    var type: String,
    var id: String,
    var specialization: String,
    var ratio: String,
    var images: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserProfilePost

        if (firstName != other.firstName) return false
        if (secondName != other.secondName) return false
        if (thirdName != other.thirdName) return false
        if (nikName != other.nikName) return false
        if (description != other.description) return false
        if (type != other.type) return false
        if (id != other.id) return false
        if (specialization != other.specialization) return false
        if (ratio != other.ratio) return false
        if (images != null) {
            if (other.images == null) return false
            if (!images.contentDeepEquals(other.images)) return false
        } else if (other.images != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + secondName.hashCode()
        result = 31 * result + thirdName.hashCode()
        result = 31 * result + nikName.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + specialization.hashCode()
        result = 31 * result + ratio.hashCode()
        result = 31 * result + (images?.contentDeepHashCode() ?: 0)
        return result
    }
}