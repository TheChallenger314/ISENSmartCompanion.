package fr.isen.aira.isensmartcompanion.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val category: String
) : Parcelable
