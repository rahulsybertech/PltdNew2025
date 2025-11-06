package com.syber.ssspltd.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syber.ssspltd.ui.theme.ThemeColors
import com.syber.ssspltd.utils.FontUtils.poppinsFontFamily1

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    themeColors:ThemeColors
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(28.dp)) // Rounded corners
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(MyConstant.primaryCOLUR), // light pink
                        Color(0xFFFF4B2B)  // deep red
                    )
                )
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
    }
}


