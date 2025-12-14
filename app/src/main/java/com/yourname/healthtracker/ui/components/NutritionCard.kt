package com.yourname.healthtracker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yourname.healthtracker.R
import com.yourname.healthtracker.data.Food
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.sp
import com.yourname.healthtracker.ui.theme.FoodColor
import kotlin.math.roundToInt

@Composable
fun NutritionCard(food: Food, amount: Int = 100) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
         Column(
             modifier = Modifier
                 .padding(20.dp)
         ) {
             ProductHeader(stringResource(food.name), "$amount ${stringResource(R.string.grams)}")

             Spacer(modifier = Modifier.height(24.dp))

             CaloriesSection(calories = if(amount >= 100) (food.calories * (amount.toDouble()/100)).toInt() else (food.calories / (100/amount.toDouble())).toInt())

             Spacer(modifier = Modifier.height(20.dp))

             MacrosSection(
                 protein = if(amount >= 100) ((food.protein * (amount.toDouble()/100)) * 10).roundToInt() / 10.0 else ((food.protein / (100/amount.toDouble())) * 10).roundToInt() / 10.0,
                 fats = if(amount >= 100) ((food.fat * (amount.toDouble()/100)) * 10).roundToInt() / 10.0 else ((food.fat / (100/amount.toDouble())) * 10).roundToInt() / 10.0,
                 carbs = if(amount >= 100) ((food.carbs * (amount.toDouble()/100)) * 10).roundToInt() / 10.0 else ((food.carbs / (100/amount.toDouble())) * 10).roundToInt() / 10.0
             )

             Spacer(modifier = Modifier.height(16.dp))

             NutritionProgressSection(
                 protein = if(amount >= 100) ((food.protein * (amount/100)) * 10).roundToInt() / 10.0 else ((food.protein / (100/amount)) * 10).roundToInt() / 10.0,
                 fats = if(amount >= 100) ((food.fat * (amount/100)) * 10).roundToInt() / 10.0 else ((food.fat / (100/amount)) * 10).roundToInt() / 10.0,
                 carbs = if(amount >= 100) ((food.carbs * (amount/100)) * 10).roundToInt() / 10.0 else ((food.carbs / (100/amount)) * 10).roundToInt() / 10.0
             )
         }
    }
}

@Composable
private fun ProductHeader(productName: String, portionSize: String) {
    Column {
        Text(
            text = productName,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "на $portionSize",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CaloriesSection(calories: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.nut_value),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$calories",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.kcal),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MacrosSection(protein: Double, fats: Double, carbs: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        NutrientItem(
            value = protein,
            label = stringResource(R.string.protein),
            unit = stringResource(R.string.grams),
            color = MaterialTheme.colorScheme.primary
        )
        NutrientItem(
            value = fats,
            label = stringResource(R.string.fats),
            unit = stringResource(R.string.grams),
            color = MaterialTheme.colorScheme.secondary
        )
        NutrientItem(
            value = carbs,
            label = stringResource(R.string.carbs),
            unit = stringResource(R.string.grams),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
private fun NutrientItem(value: Double, label: String, unit: String, color: androidx.compose.ui.graphics.Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = color,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = unit,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun NutritionProgressSection(protein: Double, fats: Double, carbs: Double) {
    val total = protein + fats + carbs
    if (total > 0) {
        Column {
            Text(
                text = stringResource(R.string.mn_balance),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(120.dp)
//                        .align(Alignment.CenterHorizontally)
                ) {
//                CircularProgressIndicator(
//                    progress = .8f,
//                    color = MaterialTheme.colorScheme.surfaceVariant,
//                    strokeWidth = 8.dp,
//                    modifier = Modifier.size(120.dp)
//                )

//                MacroSegments(protein, fats, carbs, total)

                    val allValues = protein+fats+carbs
                    val biggestValue = listOf((protein/allValues*100).toInt(),(fats/allValues*100).toInt(),(carbs/allValues*100).toInt()).max()
                    var bvName = ""

                    when(biggestValue) {
                        (protein/allValues*100).toInt() -> bvName = stringResource(R.string.protein)
                        (fats/allValues*100).toInt() -> bvName = stringResource(R.string.fats)
                        (carbs/allValues*100).toInt() -> bvName = stringResource(R.string.carbs)
                    }

                    ThreeColorCircularProgressIndicator(
                        modifier = Modifier.size(120.dp),
                        percent1 = (protein/allValues).toFloat(),
                        percent2 = (fats/allValues).toFloat(),
                        percent3 = (carbs/allValues).toFloat(),
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$biggestValue% $bvName",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                MacroLegend(protein, fats, carbs, total)
            }


        }
    }
}

@Composable
private fun MacroSegments(protein: Double, fats: Double, carbs: Double, total: Double) {
    Canvas(modifier = Modifier.size(120.dp)) {
        var startAngle = -90f

        drawArc(
            color = FoodColor,
            startAngle = startAngle,
            sweepAngle = (protein / total * 360).toFloat(),
            useCenter = false,
            style = Stroke(8.dp.toPx(), cap = StrokeCap.Round)
        )
        startAngle += (protein / total * 360).toFloat()

        drawArc(
            color = FoodColor,
            startAngle = startAngle,
            sweepAngle = (fats / total * 360).toFloat(),
            useCenter = false,
            style = Stroke(8.dp.toPx(), cap = StrokeCap.Round)
        )
        startAngle += (fats / total * 360).toFloat()

        drawArc(
            color = FoodColor,
            startAngle = startAngle,
            sweepAngle = (carbs / total * 360).toFloat(),
            useCenter = false,
            style = Stroke(8.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun MacroLegend(protein: Double, fats: Double, carbs: Double, total: Double) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LegendItem(
            color = Color.Blue,
            label = stringResource(R.string.protein),
            value = protein,
            percentage = (protein / total * 100).toInt()
        )
        LegendItem(
            color = Color.Yellow,
            label = stringResource(R.string.fats),
            value = fats,
            percentage = (fats / total * 100).toInt()
        )
        LegendItem(
            color = Color.Green,
            label = stringResource(R.string.carbs),
            value = carbs,
            percentage = (carbs / total * 100).toInt()
        )
    }
}

@Composable
private fun LegendItem(color: androidx.compose.ui.graphics.Color, label: String, value: Double, percentage: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
            Text(
                text = "${percentage}%",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )
        }
    }
}
