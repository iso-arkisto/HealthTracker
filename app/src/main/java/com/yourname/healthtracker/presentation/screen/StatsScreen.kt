package com.yourname.healthtracker.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourname.healthtracker.R
import com.yourname.healthtracker.domain.Food
import com.yourname.healthtracker.domain.FoodType
import com.yourname.healthtracker.presentation.viewmodel.FoodViewModel
import com.yourname.healthtracker.data.repository.MainRepository
import com.yourname.healthtracker.presentation.components.DeterminateProgressWithText
import com.yourname.healthtracker.presentation.components.FoodLogCard
import com.yourname.healthtracker.ui.theme.FoodColor
import com.yourname.healthtracker.ui.theme.WaterColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(foodViewModel: FoodViewModel, repository: MainRepository) {
    val foodDay = foodViewModel.currentDay.collectAsState()

    val menu2options = listOf(
        R.string.logs,
        R.string.bccu,
        R.string.drinks
    )

    val emptyLogsListTexts = listOf(
        R.string.empty_logs_list_friendly,
        R.string.empty_logs_list_motivate,
        R.string.empty_logs_list_playful,
    )
    val emptyLogsListResult = emptyLogsListTexts.random()

    var chosenTab: Int by remember {
        mutableIntStateOf(R.string.logs)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = repository.getCurrentDate(),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
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
                if(foodDay.value.logs.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(foodDay.value.logs.sortedByDescending { it.timestamp }) { log ->
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

                           if(food!=null) {
                               FoodLogCard(
                                   name = name,
                                   icon = food.icon,
                                   time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                                       Date(log.timestamp)
                                   ),
                                   amount = "${log.amount} ${if(type == FoodType.FOOD) stringResource(R.string.grams) else stringResource(R.string.milliliters)}",
                                   onDelete = {
                                       foodViewModel.removeFoodLog(log.id)
                                   }
                               )
                           }

//                            Text(
//                                text = "+${log.amount} ${
//                                    if (type == FoodType.FOOD) stringResource(R.string.grams) else stringResource(
//                                        R.string.milliliters
//                                    )
//                                } of $name (${
//                                    SimpleDateFormat("HH:mm", Locale.getDefault()).format(
//                                        Date(log.timestamp)
//                                    )
//                                })"
//                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(emptyLogsListResult),
                            fontSize = 20.sp,
                            modifier = Modifier
                                .width(300.dp)
                                .padding(bottom = 30.dp)
                        )
                    }
                }

        } else if(chosenTab == R.string.drinks) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    DeterminateProgressWithText(
                        stringResource(R.string.liquid_level),
                        foodViewModel.waterProgress,
                        WaterColor,
                        modifier = Modifier.size(150.dp)
                    )
                }
            }
        } else if(chosenTab == R.string.bccu) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DeterminateProgressWithText(
                    stringResource(R.string.calories),
                    foodViewModel.caloriesProgress,
                    FoodColor,
                    modifier = Modifier.size(150.dp)

                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DeterminateProgressWithText(
                        stringResource(R.string.protein),
                        foodViewModel.proteinProgress,
                        Color.Blue,
                        modifier = Modifier.size(100.dp)

                    )
                    DeterminateProgressWithText(
                        stringResource(R.string.fats),
                        foodViewModel.fatsProgress,
                        Color.Yellow,
                        modifier = Modifier.size(100.dp)

                    )
                    DeterminateProgressWithText(
                        stringResource(R.string.carbs),
                        foodViewModel.carbsProgress,
                        Color.Green,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
        }


    }
}