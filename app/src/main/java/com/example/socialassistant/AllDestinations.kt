package com.example.socialassistant

import androidx.navigation.NavHostController
import com.example.socialassistant.AllDestinations.CARD_PSU
import com.example.socialassistant.AllDestinations.CHOOSING_SERVICES_TO_TRANSFER
import com.example.socialassistant.AllDestinations.DAY_PLAN
import com.example.socialassistant.AllDestinations.SCHEDULE
import com.example.socialassistant.AllDestinations.SERVICE_RECIPIENT_SURVEY
import com.example.socialassistant.AllDestinations.MY_PSU
import com.example.socialassistant.AllDestinations.STATISTICS
import com.example.socialassistant.AllDestinations.INFORMATION
import com.example.socialassistant.AllDestinations.SETTINGS
import com.example.socialassistant.AllDestinations.EXIT

object AllDestinations {
    const val DAY_PLAN = "План дня"
    const val CARD_PSU = "Карточка ПСУ"
    const val CHOOSING_SERVICES_TO_TRANSFER = "Выбор услуг для переноса"

    const val SCHEDULE = "График"
    const val SERVICE_RECIPIENT_SURVEY = "Опрос получателя услуг"
    const val MY_PSU = "Мои ПСУ"
    const val STATISTICS = "Статистика"
    const val INFORMATION = "Справочная информация"
    const val SETTINGS = "Настройки"
    const val EXIT = "Выход"
}

class AppNavigationActions(private val navController: NavHostController) {

    fun navigateToDayPlan() {
        navController.navigate(DAY_PLAN) {
            popUpTo(DAY_PLAN)
        }
    }
    fun navigateToSchedule() {
        navController.navigate(SCHEDULE) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToServiceRecipientSurvey() {
        navController.navigate(SERVICE_RECIPIENT_SURVEY) {
            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateToMyPSU() {
        navController.navigate(MY_PSU) {
            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateToStatistics() {
        navController.navigate(STATISTICS) {
            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateToInformation() {
        navController.navigate(INFORMATION) {
            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateToSettings() {
        navController.navigate(SETTINGS) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToExit() {
        navController.navigate(EXIT) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToCardPSU() {
        navController.navigate(CARD_PSU) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToChoosingServicesToTransfer() {
        navController.navigate(CHOOSING_SERVICES_TO_TRANSFER) {
            launchSingleTop = true
            restoreState = true
        }
    }
}