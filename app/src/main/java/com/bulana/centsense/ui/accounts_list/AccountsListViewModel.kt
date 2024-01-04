package com.bulana.centsense.ui.accounts_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulana.centsense.data.Account
import com.bulana.centsense.data.AccountRepository
import com.bulana.centsense.util.Routes
import com.bulana.centsense.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountsListViewModel @Inject constructor(
    private val repository: AccountRepository
) : ViewModel() {

    val accountsList = repository.getAllAccounts()

    private val _uiEvent = Channel<UiEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()

    private var deleteAccount: Account? = null

    fun onEvent(event: AccountEvent) {

        when (event) {

            is AccountEvent.OnAccountClick -> {
                sendUiEvent(event = UiEvent.Navigate(Routes.ADD_EDIT_ACCOUNT + "?accountId=${event.account.accountNumber}"))
            }

            is AccountEvent.OnAddAccountClick -> {
                sendUiEvent(event = UiEvent.Navigate(Routes.ADD_EDIT_ACCOUNT))
            }

            is AccountEvent.OnDoneChanged -> {
                viewModelScope.launch {

                    repository.insertAccount(
                        event.account.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }

            is AccountEvent.OnUndoDeleteClick -> {
                viewModelScope.launch {

                    deleteAccount = event.account

                    repository.deleteAccount(event.account)

                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = "Account deleted!",
                            action = "Undo"
                        )
                    )

                }
            }

            is AccountEvent.DeleteAccount -> {
                deleteAccount?.let {
                    viewModelScope.launch {
                        repository.deleteAccount(id = event.account)
                    }
                }
            }

            else -> {}
        }

    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}