package com.syber.ssspltd.ui.view.saleReport

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.syber.ssspltd.R
import com.syber.ssspltd.data.model.saleReport.SaleReportSecondaryData

@Composable
 fun PurchaseRow(
    purchase: SaleReportSecondaryData,
    context: Context
) {
    Row {
        TableCell(purchase.PurchaseNo, 80.dp, color = Color.Red)
        TableCell(purchase.Supplier, 105.dp)
        TableCell(purchase.Pcs, 40.dp)
        TableCell(purchase.PAmount, 65.dp)

        Box(
            modifier = Modifier
                .width(53.dp)
                .height(40.dp)
                .border(0.5.dp, Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            if (!purchase.PackingSlipPath.isNullOrEmpty()) {
                Image(
                    painter = painterResource(R.drawable.pdf),
                    contentDescription = "Packing Slip",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            context.startActivity(
                                Intent(Intent.ACTION_VIEW, Uri.parse(purchase.PackingSlipPath))
                            )
                        }
                )
            }
        }
    }
}
@Composable
fun PurchaseRow1(
    context: Context
) {
    Row {
        TableCell1("PurchaseNo", 80.dp)
        TableCell1("Supplier", 105.dp)
        TableCell1("Pcs", 40.dp)
        TableCell1("Amount", 65.dp)
        TableCell1("PSlip", 53.dp)

    }
}

@Composable
fun TableCell(
    text: String,
    width: Dp,
    color: Color = Color.Black,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    background: Color = Color.Transparent
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(40.dp) // fixed height for all rows
            .border(0.5.dp, Color.Gray)
            .background(background)
            .padding(horizontal = 4.dp, vertical = 8.dp), // reduced padding
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = color,
            style = style,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun TableCell1(
    text: String,
    width: Dp,
    color: Color = Color.Black,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    background: Color = Color.Transparent
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(30.dp) // fixed height for all rows
            .border(0.5.dp, Color.Gray)
            .background(background)
            .padding(horizontal = 4.dp, vertical = 8.dp), // reduced padding
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = color,
            style = style,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}





