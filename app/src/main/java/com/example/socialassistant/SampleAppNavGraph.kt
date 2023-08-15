package com.example.socialassistant

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.socialassistant.model.CardPSU
import com.example.socialassistant.model.ContractType
import com.example.socialassistant.model.PSU
import com.example.socialassistant.model.Task
import com.example.socialassistant.model.User
import com.example.socialassistant.screens.dayplan.CardPSUScreen
import com.example.socialassistant.screens.dayplan.DayPlanScreen
import com.example.socialassistant.screens.ExitScreen
import com.example.socialassistant.screens.InformationScreen
import com.example.socialassistant.screens.MyPSUScreen
import com.example.socialassistant.screens.ScheduleScreen
import com.example.socialassistant.screens.ServiceRecipientSurveyScreen
import com.example.socialassistant.screens.SettingsScreen
import com.example.socialassistant.screens.StatisticsScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleAppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: AllDestinations.DAY_PLAN
    val navigationActions = remember(navController) {
        AppNavigationActions(navController)
    }

    val cardPSUItems = listOf(
        CardPSU(
            "09:00 - 10:00",
            PSU("Петрова", "Инна", "Владимировна", 80, "г.Оренбург ул. Лесная д.2 кв.8"),
            mutableListOf(
                Task("Помыть полы", 1f, ContractType.Standard, done = true, additionalTask = false),
                Task("Почитать", 5f, ContractType.Commercial, done = false, additionalTask = false),
                Task("Измерить температуру", 5f, ContractType.Standard, done = false, additionalTask = false),
                Task("Измерить давление", 5f, ContractType.Standard, done = false, additionalTask = false),
                Task("Погулять", 5f, ContractType.AboveStandard, done = false, additionalTask = false),
                Task("Покормить домашних животных", 1f, ContractType.Commercial, done = false, additionalTask = true),
                Task("Вынести мусор", 5f, ContractType.Standard, done = false, additionalTask = true)
            )
        ),
        CardPSU(
            "11:00 - 11:30",
            PSU("Семенов", "Иван", "Андреевич", 82, "г.Оренбург ул. Солнечная д.5 кв.82"),
            mutableListOf(
                Task("Измерить температуру", 5f, ContractType.Standard, done = false, additionalTask = false),
                Task("Измерить давление", 5f, ContractType.Standard, done = false, additionalTask = false),
                Task("Погулять", 5f, ContractType.AboveStandard, done = true, additionalTask = false),
                Task("Покормить собаку", 1f, ContractType.Commercial, done = false, additionalTask = true),
                Task("Вынести мусор", 5f, ContractType.Standard, done = false, additionalTask = true)
            )
        ),
        CardPSU(
            "15:00 - 16:30",
            PSU("Филиппов", "Антон", "Валерьевич", 71, "г.Оренбург ул. Орская д.10 кв.17"),
            mutableListOf(
                Task("Почитать", 5f, ContractType.Commercial, done = false, additionalTask = false),
                Task("Погулять", 5f, ContractType.AboveStandard, done = true, additionalTask = false),
                Task("Сводить к врачу", 1f, ContractType.Commercial, done = false, additionalTask = true),
                Task("Сводить на танцы", 5f, ContractType.Standard, done = false, additionalTask = true)
            )
        )
    )

    val currentCardPSU = remember {
        mutableStateOf(
            cardPSUItems[0]
        )
    }

    val currentTask = remember {
        mutableStateOf(
            cardPSUItems[0].taskList[0]
        )
    }

    val taskListState = remember {
        mutableStateOf(
            cardPSUItems[0].taskList
        )
    }

    val checkedState = remember {
        mutableStateOf(false)
    }

    val checkedStateAdditionalTask = remember {
        mutableStateOf(false)
    }

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                user = User("Фамилия", "Имя", "Отчество"),
                route = currentRoute,
                navigateToDayPlan = { navigationActions.navigateToDayPlan() },
                navigateToSchedule = { navigationActions.navigateToSchedule() },
                navigateToServiceRecipientSurvey = { navigationActions.navigateToServiceRecipientSurvey() },
                navigateToMyPSU = { navigationActions.navigateToMyPSU() },
                navigateToStatistics = { navigationActions.navigateToStatistics() },
                navigateToInformation = { navigationActions.navigateToInformation() },
                navigateToSettings = { navigationActions.navigateToSettings() },
                navigateToExit = { navigationActions.navigateToExit() },
            ) { coroutineScope.launch { drawerState.close() } }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = currentRoute) },
                    modifier = Modifier.fillMaxWidth(),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }, content = {
                            Icon(
                                imageVector = Icons.Default.Menu, contentDescription = null
                            )
                        })
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                )
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = AllDestinations.DAY_PLAN,
                modifier = modifier.padding(it)
            ) {
                composable(route = AllDestinations.DAY_PLAN) {
                    DayPlanScreen(cardPSUItems, currentCardPSU) {
                        navController.navigate(AllDestinations.CARD_PSU)
                    }
                }
                composable(route = AllDestinations.SCHEDULE) {
                    ScheduleScreen()
                }
                composable(route = AllDestinations.SERVICE_RECIPIENT_SURVEY) {
                    ServiceRecipientSurveyScreen()
                }
                composable(route = AllDestinations.MY_PSU) {
                    MyPSUScreen()
                }
                composable(route = AllDestinations.STATISTICS) {
                    StatisticsScreen()
                }
                composable(route = AllDestinations.INFORMATION) {
                    InformationScreen()
                }
                composable(route = AllDestinations.SETTINGS) {
                    SettingsScreen()
                }
                composable(route = AllDestinations.EXIT) {
                    ExitScreen()
                }
                composable(route = AllDestinations.CARD_PSU) {
                    CardPSUScreen(
                        currentCardPSU,
                        currentTask,
                        taskListState,
                        checkedState,
                        { navController.navigate(AllDestinations.DAY_PLAN) },
                        {},
                        {})
                }
            }
        }
    }


}