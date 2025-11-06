package com.syber.ssspltd.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syber.ssspltd.utils.FontUtils.poppinsFontFamily1


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: (@Composable (() -> Unit))? = null,
    isError: Boolean = false,
    errorText: String? = null,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    backgroundColor: Color = Color(0xFFF5F5F5),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = if (value.isEmpty() && label != null) {
                { Text(text = label, fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Normal) }
            } else null,
            placeholder = if (value.isNotEmpty() && placeholder != null) {
                { Text(text = placeholder,fontSize = 12.sp, fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Normal) }
            } else null,
            leadingIcon = leadingIcon,
            isError = isError,
            singleLine = true,
            shape = shape,
            keyboardOptions = keyboardOptions,
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .clip(shape)
                .background(backgroundColor),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = backgroundColor,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary
            )
        )

        if (isError && !errorText.isNullOrEmpty()) {
            Text(
                text = errorText,
                fontSize = 10.sp,
                fontFamily = poppinsFontFamily1,fontWeight = FontWeight.Normal,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }
    }
}
