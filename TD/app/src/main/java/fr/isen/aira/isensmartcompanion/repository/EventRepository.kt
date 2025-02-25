package fr.isen.aira.isensmartcompanion.repository

import fr.isen.aira.isensmartcompanion.model.Event
import fr.isen.aira.isensmartcompanion.network.RetrofitInstance

class EventRepository {
    suspend fun getEvents(): List<Event> {
        return RetrofitInstance.api.getEvents()
    }
}
