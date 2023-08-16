package com.example.socialassistant

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.socialassistant.model.CardPSU
import com.example.socialassistant.model.ContractType
import com.example.socialassistant.model.PSU
import com.example.socialassistant.model.Task

class SocialAssistantViewModel : ViewModel() {
    private val cardPSUItems = CardPSU("", PSU("", "", "", 0, ""), mutableListOf(Task("Помыть полы", 1f, ContractType.Standard, done = true, selected = false, rescheduled = false)))

    var currentCardPSU by mutableStateOf( cardPSUItems )
    var currentTask by mutableStateOf( cardPSUItems.taskList[0] )
    var selectedTasksListState by mutableStateOf( cardPSUItems.taskList )
    var doneState by mutableStateOf(false)
    var selectedState by mutableStateOf(false)
    var unselectedTasksListState by mutableStateOf( cardPSUItems.taskList )
}