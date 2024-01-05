package com.bulana.centsense.ui.accounts_list

import com.bulana.centsense.data.Account

sealed class AccountEvent {

    data class onDeleteAccountClick(val account: Account) : AccountEvent()

    data class OnDoneChange(val account: Account) : AccountEvent()

    data object OnUndoDeleteClick : AccountEvent()

    data object OnAddAccountClick : AccountEvent()

    data class OnAccountClick(val account: Account) : AccountEvent()

    data object LoadAccounts : AccountEvent()

    data class SearchAccounts(val query: String) : AccountEvent()

    data class NavigateToDetails(val id: Int) : AccountEvent()

    data class ErrorEvent(val errorMessage: String) : AccountEvent()
}