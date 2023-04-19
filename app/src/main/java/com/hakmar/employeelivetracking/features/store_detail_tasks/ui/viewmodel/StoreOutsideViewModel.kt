package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.model.CheckItem
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.StoreOutsideEvent
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.state.StoreOutsideState
import com.hakmar.employeelivetracking.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreOutsideViewModel @Inject constructor(

) : BaseViewModel<StoreOutsideEvent>() {

    private val _state = MutableStateFlow(StoreOutsideState())
    val state = _state.asStateFlow()

    private val _checkList = mutableStateListOf<CheckItem>()
    val checkList: List<CheckItem> = _checkList


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            _checkList.addAll(
                listOf(
                    CheckItem(
                        description = "sdfmswdklmflsadkmfklamsdflkasdfkdfsamkasdklfaskdmfadsf",
                        id = 1,
                        completed = false,
                        type = "Claim requested credentials"
                    ),
                    CheckItem(
                        description = "sdfmswdklmflsadkmfklamsdflkasdfkdfsamkasdklfaskdmfadsf",
                        id = 2,
                        completed = false,
                        type = "Claim requested credentials"
                    ),
                    CheckItem(
                        description = "sdfmswdklmflsadkmfklamsdflkasdfkdfsamkasdklfaskdmfadsf",
                        id = 3,
                        completed = false,
                        type = "Claim received credentials"
                    ),
                    CheckItem(
                        description = "sdfmswdklmflsadkmfklamsdflkasdfkdfsamkasdklfaskdmfadsf",
                        id = 4,
                        completed = false,
                        type = "Pending Requests"
                    ),
                    CheckItem(
                        description = "sdfmswdklmflsadkmfklamsdflkasdfkdfsamkasdklfaskdmfadsf",
                        id = 5,
                        completed = false,
                        type = "Pending Requests"
                    ),
                )
            )
        }

    }


    override fun onEvent(event: StoreOutsideEvent) {
        when (event) {
            StoreOutsideEvent.CompleteCheck -> {
                completeCheck()
            }

            is StoreOutsideEvent.OnChecked -> {
                changeStatus(event.checkItem)
            }
        }
    }

    private fun changeStatus(checkItem: CheckItem) {
        val copyList = checkList.toMutableList()
        val index = copyList.indexOf(checkItem)
        copyList[index] = copyList[index].copy(
            completed = !checkItem.completed
        )
        _checkList.clear()
        _checkList.addAll(copyList)
    }

    private fun completeCheck() {

    }
}