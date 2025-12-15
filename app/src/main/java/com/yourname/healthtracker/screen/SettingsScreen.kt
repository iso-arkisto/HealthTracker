package com.yourname.healthtracker.screen

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourname.healthtracker.R
import com.yourname.healthtracker.data.ActivityLevel
import com.yourname.healthtracker.data.CalorieCalculator
import com.yourname.healthtracker.data.FitnessGoal
import com.yourname.healthtracker.data.FoodViewModel
import com.yourname.healthtracker.data.ProfileViewModel
import com.yourname.healthtracker.ui.components.MenuTab
import com.yourname.healthtracker.ui.components.MenuTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(foodVM: FoodViewModel, profileVM: ProfileViewModel) {

    val userProfile = profileVM.userProfile.collectAsState()
    val foodDay = foodVM.day.collectAsState()

    var waterGoal by remember { mutableStateOf("2500") }
    var caloriesGoal by remember { mutableStateOf("2200") }
    var proteinGoal by remember { mutableStateOf("84.0") }
    var fatsGoal by remember { mutableStateOf("60.0") }
    var carbsGoal by remember { mutableStateOf("300.0") }

    var gender by remember { mutableIntStateOf(R.string.male) }
    var age by remember { mutableStateOf("251") }
    var weight by remember { mutableStateOf("65.0") }
    var height by remember { mutableStateOf("170.0") }
    var activityLevel by remember { mutableStateOf(ActivityLevel.MODERATE) }
    var fitnessGoal by remember { mutableStateOf(FitnessGoal.MAINTENANCE) }

    var errorState by remember { mutableStateOf("") }
    var chosenTab by remember { mutableIntStateOf(0) }
    var saveSuccess by remember { mutableStateOf(false) }

    var saveSuccess2 by remember { mutableStateOf(false) }
    var errorState2 by remember { mutableStateOf("") }

    val saveSuccessMessage = stringResource(R.string.save_success)
    val errorMessage = stringResource(R.string.error_occurred)

    var isExpanded1 by remember { mutableStateOf(false) }
    val menu1options = listOf(R.string.male,R.string.female)

    var isExpanded2 by remember { mutableStateOf(false) }
    val menu2options = listOf(
        ActivityLevel.SEDENTARY,
        ActivityLevel.LIGHT,
        ActivityLevel.MODERATE,
        ActivityLevel.ACTIVE,
        ActivityLevel.EXTREME
    )

    val menu3options = listOf(
        FitnessGoal.MAINTENANCE,
        FitnessGoal.WEIGHT_LOSS,
        FitnessGoal.WEIGHT_GAIN
    )
    val recommendedMacroNutrients = CalorieCalculator.calculateMacros(
        foodDay.value.caloriesGoal,
        userProfile.value.fitnessGoal
    )



    LaunchedEffect(Unit) {
        waterGoal = foodDay.value.waterGoal.toString()
        caloriesGoal = foodDay.value.caloriesGoal.toString()
        proteinGoal = foodDay.value.proteinGoal.toString()
        fatsGoal = foodDay.value.fatsGoal.toString()
        carbsGoal = foodDay.value.carbsGoal.toString()

        gender = userProfile.value.gender
        age = userProfile.value.age.toString()
        height = userProfile.value.height.toString()
        weight = userProfile.value.weight.toString()
        activityLevel = userProfile.value.activityLevel
        fitnessGoal = userProfile.value.fitnessGoal
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if(chosenTab == 0) {
            MenuTab(
                tab = stringResource(R.string.basic_info),
                onClick = { chosenTab = R.string.basic_info }
            )
            MenuTab(
                tab = stringResource(R.string.personal_goals),
                onClick = { chosenTab = R.string.personal_goals }
            )
        } else if(chosenTab == R.string.personal_goals) {
            MenuTitle(
                title = stringResource(R.string.personal_goals),
                onReturnClick = {
                    chosenTab = 0
                }
            )
            Text(
                stringResource(R.string.set_goals),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                value = waterGoal,
                onValueChange = {
                    waterGoal = it
                },
                label = { Text("${stringResource(R.string.water)} (${stringResource(R.string.milliliters)})") },
                modifier = Modifier.width(150.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                value = caloriesGoal,
                onValueChange = {
                    caloriesGoal = it
                },
                label = { Text("${stringResource(R.string.calories)} (${stringResource(R.string.grams)}) - ${CalorieCalculator.calculateDailyCalories(userProfile.value)}*") },
                modifier = Modifier.width(150.dp)

            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                value = proteinGoal,
                onValueChange = {
                    proteinGoal = it
                },
                label = { Text("${stringResource(R.string.protein)} (${stringResource(R.string.grams)}) - ${recommendedMacroNutrients.protein}*") },
                modifier = Modifier.width(150.dp)

            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                value = fatsGoal,
                onValueChange = {
                    fatsGoal = it
                },
                label = { Text("${stringResource(R.string.fats)} (${stringResource(R.string.grams)})  - ${recommendedMacroNutrients.fats}*") },
                modifier = Modifier.width(150.dp)

                )

            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                value = carbsGoal,
                onValueChange = {
                    carbsGoal = it
                },
                label = { Text("${stringResource(R.string.carbs)} (${stringResource(R.string.grams)}) - ${recommendedMacroNutrients.carbs}*") },
                modifier = Modifier.width(150.dp)

            )
//
            Spacer(modifier = Modifier.height(20.dp))
            if(errorState.isNotEmpty()) {
                Text(
                    text = errorState,
                    color = if(saveSuccess) Color.Green else Color.Red
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Text("* - ${stringResource(R.string.recommended)}")
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    saveSuccess = false
                    errorState = errorMessage

                    if(waterGoal.toIntOrNull() != null && caloriesGoal.toIntOrNull() != null && proteinGoal.toDoubleOrNull() != null && fatsGoal.toDoubleOrNull() != null && carbsGoal.toDoubleOrNull() != null) {
                        if(waterGoal.toInt() > 100 && waterGoal.toInt() < 10000 && caloriesGoal.toInt() > 10 && caloriesGoal.toInt() < 25000 && proteinGoal.toDouble() >= 0.0 && proteinGoal.toDouble() < 10000.0 && fatsGoal.toDouble() >= 0.0 && fatsGoal.toDouble() < 10000.0 && carbsGoal.toDouble() >= 0.0 && carbsGoal.toDouble() < 1000000.0) {
                            saveSuccess = true
                            errorState = saveSuccessMessage
                        }
                    }

                    if(saveSuccess) {
                        foodVM.updateSettings(
                            waterGoal.toInt(),
                            caloriesGoal.toInt(),
                            proteinGoal.toDouble(),
                            fatsGoal.toDouble(),
                            carbsGoal.toDouble()
                        )
                    } else {
                        waterGoal = foodDay.value.waterGoal.toString()
                        caloriesGoal = foodDay.value.caloriesGoal.toString()
                        proteinGoal = foodDay.value.proteinGoal.toString()
                        fatsGoal = foodDay.value.fatsGoal.toString()
                        carbsGoal = foodDay.value.carbsGoal.toString()

                    }
                },
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(stringResource(R.string.save))
            }
        } else if(chosenTab == R.string.basic_info) {
            MenuTitle(
                title = stringResource(R.string.basic_info),
                onReturnClick = {
                    chosenTab = 0
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.gender),
                    modifier = Modifier.padding(top = 10.dp)
                )
                menu1options.forEach {
                        opt ->
                    val selected = gender == opt
                    FilterChip(
                        selected = selected,
                        onClick = {
                            gender = opt
                        },
                        label = {
                            Text(stringResource(opt))
                        },
                        leadingIcon = null,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.age))
                Spacer(modifier = Modifier.width(15.dp))
                TextField(
                    value = age,
                    onValueChange = {
                        age = it
                    },
                )

            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.height))
                Spacer(modifier = Modifier.width(15.dp))
                TextField(
                    value = height,
                    onValueChange = {
                        height = it
                    },
                )

            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.weight))
                Spacer(modifier = Modifier.width(15.dp))
                TextField(
                    value = weight,
                    onValueChange = {
                        weight = it
                    },
                )

            }
            Spacer(modifier = Modifier.height(20.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.activity_level),
                    modifier = Modifier.padding(top = 10.dp)
                )
