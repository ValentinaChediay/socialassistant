package com.example.socialassistant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialassistant.model.User

@Composable
fun AppDrawer(
    user: User,
    route: String,
    navigateToDayPlan: () -> Unit = {},
    navigateToSchedule: () -> Unit = {},
    navigateToServiceRecipientSurvey: () -> Unit = {},
    navigateToMyPSU: () -> Unit = {},
    navigateToStatistics: () -> Unit = {},
    navigateToInformation: () -> Unit = {},
    navigateToSettings: () -> Unit = {},
    navigateToExit: () -> Unit = {},
    closeDrawer: () -> Unit = {}
) {
    ModalDrawerSheet {
        DrawerHeader(user)
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            modifier = Modifier.padding(start = 25.dp),
            text = "Личный кабинет"
        )
        Spacer(modifier = Modifier.height(10.dp))
        NavigationDrawerItem(
            label = {
                Text(
                    text = "План дня"
                )
            },
            selected = route == AllDestinations.DAY_PLAN,
            onClick = {
                navigateToDayPlan()
                closeDrawer()
            },
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.note),
                    contentDescription = null
                )
            },
        )

        NavigationDrawerItem(
            label = { Text(text = "График") },
            selected = route == AllDestinations.SCHEDULE,
            onClick = {
                navigateToSchedule()
                closeDrawer()
            },
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.note),
                    contentDescription = null
                )
            },
        )

        NavigationDrawerItem(
            label = { Text(text = "Опрос получателя услуг") },
            selected = route == AllDestinations.SERVICE_RECIPIENT_SURVEY,
            onClick = {
                navigateToServiceRecipientSurvey()
                closeDrawer()
            },
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.note),
                    contentDescription = null
                )
            },
        )
        NavigationDrawerItem(
            label = { Text(text = "Мои ПСУ") },
            selected = route == AllDestinations.MY_PSU,
            onClick = {
                navigateToMyPSU()
                closeDrawer()
            },
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.note),
                    contentDescription = null
                )
            },
        )
        NavigationDrawerItem(
            label = { Text(text = "Статистика") },
            selected = route == AllDestinations.STATISTICS,
            onClick = {
                navigateToStatistics()
                closeDrawer()
            },
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.note),
                    contentDescription = null
                )
            },
        )
        Spacer(modifier = Modifier.height(30.dp))
        NavigationDrawerItem(
            label = { Text(text = "Справочная информация") },
            selected = route == AllDestinations.INFORMATION,
            onClick = {
                navigateToInformation()
                closeDrawer()
            },
            icon = { Icon(imageVector = Icons.Default.Info, contentDescription = null) },
        )
        NavigationDrawerItem(
            label = { Text(text = "Настройки") },
            selected = route == AllDestinations.SETTINGS,
            onClick = {
                navigateToSettings()
                closeDrawer()
            },
            icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = null) },
        )
        NavigationDrawerItem(
            label = { Text(text = "Выход") },
            selected = route == AllDestinations.EXIT,
            onClick = {
                navigateToExit()
                closeDrawer()
            },
            icon = { Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null) },
        )
    }
}

@Composable
fun DrawerHeader(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = "${user.lastName} ${user.firstName}",
                style = TextStyle(fontSize = 20.sp),
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = user.surname,
                style = TextStyle(fontSize = 20.sp),
            )
        }
        IconButton(onClick = {
            // TODO: доделать при нажатии на колокольчик
        }) {
            Icon(
                painter = painterResource(id = R.drawable.notifications),
                contentDescription = "im4",
            )
        }
    }
}


