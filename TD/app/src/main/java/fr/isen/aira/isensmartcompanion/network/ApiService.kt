package fr.isen.aira.isensmartcompanion.network

import fr.isen.aira.isensmartcompanion.model.Event
import retrofit2.http.GET

interface ApiService {
    @GET("events.json")
    suspend fun getEvents(): List<Event>
}