//                menu2options.forEach {
//                        opt ->
//                    val selected = activityLevel == opt
//                    FilterChip(
//                        selected = selected,
//                        onClick = {
//                            activityLevel = opt
//                        },
//                        label = {
//                            Text(stringResource(opt.displayName))
//                        },
//                        leadingIcon = null,
//                        modifier = Modifier.padding(start = 15.dp)
//                    )
//                }
                ExposedDropdownMenuBox(
                    expanded = isExpanded1,
                    onExpandedChange = { isExpanded1 = it },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(start = 15.dp)
                ) {
                    TextField(
                        value = stringResource(activityLevel.displayName),
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
                        menu2options.forEach {
                                opt ->
                            DropdownMenuItem(
                                text = {
                                    Text(stringResource(opt.displayName))
                                },
                                onClick = {
                                    activityLevel = opt
                                    isExpanded1 = false
                                }
                            )
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(15.dp))
            Text("${stringResource(activityLevel.displayName)}: ${stringResource(activityLevel.desc)}")
            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.goal),
                    modifier = Modifier.padding(top = 10.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = isExpanded2,
                    onExpandedChange = { isExpanded2 = it },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(start = 15.dp)
                ) {
                    TextField(
                        value = stringResource(fitnessGoal.displayName),
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
                        menu3options.forEach {
                                opt ->
                            DropdownMenuItem(
                                text = {
                                    Text(stringResource(opt.displayName))
                                },
                                onClick = {
                                    fitnessGoal = opt
                                    isExpanded2 = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            if(errorState2.isNotEmpty()) {
                Text(
                    text = errorState2,
                    color = if(saveSuccess2) Color.Green else Color.Red
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Button(
                onClick = {
                    saveSuccess2 = false
                    errorState2 = errorMessage

                    if(age.toIntOrNull() != null && weight.toDoubleOrNull() != null && height.toDoubleOrNull() != null) {
                        if(age.toInt() > 1 && age.toInt() < 150 && weight.toDouble() > 1 && weight.toDouble() < 1000 && height.toDouble() > 30 && height.toDouble() < 400) {
                            saveSuccess2 = true
                            errorState2 = saveSuccessMessage
                        }
                    }

                    if(saveSuccess2) {
                        profileVM.updateProfile(
                            userProfile.value.copy(
                                gender = gender,
                                age = age.toInt(),
                                weight = weight.toDouble(),
                                height = height.toDouble(),
                                activityLevel = activityLevel,
                                fitnessGoal = fitnessGoal
                            )
                        )
                    } else {
                        gender = userProfile.value.gender
                        age = userProfile.value.age.toString()
                        height = userProfile.value.height.toString()
                        weight = userProfile.value.weight.toString()
                        activityLevel = userProfile.value.activityLevel
                        fitnessGoal = userProfile.value.fitnessGoal
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}