package com.example.socialassistant.screens.dayplan

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.socialassistant.model.CardPSU
import com.example.socialassistant.model.ContractType
import com.example.socialassistant.model.Task
import com.example.socialassistant.ui.theme.Green
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CardPSUScreen(
    currentCardPSU: MutableState<CardPSU>,
    currentTask: MutableState<Task>,
    taskListState: MutableState<List<Task>>,
    checkedState: MutableState<Boolean>,
    onClickArrowBack: () -> Unit,
    onClickAddCircle: () -> Unit,
    onClickCheckCircle: () -> Unit
) {
    Column {
        Header(
            currentCardPSU,
            onClickArrowBack = {
                onClickArrowBack()
            },
            onClickAddCircle = {
                onClickAddCircle()
            },
            onClickCheckCircle = {
                onClickCheckCircle()
            })
        TabLayout(currentCardPSU, currentTask, taskListState, checkedState)
    }
}

@Composable
fun Header(
    currentCardPSU: MutableState<CardPSU>,
    onClickArrowBack: () -> Unit,
    onClickAddCircle: () -> Unit,
    onClickCheckCircle: () -> Unit
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    onClickAddCircle.invoke()
                }, content = {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                })
                IconButton(onClick = {
                    onClickCheckCircle.invoke()
                }, content = {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                })
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
                text = currentCardPSU.value.timeInterval,
                textDecoration = TextDecoration.Underline
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "${currentCardPSU.value.psu.lastName} ${currentCardPSU.value.psu.firstName} ${currentCardPSU.value.psu.surname}",
                fontWeight = FontWeight(600)
            )
            Text(
                text = currentCardPSU.value.psu.address
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayout(
    currentCardPSU: MutableState<CardPSU>,
    currentTask: MutableState<Task>,
    taskListState: MutableState<List<Task>>,
    checkedState: MutableState<Boolean>,
) {
    val tabList = ContractType.values().map { it.type }
    val pagerState = rememberPagerState { tabList.size }
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(text = text)
                    },
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1.0f),
        ) { index ->
            val allTasksList = currentCardPSU.value.taskList
            taskListState.value = when (index) {
                0 -> allTasksList.filter { n -> n.contract == ContractType.Standard }
                    .sortedBy { it.done }

                1 -> allTasksList.filter { n -> n.contract == ContractType.AboveStandard }
                    .sortedBy { it.done }

                else -> allTasksList.filter { n -> n.contract == ContractType.Commercial }
                    .sortedBy { it.done }
            }

            TaskList(taskListState, checkedState, currentCardPSU = currentCardPSU, currentTask)
        }
    }
}

@Composable
fun TaskList(
    taskListState: MutableState<List<Task>>,
    checkedState: MutableState<Boolean>,
    currentCardPSU: MutableState<CardPSU>,
    currentTask: MutableState<Task>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            taskListState.value
        ) { index, item ->
            TaskItem(item, index, taskListState, checkedState, currentTask, {})
        }
    }
}

@Composable
fun TaskItem(
    item: Task,
    index: Int,
    taskListState: MutableState<List<Task>>,
    checkedState: MutableState<Boolean>,
    currentTask: MutableState<Task>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(7.dp))
            .clickable {
                //currentTask.value = item
                //onClick()
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        println("LongPress")
                    }
                )
            },
        colors = CardDefaults.cardColors(
            Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "${index + 1}. ${item.taskItself}",
                    color = if (item.done) Green else Color.Red,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = "${item.price} р/мес",
                    )
                    Text(
                        modifier = Modifier,
                        text = item.contract.type,
                    )
                    Checkbox(
                        checked = item.done,
                        onCheckedChange = {
                            checkedState.value = it
                            val newList = taskListState.value
                            newList[index].done = checkedState.value
                            taskListState.value = newList.sortedBy { a -> a.done }
                        }
                    )
                }
            }
        }
    }
}

