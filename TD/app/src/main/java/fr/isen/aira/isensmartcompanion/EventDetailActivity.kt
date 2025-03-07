@file:OptIn(ExperimentalMaterial3Api::class)
package fr.isen.aira.isensmartcompanion

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.aira.isensmartcompanion.model.Event
import fr.isen.aira.isensmartcompanion.ui.theme.ISENSmartCompanionTheme

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ISENSmartCompanionTheme {
                val event: Event? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra("event", Event::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra<Event>("event")
                }
                EventDetailContent(event)
            }
        }
    }
}

@Composable
fun EventDetailContent(event: Event?) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.isen_rennes),
                            contentDescription = "Logo ISEN Rennes",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ISEN Smart Compagnon",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue)
            )
        },
        bottomBar = { BottomNavigationBarForDetail() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            EventDetailScreen(event)
        }
    }
}

@Composable
fun EventDetailScreen(event: Event?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        if (event != null) {
            Text(text = event.title, fontSize = 24.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Date: ${event.date}", fontSize = 16.sp, color = Color.Black)
            Text(text = "Location: ${event.location}", fontSize = 16.sp, color = Color.Black)
            Text(text = "Category: ${event.category}", fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = event.description, fontSize = 14.sp, color = Color.Black)
        } else {
            Text("Event not found.", color = Color.Black)
        }
    }
}

@Composable
fun BottomNavigationBarForDetail() {
    val context = LocalContext.current
    NavigationBar(containerColor = Color.Red) {
        NavigationBarItem(
            selected = false,
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("destination", "home")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            },
            label = { Text("Home", fontSize = 28.sp) },
            icon = {}
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("destination", "events")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            },
            label = { Text("Events", fontSize = 28.sp) },
            icon = {}
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("destination", "page3")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            },
            label = { Text("Page 3", fontSize = 28.sp) },
            icon = {}
        )
    }
}
