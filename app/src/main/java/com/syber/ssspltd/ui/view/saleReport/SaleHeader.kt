package com.syber.ssspltd.ui.view.saleReport

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.syber.ssspltd.data.model.saleReport.SaleReportResult

@Composable
fun SaleHeader(sale: SaleReportResult) {

    val valueStyle = MaterialTheme.typography.bodySmall
    val highlight = Color.Red

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Bill No : ")
                }
                withStyle(SpanStyle(color = highlight)) {
                    append(sale.BillNo ?: "-")
                }
            })

            Text(text = sale.SRNO ?: "-")
        }

        Text("Bilty No : ${sale.BiltyNo ?: "-"}", style = valueStyle)
        Text("Sub Party : ${sale.SubParty ?: "-"}", style = valueStyle)
        Text("Transport : ${sale.Transport ?: "-"}", style = valueStyle)
        Text("Net Amount : ${sale.SAmount ?: "-"}", style = valueStyle)
    }
}


