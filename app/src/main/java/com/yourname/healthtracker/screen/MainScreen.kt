package com.yourname.healthtracker.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourname.healthtracker.R
import com.yourname.healthtracker.data.FoodViewModel
import com.yourname.healthtracker.data.MainRepository
import com.yourname.healthtracker.ui.components.DeterminateProgressWithText
import com.yourname.healthtracker.ui.theme.WaterColor

@Composable
fun MainScreen(foodVM: FoodViewModel, repository: MainRepository) {

    var isExpanded1 by remember {
        mutableStateOf(false)
    }
    var chosenDrink by remember {
        mutableIntStateOf(1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            DeterminateProgressWithText(
                title = stringResource(R.string.water),
                progress = foodVM.waterProgress,
                color = WaterColor
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
//        ExposedDropdownMenuBox(
//            expanded = isExpanded1,
//            onExpandedChange = { isExpanded1 = it }
//        ) {
//            TextField(
//                value = apivm.appTheme.value,
//                onValueChange = {},
//                readOnly = true,
//                trailingIcon = {
//                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded1)
//                },
//                colors = ExposedDropdownMenuDefaults.textFieldColors(),
//                modifier = Modifier.menuAnchor()
//            )
//
//            ExposedDropdownMenu(
//                expanded = isExpanded1,
//                onDismissRequest = { isExpanded1 = false }
//            ) {
//                menu1options.forEach {
//                        opt ->
//                    DropdownMenuItem(
//                        text = { Text(text = opt) },
//                        onClick = {
//                            apivm.appTheme.value = opt
//                            isExpanded1 = false
////                                        Log.d("cool_log",viewModel.appTheme.value)
//                            when(apivm.appTheme.value) {
//                                "Светлая" -> AppCompatDelegate.setDefaultNightMode(
//                                    AppCompatDelegate.MODE_NIGHT_NO
//                                )
//                                "Тёмная" -> AppCompatDelegate.setDefaultNightMode(
//                                    AppCompatDelegate.MODE_NIGHT_YES
//                                )
//                            }
//                        }
//                    )
//                }
//            }
//        }
        Button(
            onClick = {
                foodVM.addFood(1,200)
            }
        ) {
            Text(
                text = "+200 мл"
            )
        }
    }
}