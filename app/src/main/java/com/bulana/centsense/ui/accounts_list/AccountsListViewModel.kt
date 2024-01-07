package com.bulana.centsense.ui.accounts_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulana.centsense.data.Account
import com.bulana.centsense.data.AccountRepository
import com.bulana.centsense.util.Routes
import com.bulana.centsense.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AccountsListViewModel @Inject constructor(
    private val repository: AccountRepository
) : ViewModel() {

    open val accountsList = repository.getAllAccounts()

    private val _uiEvent = Channel<UiEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedAccount: Account? = null

    fun onEvent(event: AccountEvent) {

        when (event) {

            is AccountEvent.OnAccountClick -> {

                sendUiEvent(
                    event = UiEvent.Navigate(
                        Routes.ADD_EDIT_ACCOUNT + "?accountId=${event.account.accountNumber}"
                    )
                )
            }

            is AccountEvent.OnAddAccountClick -> {

                sendUiEvent(
                    event = UiEvent.Navigate(Routes.ADD_EDIT_ACCOUNT)
                )
            }

            is AccountEvent.OnPaidUpChange -> {

                viewModelScope.launch {

                    repository.insertAccount(
                        event.account.copy(isPaidUp = event.isPaidUp)
                    )
                }
            }

            is AccountEvent.OnDeleteAccountClick -> {

                viewModelScope.launch {

                    deletedAccount = event.account

                    repository.deleteAccount(account = event.account)

                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = "Account deleted!",
                            action = "Undo"
                        )
                    )
                }
            }

            is AccountEvent.OnUndoDeleteClick -> {
                deletedAccount?.let { account ->
                    viewModelScope.launch {
                        repository.insertAccount(account = account)
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