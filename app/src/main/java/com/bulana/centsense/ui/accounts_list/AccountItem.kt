package com.bulana.centsense.ui.accounts_list

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bulana.centsense.data.Account
import java.util.Date
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AccountItem(
    account: Account,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(12.dp)),
        elevation = 4.dp,
        backgroundColor = Color.LightGray,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                AccountDetailTextTitle(label = "Account", value = account.accountName)
                AccountDetailText(label = "Loan Amount", value = "R ${account.loanAmount}")
                AccountDetailText(label = "Installment", value = "R ${account.monthlyInstallment}")
                AccountDetailText(label = "Interest Rate", value = "${account.annualInterestRate} %")
            }
//            Icon(
//                imageVector = Icons.Default.Delete,
//                contentDescription = "Delete account",
//                tint = Color.Black
//            )
        }
    }
}

@Composable
fun AccountDetailTextTitle(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label",
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            text = value,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun AccountDetailText(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label",
            color = Color.Gray,
            fontSize = 12.sp
        )
        Text(
            text = value,
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

data class Account(
    val accountNumber: String,
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

@Preview
@Composable
fun AccountItemPreview() {
    val sampleAccount = Account(
        accountNumber = "123456789",
        accountName = "Russell",
        loanAmount = 36000.0,
        annualInterestRate = 12.0,
        monthlyInstallment = 5000.0,
        firstPaymentDueDate = Date(),
        lastPaymentDueDate = Date(),
        numberOfInstallmentsDue = 12,
        originalTermMonths = 12,
        openingBalance = 9500.0,
        closingBalance = 9000.0,
        totalArrears = 200.0,
        currentDue = 100.0,
        totalAmountDue = 1100.0,
        isDone = false
    )

    AccountItem(account = sampleAccount)
}

