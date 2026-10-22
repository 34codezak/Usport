package com.example.usport.data.remote.websocket

// data/remote/websocket/LiveMatchSocketService.kt
class LiveMatchSocketService @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val moshi: Moshi
) {
    private var webSocket: WebSocket? = null

    // Exposes live score updates as a Flow
    fun connectToMatch(matchId: String): Flow<LiveScoreUpdate> = callbackFlow {
        val request = Request.Builder()
            .url("wss://api.kirinyagasports.ac.ke/ws/matches/$matchId")
            .build()

        webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                // Parse the incoming JSON (e.g., {"homeScore": 2, "awayScore": 1, "minute": 75})
                val update = moshi.adapter(LiveScoreUpdate::class.java).fromJson(text)
                if (update != null) {
                    trySend(update) // Emit to the Flow
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                close(t)
            }
        })

        awaitClose {
            webSocket?.close(1000, "User left screen")
            webSocket = null
        }
    }
}

data class LiveScoreUpdate(
    val homeScore: Int,
    val awayScore: Int,
    val matchMinute: Int,
    val state: String // e.g., "ONGOING", "HALF_TIME"
)