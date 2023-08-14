package com.example.socialassistant.screens.dayplan

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.socialassistant.model.CardPSU
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DayPlanScreen(cardPSUItems: List<CardPSU>, currentCardPSU: MutableState<CardPSU>, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        val formatter = SimpleDateFormat("EEEE, dd MMMM", Locale("ru", "RU"))
        val title: String = formatter.format(Date())
        Text(text = title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() })
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            itemsIndexed(cardPSUItems) { _, item ->
                ListItem(item = item, currentCardPSU = currentCardPSU) { onClick() }
            }
        }
    }
}

@Composable
fun ListItem(item: CardPSU, currentCardPSU: MutableState<CardPSU>, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(7.dp))
            .clickable {
                currentCardPSU.value = item
                onClick()
            },
        colors = CardDefaults.cardColors(
            Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = item.timeInterval,
                textDecoration = TextDecoration.Underline
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "${item.psu.lastName} ${item.psu.firstName} ${item.psu.surname}",
                fontWeight = FontWeight(600)
            )
            Text(
                text = item.psu.address
            )
        }
    }
}
