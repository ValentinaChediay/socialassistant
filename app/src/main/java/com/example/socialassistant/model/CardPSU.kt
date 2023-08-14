package com.example.socialassistant.model

data class CardPSU(
    val timeInterval: String,
    val psu: PSU,
    val taskList: List<Task>
)
