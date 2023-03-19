package com.hakmar.employeelivetracking.util

object AppConstants {

    const val DATASTORE_NAME = "employee_datastore"
    const val ACTION_GENERAL_SHIFT_TIME_START = "start_general_shift_time"
    const val ACTION_GENERAL_SHIFT_TIME_STOP = "stop_general_shift_time"
    const val ACTION_GENERAL_SHIFT_TIME_CANCEL = "cancel_general_shift_time"
    const val TIMER_STATE = "timer_state"

    const val IS_LOGIN = "is_login"
    const val LAST_KNOWN_LOCATION_LAT = "last_known_location_lat"
    const val LAST_KNOWN_LOCATION_LON = "last_known_locaiton_lon"

    const val NOTIFICATION_CHANNEL_ID = "notification_channel_id"
    const val NOTIFICATION_CHANNEL_NAME = "general_shift_time_service"
    const val NOTIFICATION_ID = 10

    const val CLICK_REQUEST_CODE = 100
    const val CANCEL_REQUEST_CODE = 101
    const val STOP_REQUEST_CODE = 102
    const val RESUME_REQUEST_CODE = 103

    const val LAST_ROUTE = "last_route"
    const val IS_ON_PAUSED = ""
}