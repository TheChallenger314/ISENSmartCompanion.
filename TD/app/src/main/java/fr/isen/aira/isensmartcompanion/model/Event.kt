package fr.isen.aira.isensmartcompanion.model

import java.io.Serializable

data class Event(
    val category: String,
    val date: String,
    val description: String,
    val id: String,
    val location: String,
    val title: String
) : Serializable
