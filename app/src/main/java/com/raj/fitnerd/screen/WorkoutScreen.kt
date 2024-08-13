package com.raj.fitnerd.screen

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
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raj.fitnerd.screen.component.CardElements
import com.raj.fitnerd.viewmodel.RoomViewModel


@Composable
fun WorkoutScreen(
    roomViewModel: RoomViewModel = viewModel(), onJoinClicked: (Room) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    val lazyColumnState = rememberLazyListState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
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
                RoomItem(room = room, modifier = Modifier
                    .height(80.dp)
                    .weight(1f), onJoinClicked = { onJoinClicked(room) })

            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                showDialog = true
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Muscle")
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
                        Button(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                        Button(onClick = {
                            if (name.isNotBlank()) {
                                showDialog = false
                                roomViewModel.createRoom(name)
                            }
                        }) {
                            Text("Add")
                        }

                    }
                })
        }

    }
}

@Composable
fun RoomItem(
    room: Room, onJoinClicked: (Room) -> Unit, modifier: Modifier
) {
    if (room.name != "Runs" && room.name != "Calorie") {
        CardElements(
            modifier = modifier,
            text = room.name,
            img = null,
            onClick = { onJoinClicked(room) })

    }
}

