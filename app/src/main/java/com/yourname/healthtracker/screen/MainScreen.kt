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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourname.healthtracker.R
import com.yourname.healthtracker.data.FoodType
import com.yourname.healthtracker.data.FoodViewModel
import com.yourname.healthtracker.data.MainRepository
import com.yourname.healthtracker.ui.components.DeterminateProgressWithText
import com.yourname.healthtracker.ui.theme.FoodColor
import com.yourname.healthtracker.ui.theme.WaterColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(foodVM: FoodViewModel, repository: MainRepository) {

    var isExpanded1 by remember {
        mutableStateOf(false)
    }
    var chosenDrink by remember {
        mutableIntStateOf(1)
    }

    var isExpanded2 by remember {
        mutableStateOf(false)
    }
    var chosenFood by remember {
        mutableIntStateOf(35)
    }

    val menu1options = repository.getAllFood(FoodType.DRINK).sortedBy { it.name }
    val menu2options = repository.getAllFood(FoodType.FOOD).sortedBy { it.name }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DeterminateProgressWithText(
                    title = stringResource(R.string.water),
                    progress = foodVM.waterProgress,
                    color = WaterColor
                )
                Spacer(modifier = Modifier.height(30.dp))
                ExposedDropdownMenuBox(
                    expanded = isExpanded1,
                    onExpandedChange = { isExpanded1 = it }
                ) {
                    TextField(
                        value = if(repository.findFoodById(chosenDrink) != null) stringResource(repository.findFoodById(chosenDrink)!!.name) else stringResource(R.string.not_found),
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
                                    Text(stringResource(opt.name))
                                },
                                onClick = {
                                    chosenDrink = opt.id
                                    isExpanded1 = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        foodVM.addFood(chosenDrink,200)
                    }
                ) {
                    Text(
                        text = "+200 ${stringResource(R.string.milliliters)}"
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                DeterminateProgressWithText(
                    title = stringResource(R.string.calories),
                    progress = foodVM.caloriesProgress,
                    color = FoodColor
                )
                Spacer(modifier = Modifier.height(30.dp))
                ExposedDropdownMenuBox(
                    expanded = isExpanded2,
                    onExpandedChange = { isExpanded2 = it }
                ) {
                    TextField(
                        value = if(repository.findFoodById(chosenFood) != null) stringResource(repository.findFoodById(chosenFood)!!.name) else stringResource(R.string.not_found),
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded1)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isExpanded2,
                        onDismissRequest = { isExpanded2 = false }
                    ) {
                        menu2options.forEach {
                                opt ->
                            DropdownMenuItem(
                                text = {
                                    Text(stringResource(opt.name))
                                },
                                onClick = {
                                    chosenFood = opt.id
                                    isExpanded2 = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        foodVM.addFood(chosenFood,200)
                    }
                ) {
                    Text(
                        text = "+200 ${stringResource(R.string.grams)}"
                    )
                }
            }
//        }

    }
}