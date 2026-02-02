package com.juarez.lingocards.data.seed

import kotlinx.serialization.Serializable

@Serializable
data class CardSeedDto(
    val de: String,
    val es: String,
    val image: String? = null,
    val topic: String? = null
)
