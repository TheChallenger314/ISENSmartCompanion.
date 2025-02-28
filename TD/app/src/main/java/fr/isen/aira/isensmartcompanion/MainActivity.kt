@file:OptIn(ExperimentalMaterial3Api::class)
package fr.isen.aira.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.compose.*
import fr.isen.aira.isensmartcompanion.ui.theme.ISENSmartCompanionTheme
import java.io.Serializable

// -------------------
// DATA CLASS: Event
// -------------------
data class Event(
    val category: String,
    val date: String,
    val description: String,
    val id: String,
    val location: String,
    val title: String
) : Serializable

// -------------------------
// MAIN ACTIVITY
// -------------------------
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

@Composable
fun MainApp() {
    val navController = rememberNavController()

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
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            // NAVIGATION GRAPH
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeScreen() }
                composable("events") { EventsScreen() }
                composable("page3") { Page3Screen() }
            }
        }
    }
}

// -------------------------
// BOTTOM NAVIGATION
// -------------------------
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

// -------------------------
// HOME SCREEN
// -------------------------
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
            textStyle = TextStyle(color = Color.Black),
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

// -------------------------
// PAGE 3 (PLACEHOLDER)
// -------------------------
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
