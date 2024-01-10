package com.bulana.centsense.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Account(
    @PrimaryKey val accountNumber: String,
    val accountName: String,
    val accountType: String,
    val openingBalance: Double,
    val closingBalance: Double,
    var annualInterestRate: Double,
    var totalAdditionalFees: Double,
    val monthlyInstallment: Double,
    val firstPaymentDueDate: Date,
    val lastPaymentDueDate: Date,
    val numberOfInstallmentsRemaining: Int,
    val originalTermMonths: Int,
    val totalArrears: Double,
    val currentDue: Double,
    val settlement: Double,
    val isPaidUp: Boolean
)
