package com.bulana.centsense.ui.accounts_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

val grey = Color(0xFF757575)

val blue = Color(0xFF002D49)

@Composable
fun AccountItem(
    account: Account,
    modifier: Modifier = Modifier,
    onEvent: (AccountEvent) -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = Color.White
    ) {

        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(color = blue),
                    verticalArrangement = Arrangement.Center,
                ) {

                    AccountTitle(label = "Account", value = account.accountName)

                    AccountSubTitle(label = "Type", value = account.accountType)

                }
            }

            Row(
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp, top = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 12.dp)
                        .background(color = Color.White),
                    verticalArrangement = Arrangement.Center
                ) {

                    AccountDetail(
                        label = "Original Loan",
                        value = "R ${account.openingBalance}",
                        color = grey
                    )

                    val fullTermTotalInstallment =
                        account.monthlyInstallment * account.originalTermMonths

                    AccountDetail(
                        label = "Full Term Installment",
                        value = "R $fullTermTotalInstallment",
                        color = grey
                    )

                    val totalInterestAndFees = fullTermTotalInstallment - account.openingBalance

                    AccountDetailForAdditionalFees(
                        label = "Term Interest and Fees",
                        value = "R $totalInterestAndFees",
                        color = blue
                    )

                    AccountDetail(
                        label = "Full Term",
                        value = "${account.originalTermMonths}",
                        color = grey
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 12.dp,
                        end = 12.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Delete",
                    color = blue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable {
                            onEvent(
                                AccountEvent.OnDeleteAccountClick(
                                    account = account
                                )
                            )
                        }
                        .padding(end = 18.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                OutlinedButton(
                    onReportClicked = {
                        onEvent(AccountEvent.OnAccountClick(account))
                    }
                )
            }
        }
    }
}

private fun onReportClicked() {}

@Composable
fun AccountTitle(label: String, value: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 12.dp,
                end = 12.dp,
                top = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "$label",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        Text(
            text = value,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp

        )
    }
}

@Composable
fun AccountSubTitle(label: String, value: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 12.dp,
                end = 12.dp,
                bottom = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "$label",
            color = Color.White,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        )

        Text(
            text = value,
            color = Color.White,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        )
    }
}

@Composable
fun AccountDetailForAdditionalFees(
    label: String,
    value: String,
    color: Color
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "$label:",
            color = color,
            fontSize = 12.sp,
            modifier = Modifier.width(150.dp),
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = value,
            color = Color.Red,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun AccountDetail(label: String, value: String, color: Color) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            color = color,
            fontSize = 12.sp,
            modifier = Modifier.width(150.dp)
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = value,
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun OutlinedButton(onReportClicked: () -> Unit) {
    OutlinedButton(
        onClick = { onReportClicked() },
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.wrapContentWidth(Alignment.Start),
        contentPadding = PaddingValues(
            start = 4.dp,
            top = 0.dp,
            end = 4.dp,
            bottom = 0.dp
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Red,
            contentColor = Color.Black
        )
    ) {
        Text(
            text = "Report",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Preview
@Composable
fun AccountItemPreview_1() {

    val account = Account(
        accountNumber = "123456789",
        accountName = "Standard Bank",
        accountType = "Personal Loan",
        openingBalance = 100000.0,
        closingBalance = 9000.0,
        annualInterestRate = 12.0,
        monthlyInstallment = 5000.0,
        totalAdditionalFees = 500.00,
        firstPaymentDueDate = Date(),
        lastPaymentDueDate = Date(),
        numberOfInstallmentsRemaining = 12,
        originalTermMonths = 18,
        totalArrears = 200.0,
        currentDue = 56000.0,
        settlement = 399000.0,
        isPaidUp = false
    )

    AccountItem(account = account, onEvent = {})
}

@Preview
@Composable
fun AccountItemPreview_2() {

    val account = Account(
        accountNumber = "123456789",
        accountName = "ABSA",
        accountType = "Vehicle Finance",
        openingBalance = 500000.0,
        closingBalance = 9000.0,
        annualInterestRate = 12.0,
        totalAdditionalFees = 500.00,
        monthlyInstallment = 8000.0,
        firstPaymentDueDate = Date(),
        lastPaymentDueDate = Date(),
        numberOfInstallmentsRemaining = 12,
        originalTermMonths = 67,
        totalArrears = 200.0,
        currentDue = 480000.0,
        settlement = 8000.0,
        isPaidUp = false
    )

    AccountItem(account = account, onEvent = {})
}

@Preview
@Composable
fun AccountItemPreview_3() {

    val account = Account(
        accountNumber = "123456789",
        accountName = "TymeBank",
        accountType = "Temp Loan",
        openingBalance = 36000.0,
        closingBalance = 9000.0,
        annualInterestRate = 4.0,
        totalAdditionalFees = 500.00,
        monthlyInstallment = 5000.0,
        firstPaymentDueDate = Date(),
        lastPaymentDueDate = Date(),
        numberOfInstallmentsRemaining = 12,
        originalTermMonths = 12,
        totalArrears = 200.0,
        currentDue = 100.0,
        settlement = 1100.0,
        isPaidUp = false
    )

    AccountItem(account = account, onEvent = {})
}


