package com.lumins.sua.views.finance.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lumins.sua.R
import com.lumins.sua.ui.theme.SUATheme
import com.lumins.sua.ui.theme.SuaColors

@Composable
fun BudgetCategoryCard(
    modifier: Modifier,
    imageResourceId: Int,
    categoryColor: Color,
    categoryTitle: String,
    categoryPercentage: Int,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.secondaryContainer else Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = null,
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 6.dp)
                .width(60.dp)
                .height(6.dp)
                .background(categoryColor, RoundedCornerShape(50.dp))
                .align(Alignment.CenterHorizontally)

        )
        Spacer(modifier = Modifier.height(6.dp))
        Image(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.CenterHorizontally)
                .padding(4.dp),
            painter = painterResource(id = imageResourceId),
            contentDescription = null,
            colorFilter = ColorFilter.tint(LocalContentColor.current)
        )

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = categoryTitle,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "$categoryPercentage%",
            fontSize = 14.sp,
            color = LocalContentColor.current.copy(0.8f)
        )

    }
}


@Preview()
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewBudgetCategoryCard() {
    SUATheme {
        Box(
            contentAlignment = Alignment.Center
        ) {
            BudgetCategoryCard(

                modifier = Modifier
                    .height(160.dp)
                    .width(136.dp)
                    .padding(start = 6.dp),
                imageResourceId = R.drawable.icon_food,
                categoryColor = SuaColors.lightGreenMisc,
                categoryTitle = "Food",
                categoryPercentage = 45
            ) {}
        }
    }
}