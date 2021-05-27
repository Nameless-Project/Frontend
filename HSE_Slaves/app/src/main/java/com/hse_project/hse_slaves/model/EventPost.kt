package com.hse_project.hse_slaves.model

data class EventPost(
    val name: String,
    val description: String,
    val rating: Double,
    val geoData: String,
    val specialization: String,
    val date: String,
    val images: List<String>,
) {

}