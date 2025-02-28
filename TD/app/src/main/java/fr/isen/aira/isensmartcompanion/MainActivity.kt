package fr.isen.aira.isensmartcompanion

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
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
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.parcelize.Parcelize

// 1. Data class pour modéliser un Event (Parcelable)
@Parcelize
data class Event(
    val category: String,
    val date: String,
    val description: String,
    val id: String,
    val location: String,
    val title: String
) : Parcelable


// 2. MainActivity avec Navigation Compose
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
    // Récupération éventuelle de l'extra "destination" passée par EventDetailActivity
    val context = LocalContext.current
    val destination = (context as? ComponentActivity)?.intent?.getStringExtra("destination")

    Scaffold(
        containerColor = Color.White,
        topBar = {
            // TopAppBar bleu avec logo à gauche et texte à droite
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
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeScreen() }
                composable("events") { EventsScreen() }
                composable("page3") { Page3Screen() }
            }
            // Si un extra "destination" est présent, naviguer vers l'écran correspondant
            destination?.let {
                LaunchedEffect(it) {
                    navController.navigate(it) {
                        popUpTo("home") { inclusive = true }
                    }
                }
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

// 3. HomeScreen avec interface Q&R
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

// 4. Page3Screen (placeholder)
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

// 5. EventDetailActivity avec barre de navigation similaire
class EventDetailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ISENSmartCompanionTheme {
                val event = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra("event", Event::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra("event")
                }
                // On affiche un Scaffold avec topBar et bottomBar identiques
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
                    bottomBar = {
                        // Barre de navigation pour l'Activity de détail
                        BottomNavigationBarForDetail()
                    }
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
        }
    }
}

// Bottom navigation bar for EventDetailActivity qui lance MainActivity
@Composable
fun BottomNavigationBarForDetail() {
    val context = LocalContext.current
    NavigationBar(containerColor = Color.Red) {
        NavigationBarItem(
            selected = false,
            onClick = {
                // Lancer MainActivity et afficher l'écran Home
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
                // Lancer MainActivity et afficher l'écran Events
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
                // Lancer MainActivity et afficher l'écran Page 3
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

// 6. EventDetailScreen affichant les détails de l'événement
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
