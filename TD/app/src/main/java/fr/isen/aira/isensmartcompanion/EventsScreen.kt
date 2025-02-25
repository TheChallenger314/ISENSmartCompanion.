package fr.isen.aira.isensmartcompanion

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.isen.aira.isensmartcompanion.EventDetailActivity
import fr.isen.aira.isensmartcompanion.viewmodel.EventViewModel

@Composable
fun EventsScreen() {
    val eventViewModel: EventViewModel = viewModel()
    val events by eventViewModel.events.collectAsState()
    val error by eventViewModel.error.collectAsState()
    val context = LocalContext.current

    if (error.isNotEmpty()) {
        Text(text = error, color = Color.Red, modifier = Modifier.padding(16.dp))
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(events) { event ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        // Au clic, lancer EventDetailActivity en passant l'objet Event
                        val intent = Intent(context, EventDetailActivity::class.java)
                        intent.putExtra("event", event)
                        context.startActivity(intent)
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = event.title, fontSize = 20.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = event.date, fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
    }
}
