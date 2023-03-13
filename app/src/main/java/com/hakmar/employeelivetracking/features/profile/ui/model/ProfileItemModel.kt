package com.hakmar.employeelivetracking.features.profile.ui.model

data class ProfileItemModel(
    var name: String,
    var icon: Int,
    var onClick: () -> Unit
)