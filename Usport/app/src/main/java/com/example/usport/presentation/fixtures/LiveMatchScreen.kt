package com.example.usport.presentation.fixtures

// presentation/fixtures/LiveMatchScreen.kt
@Composable
fun LiveMatchScreen(matchId: String, viewModel: LiveMatchViewModel) {

    val liveScore by viewModel.liveScore.collectAsState()

    // DisposableEffect ensures the WebSocket is closed when the user leaves this screen
    DisposableEffect(matchId) {
        viewModel.startListeningToMatch(matchId)

        onDispose {
            viewModel.stopListening()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Minute: ${liveScore.matchMinute}'", style = MaterialTheme.typography.headlineMedium)

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("Home: ${liveScore.homeScore}", style = MaterialTheme.typography.displayLarge)
            Text("-", style = MaterialTheme.typography.displayLarge)
            Text("Away: ${liveScore.awayScore}", style = MaterialTheme.typography.displayLarge)
        }
    }
}