package com.hakmar.employeelivetracking.features.profile.ui.model

data class ProfileItemModel(
    val name: Int,
    val icon: Int,
    val destination: String,
    var onClick: (() -> Unit)? = null
)