package com.yourname.healthtracker.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yourname.healthtracker.R
import com.yourname.healthtracker.data.FoodViewModel

@Composable
fun SettingsScreen(foodVM: FoodViewModel) {

    var waterGoal by remember { mutableStateOf("2500") }

    var errorState by remember { mutableStateOf("") }
    var saveSuccess by remember { mutableStateOf(false) }

    val saveSuccessMessage = stringResource(R.string.save_success)
    val errorMessage = stringResource(R.string.error_occurred)

    LaunchedEffect(Unit) {
        waterGoal = foodVM.day.waterGoal.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
            TextField(
                value = waterGoal,
                onValueChange = {
                    waterGoal = it
                },
                label = { Text(stringResource(R.string.water_goal)) },
            )
        Spacer(modifier = Modifier.height(20.dp))
        if(errorState.isNotEmpty()) {
            Text(
                text = errorState,
                color = if(saveSuccess) Color.Green else Color.Red
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        Button(
            onClick = {
                saveSuccess = false
                errorState = errorMessage

                if(waterGoal.toIntOrNull() != null) {
                    if(waterGoal.toInt() > 100 && waterGoal.toInt() < 10000) {
                        saveSuccess = true
                        errorState = saveSuccessMessage
                    }
                }

                if(saveSuccess) {
                    foodVM.updateSettings(
                        waterGoal.toInt()
                    )
                } else {
                    waterGoal = foodVM.day.waterGoal.toString()
                }
            }
        ) {
            Text(stringResource(R.string.save))
        }

    }
}