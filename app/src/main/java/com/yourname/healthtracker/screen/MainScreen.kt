package com.yourname.healthtracker.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourname.healthtracker.R
import com.yourname.healthtracker.data.classes.CalorieCalculator
import com.yourname.healthtracker.data.classes.FoodType
import com.yourname.healthtracker.data.viewmodel.FoodViewModel
import com.yourname.healthtracker.data.repository.MainRepository
import com.yourname.healthtracker.ui.components.MenuTitle
import com.yourname.healthtracker.ui.components.NutritionCard
import com.yourname.healthtracker.ui.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(foodVM: FoodViewModel, repository: MainRepository) {

    var searchQuery by remember {
        mutableStateOf("")
    }

    val chosenFood = remember { mutableStateOf<Int?>(null) }
    val servingSize = remember { mutableStateOf("") }

    val context = LocalContext.current

    val menu3options = listOf(R.string.food,R.string.drinks)

    var chosenTab by remember {
        mutableIntStateOf(R.string.food)
    }

    val startSearchTexts = listOf(
        R.string.start_search_friendly,
        R.string.start_search_motivating,
        R.string.start_search_playful,
    )
    val startSearchResult = startSearchTexts.random()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (chosenFood.value == null) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onClearQuery = { searchQuery = "" }
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    menu3options.forEach { opt ->
                        val selected = chosenTab == opt
                        FilterChip(
                            selected = selected,
                            onClick = {
                                chosenTab = opt
                            },
                            label = {
                                Text(stringResource(opt))
                            },
                            leadingIcon = null,
                            modifier = Modifier.padding(start = 15.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))

                if (searchQuery.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(startSearchResult),
                            fontSize = 20.sp,
                            modifier = Modifier
                                .width(300.dp)
                                .padding(bottom = 30.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 15.dp)
                    ) {
                        items(
                            repository.getAllFood(
                            type = if (chosenTab == R.string.food) {
                                FoodType.FOOD
                            } else {
                                FoodType.DRINK
                            }
                        ).filter { food ->
                            context.getString(food.name).contains(searchQuery, ignoreCase = true)
                        }.take(10)
                        ) { food ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(MaterialTheme.colorScheme.onSecondary)
                                    .padding(top = 7.dp)
                                    .clickable {
                                        chosenFood.value = food.id
                                        Log.d("", "NEW FOOD: ${chosenFood.value == null}")
                                    }
                            ) {
                                Text(
                                    text = "${food.icon} ${stringResource(food.name)}",
                                    modifier = Modifier
                                        .padding(10.dp)
                                )
                            }
                        }
                    }
                }
            } else {
                MenuTitle(
                    title = stringResource(R.string.product),
                    onReturnClick = {
                        chosenFood.value = null
                    }
                )
                TextField(
                    value = servingSize.value,
                    onValueChange = {
                        servingSize.value = it
                        if(servingSize.value.toIntOrNull()!=null) {
                            foodVM.foodAddValue = it.toInt()
                        } else {
                            foodVM.foodAddValue = 0
                        }
                    },
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.serving_size)) },
                    modifier = Modifier.width(150.dp)

                )
                if(foodVM.foodAddValue in 1..10000) {
                    Spacer(modifier = Modifier.height(5.dp))
                    NutritionCard(
                        food = repository.findFoodById(chosenFood.value!!)!!,
                        amount = foodVM.foodAddValue
                    )
                    Button(
                        onClick = {
                            foodVM.addFood(chosenFood.value!!, foodVM.foodAddValue)
                        },
                        modifier = Modifier
                            .padding(end = 10.dp, top = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.add)
                        )
                    }
                }
            }
        }
    }
}