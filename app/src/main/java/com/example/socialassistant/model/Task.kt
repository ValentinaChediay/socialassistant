package com.example.socialassistant.model

data class Task(
    val taskItself: String,
    val price: Float,
    val contract: ContractType,
    var done: Boolean,
    var selected: Boolean,
    var rescheduled: Boolean
)
