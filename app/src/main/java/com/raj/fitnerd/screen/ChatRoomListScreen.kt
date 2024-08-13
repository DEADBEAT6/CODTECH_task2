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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raj.fitnerd.R
import com.raj.fitnerd.screen.component.CardElements
import com.raj.fitnerd.viewmodel.RoomViewModel

data class Room(
    val id: String = "",
    val name: String = ""
)

@Composable
fun HomeScreen(
    roomViewModel: RoomViewModel = viewModel(),
    onWorkoutClicked:()->Unit,
    onCalorieClicked:()->Unit,
    onRunClicked:()->Unit
) {
    roomViewModel.loadRooms()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("FitNerd", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))


        CardElements(
            modifier = Modifier.weight(1f),
            text = "Calorie Intake",
            img = R.drawable.calorie,
            onClick = {onCalorieClicked()})
        CardElements(
            modifier = Modifier.weight(1f),
            text = "Workout",
            img = R.drawable.workout,
            onClick = {onWorkoutClicked()})
        CardElements(
            modifier = Modifier.weight(1f),
            text = "Runs",
            img = R.drawable.run,
            onClick = {onRunClicked()})

        Spacer(modifier = Modifier.height(16.dp))

    }
}


