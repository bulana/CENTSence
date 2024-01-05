package com.bulana.centsense.ui.add_edit_account

import java.util.Date

sealed class AddEditAccountEvent {

    data class onAccountNumberChange(
        val accoutNumber: String
    ) : AddEditAccountEvent()

    data class onAccountNameChange(
        val accountName: String
    ) : AddEditAccountEvent()

    data class onAccountTypeChange(
        val accountType: String
    ) : AddEditAccountEvent()

    data class onOpeningBalanceChange(
        val openingBalance: Double
    ) : AddEditAccountEvent()

    data class onClosingBalanceChange(
        val closingBalance: Double
    ) : AddEditAccountEvent()

    data class onAnnualInterestRateChange(
        val annualInterestRate: Double
    ) : AddEditAccountEvent()

    data class onMonthlyInstallmentChange(
        val monthlyInstallment: Double
    ) : AddEditAccountEvent()

    data class onFirstPaymentDueDaterChange(
        val firstPaymentDueDate: Date
    ) : AddEditAccountEvent()

    data class onLastPaymentDueDateChange(
        val lastPaymentDueDate: Date
    ) : AddEditAccountEvent()

    data class onNumberOfInstallmentsRemainingChange(
        val numberOfInstallmentsRemaining: Int
    ) : AddEditAccountEvent()

    data class onOriginalTermMonthsChange(
        val originalTermMonths: Int
    ) : AddEditAccountEvent()

    data class onTotalArrearsChange(
        val totalArrears: Double
    ) : AddEditAccountEvent()

    data class onCurrentDueChange(
        val currentDue: Double
    ) : AddEditAccountEvent()

    data class onSettlementChange(
        val settlement: Double
    ) : AddEditAccountEvent()

    data object OnSaveAccountClick : AddEditAccountEvent()

}
