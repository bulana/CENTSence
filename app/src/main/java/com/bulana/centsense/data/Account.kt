package com.bulana.centsense.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Account(
    @PrimaryKey val accountNumber: String,
    val accountName: String,
    val loanAmount: Double,
    val annualInterestRate: Double,
    val monthlyInstallment: Double,
    val firstPaymentDueDate: Date,
    val lastPaymentDueDate: Date,
    val numberOfInstallmentsDue: Int,
    val originalTermMonths: Int,
    val openingBalance: Double,
    val closingBalance: Double,
    val totalArrears: Double,
    val currentDue: Double,
    val totalAmountDue: Double,
    val isDone: Boolean
)
