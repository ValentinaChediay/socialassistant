package com.example.socialassistant.screens.dayplan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.socialassistant.SocialAssistantViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChoosingServicesToTransfer(
    viewModel: SocialAssistantViewModel,
    onClickArrowBack: () -> Unit
) {
    Header(viewModel = viewModel, onClickArrowBack = { onClickArrowBack()})
}


@Composable
fun Header(
    viewModel: SocialAssistantViewModel,
    onClickArrowBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                onClickArrowBack.invoke()
            }, content = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            })
            Button(
                onClick = {

                }) {
                Text("Перенести")
            }
        }

        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            val formatter = SimpleDateFormat("EEEE, dd MMMM", Locale("ru", "RU"))
            val title: String = formatter.format(Date())
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = viewModel.currentCardPSU.timeInterval,
                textDecoration = TextDecoration.Underline
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "${viewModel.currentCardPSU.psu.lastName} ${viewModel.currentCardPSU.psu.firstName} ${viewModel.currentCardPSU.psu.surname}",
                fontWeight = FontWeight(600)
            )
            Text(
                text = viewModel.currentCardPSU.psu.address
            )
        }
    }
}