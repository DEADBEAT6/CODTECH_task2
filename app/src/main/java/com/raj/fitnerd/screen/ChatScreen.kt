package com.raj.fitnerd.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raj.fitnerd.data.Message
import com.raj.fitnerd.viewmodel.MessageViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    roomId: String,
    roomName: String,
    navigateBack: () -> Unit,
    messageViewModel: MessageViewModel = viewModel()
) {

    val messages by messageViewModel.messages.observeAsState(emptyList())
    messageViewModel.setRoomId(roomId)

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Track the number of messages to detect when a new message is added
    val previousMessageCount = remember { mutableStateOf(messages.size) }

    val weight = remember { mutableStateOf("") }
    val reps = remember { mutableStateOf("") }
    val text = remember { mutableStateOf("") }
    val exercise = remember { mutableStateOf("") }

    LaunchedEffect(roomId) {
        messageViewModel.setRoomId(roomId)
    }
    // Scroll to the bottom when a new message is added
    LaunchedEffect(messages.size) {
        if (messages.size > previousMessageCount.value) {
            coroutineScope.launch {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
        previousMessageCount.value = messages.size
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Display the chat messages
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(Color(0xFF73d3fb)),

            ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    navigateBack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
                Text(text = roomName, fontSize = 20.sp, modifier = Modifier.padding(16.dp))
            }
        }
        Card(
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            colors = CardDefaults.cardColors(Color(0xFFFFFFFF)),
        ) {
            LazyColumn(
                modifier = Modifier.weight(2f),
                state = listState,
            ) {
                items(messages) { message ->
                    ChatMessageItem(
                        message = message.copy(
                            isSentByCurrentUser = message.senderId == messageViewModel.currentUser.value?.email
                        )
                    )
                }
            }
        }
        // Chat input field and send icon

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                label = {
                    Text(text = "Exercise")
                },

                value = exercise.value,
                onValueChange = { exercise.value = it },
                textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .padding(end = 20.dp)
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                label = {
                    Text(text = "Weight")
                },

                value = weight.value,
                onValueChange = { weight.value = it },
                textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
            OutlinedTextField(
                label = {
                    Text(text = "Reps")
                },
                value = reps.value,
                onValueChange = { reps.value = it },
                textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )


            IconButton(
                onClick = {
                    // Send the message when the icon is clicked
                    text.value =
                        exercise.value + " : " + weight.value + "kg x " + reps.value + "times"
                    if (text.value.isNotEmpty()) {
                        messageViewModel.sendMessage(text.value.trim())
                        text.value = ""
                        weight.value = ""
                        reps.value = ""

                    }
                    messageViewModel.loadMessages()
                }
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatMessageItem(message: Message) {
    if (message.isSentByCurrentUser) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(8.dp)
            ) {
                Text(
                    text = message.text,
                    color = Color.White,
                    style = TextStyle(fontSize = 16.sp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                Text(
                    text = formatTimestamp(message.timestamp), // Replace with actual timestamp logic
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black
                    ),
                )
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
private fun formatTimestamp(timestamp: Long): String {
    val messageDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())

    return formatDate(messageDateTime)

}


@RequiresApi(Build.VERSION_CODES.O)
private fun formatDate(dateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
    return formatter.format(dateTime)
}
