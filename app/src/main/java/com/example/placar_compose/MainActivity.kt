package com.example.placar_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.placar_compose.ui.theme.PlacarcomposeTheme
import kotlinx.coroutines.delay
import java.lang.Math.max
import kotlin.concurrent.timer
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlacarcomposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TimerView()
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ScoreView(
                            Score("Fortaleza", 0, "Ceará", 0)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreView(score: Score) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            TeamScore(
                team = score.homeTeam,
                score = score.homeScore,
                buttonColor = Color.Red,
                onUpdate = { newScore ->
                    score.homeScore = newScore
                }
            )
            Text(
                text = "x",
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.Red
                )
            )
            TeamScore(
                team = score.visitorTeam,
                score = score.visitorScore,
                buttonColor = Color.Black,
                onUpdate = { newScore ->
                    score.visitorScore = newScore
                }
            )
        }
        OutlinedButton(
            onClick = {
                score.homeScore = 0
                score.visitorScore = 0
            }
        ) {
            Text("Reset")
        }
    }
}

@Composable
fun TeamScore(
    team: String,
    score: Int,
    buttonColor: Color,
    onUpdate: (Int) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = team,
            style = TextStyle(
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = {
                onUpdate(score + 1)
            },
            colors = ButtonDefaults.buttonColors(buttonColor)
        ) { Text("+") }
        Text(
            text = score.toString(),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = {
                onUpdate(max(score - 1, 0))
            },
            colors = ButtonDefaults.buttonColors(buttonColor)
        ) { Text("-") }
    }
}

class Score(
    homeTeam: String,
    homeScore: Int,
    visitorTeam: String,
    visitorScore: Int
) {
    var homeTeam by mutableStateOf(homeTeam)
    var homeScore by mutableStateOf(homeScore)
    var visitorTeam by mutableStateOf(visitorTeam)
    var visitorScore by mutableStateOf(visitorScore)
}

@Composable
fun TimerView() {
    var time by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(true) }

    LaunchedEffect(isRunning) {
        while (true) {
            delay(1000)
            if (isRunning) {
                time++
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = formatTime(time),
            style = TextStyle(fontSize = 24.sp),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { isRunning = !isRunning }
            ) {
                Text(if (isRunning) "Pausar" else "Continuar")
            }
            Button(
                onClick = { time = 0 }
            ) {
                Text("Reiniciar")
            }
        }
    }
}


fun formatTime(time: Int): String {
    val minutes = time / 60
    val seconds = time % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}



