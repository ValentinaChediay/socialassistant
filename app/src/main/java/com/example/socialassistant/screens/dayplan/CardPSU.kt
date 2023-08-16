package com.example.socialassistant.screens.dayplan

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialassistant.SocialAssistantViewModel
import com.example.socialassistant.model.ContractType
import com.example.socialassistant.model.Task
import com.example.socialassistant.ui.theme.Green
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardPSUScreen(
    viewModel: SocialAssistantViewModel,
    onClickArrowBack: () -> Unit,
    onClickCheckCircle: () -> Unit,
    onLongPress: () -> Unit
) {
    val tabList = ContractType.values().map { it.type }
    val pagerState = rememberPagerState { tabList.size }
    Column {
        Header(
            pagerState,
            viewModel,
            onClickArrowBack = {
                onClickArrowBack()
            },
            onClickCheckCircle = {
                onClickCheckCircle()
            })
        TabLayout(tabList, pagerState, viewModel) { onLongPress() }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Header(
    pagerState: PagerState,
    viewModel: SocialAssistantViewModel,
    onClickArrowBack: () -> Unit,
    onClickCheckCircle: () -> Unit
) {
    var openBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

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
                    openBottomSheet = true
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
    if (openBottomSheet) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = {
                for (i in viewModel.unselectedTasksListState) {
                    i.selected = false
                }
                openBottomSheet = false
            },
            dragHandle = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(text = "Выберите задачи", fontWeight = FontWeight(600))
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider()
                }
            }
        ) {
            BottomSheetContent(
                viewModel = viewModel,
                onHideButtonClick = {
                    scope.launch {
                        // логика, чтоб таб текущий таб обновился сразу после кнопки Сохранить, а не после переключения
                        val currentType = when (pagerState.currentPage) {
                            0 -> ContractType.Standard
                            1 -> ContractType.AboveStandard
                            else -> ContractType.Commercial
                        }
                        val newList = mutableListOf<Task>()
                        for (i in viewModel.selectedTasksListState) {
                            newList.add(i)
                        }
                        for (i in viewModel.unselectedTasksListState) {
                            if (i.selected && (i.contract == currentType)) {
                                newList.add(i)
                            }
                        }
                        viewModel.selectedTasksListState = newList.sortedBy { n -> n.done }
                        bottomSheetState.hide()
                    }.invokeOnCompletion {
                        if (!bottomSheetState.isVisible) openBottomSheet = false
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayout(
    tabList: List<String>,
    pagerState: PagerState,
    viewModel: SocialAssistantViewModel,
    onLongPress: () -> Unit
) {
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    val allSelectedTasksList: List<Task> =
        viewModel.currentCardPSU.taskList.filter { n -> n.selected }.filter { n -> !n.rescheduled }

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
                        Text(text = text, fontSize = 11.sp)
                    },
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1.0f),
        ) {
            viewModel.selectedTasksListState = when (pagerState.currentPage) {
                0 -> allSelectedTasksList.filter { n -> n.contract == ContractType.Standard }
                    .sortedBy { it.done }

                1 -> allSelectedTasksList.filter { n -> n.contract == ContractType.AboveStandard }
                    .sortedBy { it.done }

                else -> allSelectedTasksList.filter { n -> n.contract == ContractType.Commercial }
                    .sortedBy { it.done }
            }
        }
        TaskList(viewModel) { onLongPress() }
    }
}

@Composable
fun TaskList(
    viewModel: SocialAssistantViewModel,
    onLongPress: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            viewModel.selectedTasksListState
        ) { index, item ->
            TaskItem(item, index, viewModel) { onLongPress() }
        }
    }
}

@Composable
fun TaskItem(
    item: Task,
    index: Int,
    viewModel: SocialAssistantViewModel,
    onLongPress: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(7.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        //item.rescheduled = true
                        onLongPress()
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
                    viewModel.doneState = item.done
                    Checkbox(
                        checked = viewModel.doneState,
                        onCheckedChange = {
                            viewModel.doneState = it
                            val newList = viewModel.selectedTasksListState
                            newList[index].done = viewModel.doneState
                            viewModel.selectedTasksListState = newList.sortedBy { a -> a.done }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    viewModel: SocialAssistantViewModel,
    onHideButtonClick: () -> Unit
) {
    viewModel.unselectedTasksListState =
        viewModel.currentCardPSU.taskList.filter { n -> !n.selected }
    if (viewModel.unselectedTasksListState.isEmpty()) {
        Text(
            text = "Список не выбранных задач пуст", modifier = Modifier
                .padding(top = 30.dp, bottom = 30.dp)
                .fillMaxWidth(), textAlign = TextAlign.Center
        )
    } else {
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            itemsIndexed(
                viewModel.unselectedTasksListState
            ) { index, item ->
                AdditionalTaskItem(item, index, viewModel)
            }
            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onHideButtonClick()
                    }) {
                    Text("Сохранить")
                }
            }
        }
    }
}

@Composable
fun AdditionalTaskItem(item: Task, index: Int, viewModel: SocialAssistantViewModel) {
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
                text = "${index + 1}. ${item.taskItself}"
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
                viewModel.selectedState = item.selected
                Checkbox(
                    checked = viewModel.selectedState,
                    onCheckedChange = {
                        viewModel.selectedState = it
                        val newList = viewModel.unselectedTasksListState
                        newList[index].selected = viewModel.selectedState
                        viewModel.unselectedTasksListState = newList
                    }
                )
            }
        }
    }
}

