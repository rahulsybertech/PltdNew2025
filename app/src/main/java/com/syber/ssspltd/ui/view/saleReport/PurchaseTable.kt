package com.syber.ssspltd.ui.view.saleReport

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
fun PurchaseTable(purchases: List<SaleReportSecondaryData>?) {
    val context = LocalContext.current
    val headerStyle = MaterialTheme.typography.labelMedium.copy(
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .horizontalScroll(rememberScrollState()) // for overflow
    ) {
        // Header Row
        Row(Modifier.background(Color(0xFFE0E0E0))) {
            TableCell("Purchase No", width = 80.dp, style = headerStyle, background = Color(0xFFE0E0E0))
            TableCell("Supplier", width = 105.dp, style = headerStyle, background = Color(0xFFE0E0E0))
            TableCell("Pcs", width = 40.dp, style = headerStyle, background = Color(0xFFE0E0E0))
            TableCell("Amount", width = 65.dp, style = headerStyle, background = Color(0xFFE0E0E0))
            TableCell("P.slip", width = 53.dp, style = headerStyle, background = Color(0xFFE0E0E0))
        }

        // Data Rows
        purchases?.forEach { purchase ->
            Row {
                TableCell(purchase.PurchaseNo, width = 80.dp, color = Color.Red)
                TableCell(purchase.Supplier, width = 105.dp)
                TableCell(purchase.Pcs.toString(), width = 40.dp)
                TableCell(purchase.PurchaseNo.toString(), width = 65.dp) // Fixed column
                // P.slip column
                Box(
                    modifier = Modifier
                        .width(53.dp)
                        .height(40.dp)
                        .border(0.5.dp, Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    if (!purchase.PackingSlipPath.isNullOrEmpty()) {
                        Image(
                            painter = painterResource(id = R.drawable.pdf), // your PNG/JPG drawable
                            contentDescription = "Packing Slip",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    val intent = Intent(Intent.ACTION_VIEW).apply {
                                        data = Uri.parse(purchase.PackingSlipPath)
                                    }
                                    context.startActivity(intent)
                                }
                        )
                    }
                }
            }
        }
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





