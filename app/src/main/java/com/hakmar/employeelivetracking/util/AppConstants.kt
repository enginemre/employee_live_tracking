package com.hakmar.employeelivetracking.util

object AppConstants {


    const val BASE_URL = "https://ruhidibadli.pythonanywhere.com"
    const val TIMEOUT_SHORT = 5L
    const val TIMEOUT_LONG = 30L
    const val TIMEOUT_VERY_LONG = 50L
    const val DATASTORE_NAME = "employee_datastore"
    const val ACTION_GENERAL_SHIFT_TIME_START = "start_general_shift_time"
    const val ACTION_GENERAL_SHIFT_TIME_STOP = "stop_general_shift_time"
    const val ACTION_GENERAL_SHIFT_TIME_CANCEL = "cancel_general_shift_time"
    const val ACTION_STORE_SHIFT_TIME_START = "start_store_shift_time"
    const val ACTION_STORE_SHIFT_TIME_STOP = "stop_store_shift_time"
    const val ACTION_STORE_SHIFT_TIME_CANCEL = "cancel_store_shift_time"
    const val ACTION_OBSERVE_GENERAL_SHIFT = "observe_general_shift"
    const val ACTION_OBSERVE_STORE_SHIFT = "observe_store_shift"
    const val TIME_ELAPSED = "time_elapsed"
    const val NFC_DATA= "nfc_data"
    const val STORE_INFO = "store_info"
    const val LAST_TIME = "last_timer"
    const val TIMER_STATE = "timer_state"

    const val IS_LOGIN = "is_login"
    const val IS_FIRST = "is_first"
    const val LAST_KNOWN_LOCATION_LAT = "last_known_location_lat"
    const val LAST_KNOWN_LOCATION_LON = "last_known_locaiton_lon"
    const val FIREBASE_TOKEN = "fcm_token"

    const val NOTIFICATION_CHANNEL_ID_GENERAL_SHIFT = "notification_channel_id_general"
    const val NOTIFICATION_CHANNEL_ID_STORE_SHIFT = "notification_channel_id_general"
    const val NOTIFICATION_CHANNEL_ID_APP = "notification_channel_id_app"
    const val NOTIFICATION_CHANNEL_NAME_GENERAL = "general_shift_time_service"
    const val NOTIFICATION_CHANNEL_NAME_STORE = "store_shift_time_service"
    const val NOTIFICATION_CHANNEL_NAME_APP = "app_notification"
    const val NOTIFICATION_ID_GENERAL = 10
    const val NOTIFICATION_ID_STORE = 11
    const val NOTIFICATION_ID_APP = 12

    const val GENERAL_SHIFT_CLICK_REQUEST_CODE = 100
    const val STORE_SHIFT_CLICK_REQUEST_CODE = 101

    const val LAST_ROUTE = "last_route"
    const val IS_ON_PAUSED = ""
}