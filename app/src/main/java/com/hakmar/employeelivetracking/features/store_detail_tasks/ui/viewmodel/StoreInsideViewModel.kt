package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.common.presentation.base.BaseViewModel
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.model.CheckItem
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.event.StoreInsideEvent
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.state.StoreInsideState
import com.hakmar.employeelivetracking.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreInsideViewModel @Inject constructor(

) : BaseViewModel<StoreInsideEvent>() {

    private val _state = MutableStateFlow(StoreInsideState())
    val state = _state.asStateFlow()

    private val _checkList = mutableStateListOf<CheckItem>(

    )
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


    override fun onEvent(event: StoreInsideEvent) {
        when (event) {
            StoreInsideEvent.CompleteCheck -> {
                completeCheck()
            }

            is StoreInsideEvent.OnChecked -> {
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