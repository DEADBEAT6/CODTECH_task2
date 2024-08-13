package com.raj.fitnerd.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raj.fitnerd.screen.Room
import com.raj.fitnerd.screen.Screen
import com.raj.fitnerd.viewmodel.RoomViewModel


@Composable
fun WorkoutScreen(
    roomViewModel: RoomViewModel = viewModel(), onJoinClicked: (Room) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    val lazyColumnState = rememberLazyListState()

    val rooms by roomViewModel.rooms.observeAsState(emptyList())
    roomViewModel.loadRooms()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("FitNerd", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f), state = lazyColumnState
        ) {
            items(rooms) { room ->
                RoomItem(room = room, onJoinClicked = { onJoinClicked(room) })
                HorizontalDivider()
            }
        }
        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                showDialog = true
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Room")
        }

        if (showDialog) {
            AlertDialog(onDismissRequest = { showDialog = true },
                title = { Text("Create a new room") },
                text = {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {
                            if (name.isNotBlank()) {
                                showDialog = false
                                roomViewModel.createRoom(name)
                            }
                        }) {
                            Text("Add")
                        }
                        Button(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                    }
                })
        }

    }
}

@Composable
fun RoomItem(
    room: Room, onJoinClicked: (Room) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = room.name, fontSize = 16.sp, fontWeight = FontWeight.Normal)

        OutlinedButton(onClick = {
            onJoinClicked(room)
        }) {
            Text("Join")
        }
    }
}

