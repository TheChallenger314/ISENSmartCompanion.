package fr.isen.aira.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.aira.isensmartcompanion.ui.theme.ISENSmartCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ISENSmartCompanionTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var userQuestion by remember { mutableStateOf("") }
    var aiResponse by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Top: ISEN Rennes logo + "Smart Companion"
            Column(
                modifier = Modifier
                    .padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. ISEN Rennes Logo
                Image(
                    painter = painterResource(id = R.drawable.isen_rennes), // rename file if needed
                    contentDescription = "ISEN Rennes Logo",
                    modifier = Modifier
                        .size(180.dp) // Increased size by 50% (120 * 1.5 = 180)
                )
                Spacer(modifier = Modifier.height(8.dp))
                // 2. Subtitle text
                Text(
                    text = "Smart Companion",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            // Bottom: Text field + Send button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Text field (occupies most of the row)
                OutlinedTextField(
                    value = userQuestion,
                    onValueChange = { userQuestion = it },
                    placeholder = { Text("Ask something...") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Circular red button with a right arrow
                FloatingActionButton(
                    onClick = {
                        // Handle "Send" action here
                        aiResponse = if (userQuestion.isNotBlank()) {
                            "You asked: $userQuestion"
                        } else {
                            "Please enter a question."
                        }
                        userQuestion = ""
                    },
                    containerColor = Color.Red,
                    modifier = Modifier.clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Send",
                        tint = Color.White
                    )
                }
            }
            if (aiResponse.isNotEmpty()) {
                Text(
                    text = aiResponse,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ISENSmartCompanionTheme {
        MainScreen()
    }
}