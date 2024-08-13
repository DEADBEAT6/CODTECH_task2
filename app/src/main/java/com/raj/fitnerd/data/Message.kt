package com.raj.fitnerd.data

data class Message(
    val senderFirstName: String= "",
    val senderId: String= "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isSentByCurrentUser: Boolean = false
)
data class WorkoutRecord(
    val senderFirstName: String= "",
    val senderId: String= "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isSentByCurrentUser: Boolean = false
)

data class RunRecord(
    val senderFirstName: String= "",
    val senderId: String= "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isSentByCurrentUser: Boolean = false
)

data class CaloriesRecord(
    val senderFirstName: String= "",
    val senderId: String= "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isSentByCurrentUser: Boolean = false
)

