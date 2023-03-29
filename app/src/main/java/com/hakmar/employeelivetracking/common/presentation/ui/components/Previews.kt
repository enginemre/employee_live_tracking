package com.hakmar.employeelivetracking.common.presentation.ui.components

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f
)
@Preview(
    name = "normal font",
    group = "font scales",
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f
)
annotation class FontScalePreviews


@Preview(name = "Pixel XL", group = "Devices", device = Devices.PIXEL, showSystemUi = true)
@Preview(name = "Pixel XL", group = "Devices", device = Devices.PIXEL_XL, showSystemUi = true)
@Preview(name = "Pixel XL", group = "Devices", device = Devices.PIXEL_4_XL, showSystemUi = true)
@Preview(name = "Pixel XL", group = "Devices", device = Devices.PIXEL_C, showSystemUi = true)
annotation class DevicePreviews
