package com.bulana.centsense.ui.add_edit_account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulana.centsense.data.Account
import com.bulana.centsense.data.AccountRepository
import com.bulana.centsense.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddEditAccountViewModel @Inject constructor(
    private val repository: AccountRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var account by mutableStateOf<Account?>(null)
        private set

    private var accountNumber by mutableStateOf("")

    private var accountName by mutableStateOf("")

    private var accountType by mutableStateOf("")

    private var openingBalance by mutableDoubleStateOf(0.0)

    private var closingBalance by mutableDoubleStateOf(0.0)

    private var annualInterestRate by mutableDoubleStateOf(0.0)

    private var monthlyInstallment by mutableDoubleStateOf(0.0)

    private var totalAdditionalFees by mutableDoubleStateOf(0.0)

    private var firstPaymentDueDate by mutableStateOf(Date())

    private var lastPaymentDueDate by mutableStateOf(Date())

    private var numberOfInstallmentsRemaining by mutableIntStateOf(0)

    private var originalTermMonths by mutableIntStateOf(0)

    private var totalArrears by mutableDoubleStateOf(0.0)

    private var currentDue by mutableDoubleStateOf(0.0)

    private var settlement by mutableDoubleStateOf(0.0)

    private val _uiEvent = Channel<UiEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val accountId = savedStateHandle.get<Int>("accountId")

        if (accountId != -1) {

            viewModelScope.launch {

                accountId?.let {

                    repository.getAccountById(it).let { account ->

                        accountNumber = account.accountNumber
                        accountName = account.accountName
                        accountType = account.accountType
                        openingBalance = account.openingBalance
                        closingBalance = account.closingBalance
                        annualInterestRate = account.annualInterestRate
                        monthlyInstallment = account.monthlyInstallment
                        totalAdditionalFees = account.totalAdditionalFees
                        firstPaymentDueDate = account.firstPaymentDueDate
                        lastPaymentDueDate = account.lastPaymentDueDate
                        numberOfInstallmentsRemaining = account.numberOfInstallmentsRemaining
                        originalTermMonths = account.originalTermMonths
                        totalArrears = account.totalArrears
                        currentDue = account.currentDue
                        settlement = account.settlement
                        this@AddEditAccountViewModel.account = account
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditAccountEvent) {

        when (event) {

            is AddEditAccountEvent.onAccountNameChange -> {
                accountName = event.accountName
            }

            is AddEditAccountEvent.onAccountNumberChange -> {
                accountNumber = event.accoutNumber
            }

            is AddEditAccountEvent.onAccountTypeChange -> {
                accountType = event.accountType
            }

            is AddEditAccountEvent.onOpeningBalanceChange -> {
                openingBalance = event.openingBalance
            }

            is AddEditAccountEvent.onClosingBalanceChange -> {
                closingBalance = event.closingBalance
            }

            is AddEditAccountEvent.onAnnualInterestRateChange -> {
                annualInterestRate = event.annualInterestRate
            }

            is AddEditAccountEvent.onMonthlyInstallmentChange -> {
                monthlyInstallment = event.monthlyInstallment
            }

            is AddEditAccountEvent.onAdditionalFeesChange -> {
                totalAdditionalFees = event.totalAdditionalFees
            }

            is AddEditAccountEvent.onFirstPaymentDueDaterChange -> {
                firstPaymentDueDate = event.firstPaymentDueDate
            }

            is AddEditAccountEvent.onLastPaymentDueDateChange -> {
                lastPaymentDueDate = event.lastPaymentDueDate
            }

            is AddEditAccountEvent.onNumberOfInstallmentsRemainingChange -> {
                numberOfInstallmentsRemaining = event.numberOfInstallmentsRemaining
            }

            is AddEditAccountEvent.onOriginalTermMonthsChange -> {
                originalTermMonths = event.originalTermMonths
            }

            is AddEditAccountEvent.onTotalArrearsChange -> {
                totalArrears = event.totalArrears
            }

            is AddEditAccountEvent.onCurrentDueChange -> {
                currentDue = event.currentDue
            }

            is AddEditAccountEvent.onSettlementChange -> {
                settlement = event.settlement
            }

            is AddEditAccountEvent.OnSaveAccountClick -> {
                viewModelScope.launch {
                    if (validateAccount(account = account)) {
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = "Input field error"
                            )
                        )
                        return@launch
                    }
                    repository.insertAccount(
                        Account(
                            accountNumber = accountNumber,
                            accountName = accountName,
                            accountType = accountType,
                            openingBalance = openingBalance,
                            closingBalance = closingBalance,
                            annualInterestRate = annualInterestRate,
                            monthlyInstallment = monthlyInstallment,
                            totalAdditionalFees = totalAdditionalFees,
                            firstPaymentDueDate = firstPaymentDueDate,
                            lastPaymentDueDate = lastPaymentDueDate,
                            numberOfInstallmentsRemaining = numberOfInstallmentsRemaining,
                            originalTermMonths = originalTermMonths,
                            totalArrears = totalArrears,
                            currentDue = currentDue,
                            settlement = settlement,
                            isPaidUp = false
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun validateAccount(account: Account?): Boolean {

        if (account == null) return false

        if (account.accountNumber.isBlank()) return false

        if (account.accountName.isBlank()) return false

        if (account.accountType.isBlank()) return false

        if (account.openingBalance < 0) return false

        if (account.closingBalance < 0) return false

        if (account.annualInterestRate !in 0.0..100.0) return false

        if (account.monthlyInstallment < 0) return false

        if (account.totalAdditionalFees < 0) return false

        if (account.firstPaymentDueDate.after(account.lastPaymentDueDate)) return false

        if (account.numberOfInstallmentsRemaining < 0) return false

        if (account.originalTermMonths < 0) return false

        if (account.totalArrears < 0 || account.currentDue < 0) return false

        if (account.settlement < 0) return false

        return true
    }
}