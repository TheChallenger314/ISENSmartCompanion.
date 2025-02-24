package fr.isen.aira.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                // Appel de la fonction racine
                MainApp()
            }
        }
    }
}

// Composable racine : contient le NavController et le Scaffold global
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            // Barre du haut commune
            TopAppBar(
                title = { Text("ISEN Smart Companion", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue)
            )
        },
        bottomBar = {
            // Barre du bas : 3 items de navigation
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        // Zone principale : gère la navigation entre les 3 pages
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
        ) {
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") { HomeScreen() }
                composable("page2") { Page2Screen() }
                composable("page3") { Page3Screen() }
            }
        }
    }
}

// Barre de navigation en bas avec 3 boutons
@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("home") {
                    // Eviter de dupliquer l'écran Home dans la back stack
                    popUpTo("home") { inclusive = true }
                }
            },
            label = { Text("Home") },
            icon = {}
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("page2") },
            label = { Text("Page 2") },
            icon = {}
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("page3") },
            label = { Text("Page 3") },
            icon = {}
        )
    }
}

// -------------------
//      ECRANS
// -------------------

@Composable
fun HomeScreen() {
    // Exemple de contenu : champ de texte et réponse "IA"
    var userQuestion by remember { mutableStateOf("") }
    var aiResponse by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Bienvenue sur la page d'accueil", color = Color.Black, fontSize = 20.sp)

        OutlinedTextField(
            value = userQuestion,
            onValueChange = { userQuestion = it },
            label = { Text("Posez une question") },
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
fun Page2Screen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Bienvenue sur la Page 2", color = Color.Black, fontSize = 20.sp)
        // Ajoutez le contenu souhaité pour la page 2
    }
}

@Composable
fun Page3Screen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Bienvenue sur la Page 3", color = Color.Black, fontSize = 20.sp)
        // Ajoutez le contenu souhaité pour la page 3
    }
}
