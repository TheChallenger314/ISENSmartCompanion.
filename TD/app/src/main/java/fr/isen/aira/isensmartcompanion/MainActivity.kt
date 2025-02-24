package fr.isen.aira.isensmartcompanion

import android.content.Intent
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.aira.isensmartcompanion.ui.theme.ISENSmartCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ISENSmartCompanionTheme {
                MainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()

    Scaffold(
        containerColor = Color.White, // All pages have a white background
        topBar = {
            // TopAppBar with blue background: logo at left and text at right
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
        bottomBar = {
            // Bottom navigation bar with a red background and enlarged text
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        // Content area with a white background
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") { HomeScreen() }
                composable("events") { EventsScreen() }  // Events screen
                composable("page3") { Page3Screen() }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(containerColor = Color.Red) {
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            },
            label = { Text("Home", fontSize = 28.sp) },
            icon = {}
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("events") },
            label = { Text("Events", fontSize = 28.sp) },
            icon = {}
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("page3") },
            label = { Text("Page 3", fontSize = 28.sp) },
            icon = {}
        )
    }
}

@Composable
fun HomeScreen() {
    var userQuestion by remember { mutableStateOf("") }
    var aiResponse by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Bienvenue sur la page d'accueil",
            color = Color.Black,
            fontSize = 20.sp
        )
        OutlinedTextField(
            value = userQuestion,
            onValueChange = { userQuestion = it },
            label = { Text("Posez une question") },
            textStyle = TextStyle(color = Color.Black), // Text input appears in black
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                aiResponse = if (userQuestion.isNotBlank()) {
                    "Vous avez demandé : $userQuestion"
                } else {
                    "Veuillez entrer une question."
                }
                userQuestion = ""
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Envoyer")
        }
        if (aiResponse.isNotEmpty()) {
            Text(
                text = aiResponse,
                modifier = Modifier.padding(top = 8.dp),
                color = Color.Black
            )
        }
    }
}

@Composable
fun EventsScreen() {
    // This is the events screen
    val context = LocalContext.current
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Bienvenue sur la page Events",
            color = Color.Black,
            fontSize = 20.sp
        )
        // Button to access the EventDetailActivity (will be replaced later)
        Button(
            onClick = {
                // Launch EventDetailActivity
                val intent = Intent(context, EventDetailActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("View Event Details")
        }
    }
}

@Composable
fun Page3Screen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Bienvenue sur la Page 3",
            color = Color.Black,
            fontSize = 20.sp
        )
    }
}

// New activity for event details
class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ISENSmartCompanionTheme {
                EventDetailScreen()
            }
        }
    }
}

@Composable
fun EventDetailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Event Details Here", fontSize = 24.sp, color = Color.Black)
        // More event details can be added here later
    }
}
