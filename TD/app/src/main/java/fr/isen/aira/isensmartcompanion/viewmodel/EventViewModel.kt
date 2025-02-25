package fr.isen.aira.isensmartcompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.isen.aira.isensmartcompanion.model.Event
import fr.isen.aira.isensmartcompanion.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {

    private val repository = EventRepository()

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            try {
                val eventsList = repository.getEvents()
                _events.value = eventsList
            } catch (e: Exception) {
                _error.value = "Erreur lors de la récupération des événements: ${e.message}"
            }
        }
    }
}
