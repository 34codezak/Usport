package com.example.usport.domain.models

enum class UserRole {

    SYSTEM_ADMIN, SPORTS_ADMIN, TEAM_MANAGER, REFEREE, ATHLETE, STUDENT;

    fun canEditResult(): Boolean = this in listOf(SYSTEM_ADMIN, SPORTS_ADMIN, REFEREE)
    fun canFinalizeResult(): Boolean = this in listOf(SYSTEM_ADMIN, SPORTS_ADMIN)
}