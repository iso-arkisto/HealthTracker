package com.yourname.healthtracker.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yourname.healthtracker.R
import com.yourname.healthtracker.data.Food
import com.yourname.healthtracker.data.FoodType
import com.yourname.healthtracker.data.FoodViewModel
import com.yourname.healthtracker.data.MainRepository
import com.yourname.healthtracker.ui.components.DeterminateProgressWithText
import com.yourname.healthtracker.ui.theme.FoodColor
import com.yourname.healthtracker.ui.theme.WaterColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(foodViewModel: FoodViewModel, repository: MainRepository) {
    var isExpanded1 by remember {
        mutableStateOf(false)
    }
    val menu1options = listOf(
        R.string.for_today,
        R.string.for_week,
        R.string.for_month,
        R.string.for_all_time
    )
    val menu2options = listOf(
        R.string.logs,
        R.string.bccu,
        R.string.drinks
    )
    var chosenTime: Int by remember {
        mutableIntStateOf(R.string.for_today)
    }
    var chosenTab: Int by remember {
        mutableIntStateOf(R.string.logs)
    }
    val emptyMessage = stringResource(R.string.empty)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded1,
            onExpandedChange = { isExpanded1 = it }
        ) {
            TextField(
                value = stringResource(chosenTime),
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded1)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = isExpanded1,
                onDismissRequest = { isExpanded1 = false }
            ) {
                menu1options.forEach {
                        opt ->
                    DropdownMenuItem(
                        text = {
                            Text(stringResource(opt))
                        },
                        onClick = {
                            chosenTime = opt
                            isExpanded1 = false
                        }
                    )
                }
            }
        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(30.dp)
//        ) {
//            Text("Logs")
//            Text("Drinks")
//            Text("BCCU")
//        }
//        BeautifulSelector(chosenTime, onOptionSelected = {
//            chosenTime = it.toInt()
//        }, modifier = Modifier.fillMaxSize(), options = menu1options)
        Spacer(modifier = Modifier.height(15.dp))
        Row(
             modifier = Modifier
                 .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            menu2options.forEach {
                opt ->
                FilterChip(
                    selected = opt == chosenTab,
                    onClick = {
                        chosenTab = opt
                    },
                    label = {
                        Text(stringResource(opt))
                    },
                    leadingIcon = null
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))

        if(chosenTab == R.string.logs) {
                if(foodViewModel.day.logs.size > 0) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(foodViewModel.day.logs.sortedByDescending { it.timestamp }) { log ->
                            val food: Food? = repository.findFoodById(log.foodId)
                            var name: String
                            var type: FoodType

                            if (food != null) {
                                name = stringResource(food.name)
                                type = food.type
                            } else {
                                name = "???"
                                type = FoodType.DRINK
                            }

                            Text(
                                text = "+${log.amount} ${
                                    if (type == FoodType.FOOD) stringResource(R.string.grams) else stringResource(
                                        R.string.milliliters
                                    )
                                } of $name (${
                                    SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                                        Date(log.timestamp)
                                    )
                                })"
                            )
                        }
                    }
                } else {
                    Text(text = emptyMessage)
                }

        } else if(chosenTab == R.string.drinks) {
            DeterminateProgressWithText(
                stringResource(R.string.water),
                foodViewModel.waterProgress,
                WaterColor
            )
        } else if(chosenTab == R.string.bccu) {
            DeterminateProgressWithText(
                stringResource(R.string.calories),
                foodViewModel.caloriesProgress,
                FoodColor
            )
        }


    }
}