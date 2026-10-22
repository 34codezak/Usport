package com.example.usport.domain.models

// domain/models/MatchState.kt
// This enforces your "first-class state transition" requirement
sealed class MatchState {
    object Draft : MatchState()
    object Scheduled : MatchState()
    object Ongoing : MatchState()
    data class ResultSubmitted(val homeScore: Int, val awayScore: Int) : MatchState()
    object Verified : MatchState()
    object Finalized : MatchState()
    object Cancelled : MatchState()
}